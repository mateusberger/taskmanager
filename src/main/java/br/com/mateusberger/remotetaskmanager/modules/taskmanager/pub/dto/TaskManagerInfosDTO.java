package br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskManagerInfosDTO {

    private OperationalSystemInfoDTO operationalSystemInfo;
    private List<ProcessDTO> process;
}
