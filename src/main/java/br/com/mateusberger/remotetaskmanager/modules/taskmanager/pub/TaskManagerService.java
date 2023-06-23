package br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub;

import br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto.KilledProcessDTO;
import br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto.TaskManagerInfosDTO;

public interface TaskManagerService {

    TaskManagerInfosDTO getListOfProcessAndProperties();

    KilledProcessDTO killProcessByNickname(String processNickname, boolean force);

    KilledProcessDTO killProcessByCommand(String processCommand, boolean force);

    KilledProcessDTO killProcessByGroup(String groupName, boolean force);
    
}
