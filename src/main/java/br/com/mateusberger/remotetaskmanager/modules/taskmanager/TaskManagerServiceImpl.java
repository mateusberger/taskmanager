package br.com.mateusberger.remotetaskmanager.modules.taskmanager;

import br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto.KilledProcessDTO;
import br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto.OperationalSystemInfoDTO;
import br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto.TaskManagerInfosDTO;
import br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto.ProcessDTO;
import br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.TaskManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
class TaskManagerServiceImpl implements TaskManagerService {

    @Autowired
    private TaskManagerProperties taskManagerProperties;

    @Override
    public TaskManagerInfosDTO getListOfProcessAndProperties() {

        Stream<ProcessHandle> process = getProcess();

        List<ProcessDTO> listOfProcess = process
                .map(this::processDTOFromProcessHandle)
                .toList();

        return TaskManagerInfosDTO.builder()
                .operationalSystemInfo(getSystemInfos())
                .process(listOfProcess)
                .build();
    }

    @Override
    public KilledProcessDTO killProcessByNickname(String processNickname, boolean force) {

        ProcessInfo processInfo = taskManagerProperties.mapNicknameToProcessInfo().get(processNickname);

        if(processInfo == null){
            return null;
        }

        return this.killProcessByCommand(processInfo.processName(), force);
    }

    @Override
    public KilledProcessDTO killProcessByCommand(String processCommand, boolean force) {

        Stream<ProcessHandle> processStream = getProcess();

        List<ProcessDTO> destroyed = new ArrayList<>();

        processStream.forEach(p -> {

                    boolean endsWith = p.info()
                            .command()
                            .orElse("")
                            .endsWith(processCommand);

                    if (!endsWith) {
                        return;
                    }

                    destroyed.add(processDTOFromProcessHandle(p));

                    if (force) {
                        p.destroyForcibly();
                    }

                    p.destroy();
                }
        );


        return KilledProcessDTO.builder()
                .processKilled(destroyed)
                .build();
    }

    @Override
    public KilledProcessDTO killProcessByGroup(String groupName, boolean force) {

        List<String> directories = taskManagerProperties.getDirectories().get(groupName);

        List<String> processNames = taskManagerProperties.getProcessToManage().stream()
                .filter(processInfo -> processInfo.groups().contains(groupName))
                .map(ProcessInfo::processName)
                .toList();

        Stream<ProcessHandle> processStream = getProcess();

        List<ProcessDTO> destroyed = new ArrayList<>();

        processStream.forEach(p -> {

                    if (p.info().command().isEmpty() || p.info().command().get().isBlank()){
                        return;
                    }

                    boolean startsWith = directories.stream()
                            .anyMatch( d -> p.info().command().get().startsWith(d));

                    boolean endsWith = processNames.stream()
                            .anyMatch(procName -> p.info().command().get().endsWith(procName));

                    if (!startsWith && !endsWith) {
                        return;
                    }

                    destroyed.add(processDTOFromProcessHandle(p));

                    if (force) {
                        p.destroyForcibly();
                    }

                    p.destroy();
                }
        );


        return KilledProcessDTO.builder()
                .processKilled(destroyed)
                .build();
    }

    private ProcessDTO processDTOFromProcessHandle(ProcessHandle process) {

        String command = process.info().command().orElse(null);
        String processName = processName(command);

        List<ProcessInfo> processToManage = taskManagerProperties.getProcessToManage();

        Optional<ProcessInfo> first = processToManage.stream()
                .filter(processInfo -> processInfo.processName().equals(processName))
                .findFirst();

        return ProcessDTO.builder()
                .pid(process.pid())
                .command(command)
                .processName(processName)
                .nickname(first.orElse(new ProcessInfo(null, null, null)).nickname())
                .groups(first.orElse(new ProcessInfo(null, null, null)).groups())
                .build();
    }

    private String processName(String command) {

        if (Objects.isNull(command)) {
            return null;
        }

        String cleanPath = StringUtils.cleanPath(command);

        return StringUtils.getFilename(cleanPath);
    }

    private OperationalSystemInfoDTO getSystemInfos() {
        return OperationalSystemInfoDTO.builder()
                .systemName(System.getProperty("os.name"))
                .user(System.getProperty("user.name"))
                .separetors(OperationalSystemInfoDTO.OperationalSystemSeparatorsDTO.builder()
                        .file(System.getProperty("file.separator"))
                        .line(System.getProperty("line.separator"))
                        .path(System.getProperty("path.separator"))
                        .build())
                .build();
    }

    private Stream<ProcessHandle> getProcess() {
        return ProcessHandle.allProcesses();
    }
}
