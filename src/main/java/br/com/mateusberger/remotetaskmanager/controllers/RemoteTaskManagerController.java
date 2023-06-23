package br.com.mateusberger.remotetaskmanager.controllers;

import br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.TaskManagerService;
import br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto.KilledProcessDTO;
import br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto.TaskManagerInfosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskmanager")
public class RemoteTaskManagerController {

    @Autowired
    private TaskManagerService taskManagerService;

    @GetMapping()
    public ResponseEntity<TaskManagerInfosDTO> listProcess() {
        TaskManagerInfosDTO listOfProcessAndProperties = taskManagerService.getListOfProcessAndProperties();
        return ResponseEntity.ok(listOfProcessAndProperties);
    }

    @GetMapping("/kill-by-nickname/{process}")
    public ResponseEntity<KilledProcessDTO> killByProcessNickname(
            @PathVariable String processNickname,
            @RequestParam(defaultValue = "false", required = false) boolean force
    ) {
        KilledProcessDTO killedProcessDTO = taskManagerService.killProcessByNickname(processNickname, force);
        return ResponseEntity.ok(killedProcessDTO);
    }

    @GetMapping("/kill-by-group/{group}")
    public ResponseEntity<KilledProcessDTO> killByGroup(
            @PathVariable String group,
            @RequestParam(defaultValue = "false", required = false) boolean force
    ) {
        KilledProcessDTO killedProcessDTO = taskManagerService.killProcessByGroup(group, force);
        return ResponseEntity.ok(killedProcessDTO);
    }

    @GetMapping("/kill-by-command/{command}")
    public ResponseEntity<KilledProcessDTO> killByCommand(
            @PathVariable String command,
            @RequestParam(defaultValue = "false", required = false) boolean force
    ) {
        KilledProcessDTO killedProcessDTO = taskManagerService.killProcessByCommand(command, force);
        return ResponseEntity.ok(killedProcessDTO);
    }

}
