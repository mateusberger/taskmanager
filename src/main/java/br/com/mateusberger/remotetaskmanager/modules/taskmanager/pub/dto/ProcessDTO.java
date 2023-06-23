package br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessDTO {

    private String nickname;
    private Long pid;
    private String processName;
    private String command;
    private List<String> groups;

}
