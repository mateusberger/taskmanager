package br.com.mateusberger.remotetaskmanager.modules.scheduler;

import br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.TaskManagerService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
class RemoteTaskManagerScheduler {

    private TaskScheduler taskScheduler;

    private SchedulerProperties schedulerProperties;

    private TaskManagerService taskManagerService;

    private List<String> schedulingExclusions;

    @Autowired
    public RemoteTaskManagerScheduler(
            TaskScheduler taskScheduler,
            SchedulerProperties schedulerProperties,
            TaskManagerService taskManagerService
    ) {
        this.taskScheduler = taskScheduler;
        this.schedulerProperties = schedulerProperties;
        this.taskManagerService = taskManagerService;
        this.schedulingExclusions = new ArrayList<>();
    }

    @PostConstruct
    public void schedule() {

        log.info("RemoteTaskManagerScheduler.schedule - inserting task into scheduler");

        schedulerProperties.getScheduled().forEach((name, scheduledTask) -> taskScheduler.schedule(
                        () -> {

                            log.info("RemoteTaskManagerScheduler.schedule - starting {}", name);

                            if (schedulingExclusions.contains(name)) {
                                log.info("RemoteTaskManagerScheduler.schedule - {} was skipped", name);
                                return;
                            }

                            if (scheduledTask.action() == Actions.FORCE_KILL) {
                                log.info("RemoteTaskManagerScheduler.schedule - {} executed by force killing process of group {}", name, scheduledTask.group());
                                taskManagerService.killProcessByGroup(scheduledTask.group(), true);
                            }

                            log.info("RemoteTaskManagerScheduler.schedule - {} executed by killing process of group {}", name, scheduledTask.group());
                            taskManagerService.killProcessByGroup(scheduledTask.group(), false);

                        }, new CronTrigger(scheduledTask.cronExp())
                )
        );
    }
}
