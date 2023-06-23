package br.com.mateusberger.remotetaskmanager.modules.taskmanager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "remote-task-manager", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Setter
@Getter
@Slf4j
class TaskManagerProperties {

    private List<ProcessInfo> processToManage;

    private Map<String, List<String>> directories;

    @Cacheable("TaskManager.nicknameToProcessInfo")
    protected Map<String, ProcessInfo> mapNicknameToProcessInfo(){

        log.info("RemoteTaskManagerProperties.mapNicknameToProcessInfo - Processing and caching all process nicknames");

        Map<String, ProcessInfo> map = new HashMap<>();

        processToManage.forEach(
                (ProcessInfo processInfo) -> map.put(processInfo.nickname(), processInfo)
        );

        return map;
    }

    @Cacheable("TaskManager.processNameToProcessInfo")
    protected Map<String, ProcessInfo> mapProcessNameToProcessInfo(){

        log.info("RemoteTaskManagerProperties.mapProcessNameToProcessInfo - Processing and caching all process processName");

        Map<String, ProcessInfo> map = new HashMap<>();

        processToManage.forEach(
                (ProcessInfo processInfo) -> map.put(processInfo.processName(), processInfo)
        );

        return map;
    }

    @Cacheable("TaskManager.commandToProcessInfo")
    protected Map<String, ProcessInfo> mapCommandToProcessInfo(){

        log.info("RemoteTaskManagerProperties.mapCommandToProcessInfo - Processing and caching all process command");

        Map<String, ProcessInfo> map = new HashMap<>();

        processToManage.forEach(
                (ProcessInfo processInfo) -> map.put(processInfo.nickname(), processInfo)
        );

        return map;
    }
}
