package br.com.mateusberger.remotetaskmanager.modules.scheduler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "scheduler", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Setter
@Getter
@Slf4j
class SchedulerProperties {

    private Map<String, SchedulingTask> scheduled;
}