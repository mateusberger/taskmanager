package br.com.mateusberger.remotetaskmanager.modules.taskmanager;

import java.util.List;

record ProcessInfo (

        String nickname,

        String processName,
        List<String> groups
){

}
