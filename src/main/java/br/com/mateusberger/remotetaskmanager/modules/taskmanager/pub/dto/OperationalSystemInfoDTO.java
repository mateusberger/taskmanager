package br.com.mateusberger.remotetaskmanager.modules.taskmanager.pub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationalSystemInfoDTO {

    private String systemName;
    private String user;
    private OperationalSystemSeparatorsDTO separetors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OperationalSystemSeparatorsDTO{
        private String path;
        private String file;
        private String line;
    }
}
