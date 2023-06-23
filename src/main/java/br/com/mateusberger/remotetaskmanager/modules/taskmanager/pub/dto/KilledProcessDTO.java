package br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KilledProcessDTO {

    private List<ProcessDTO> processKilled;

}
