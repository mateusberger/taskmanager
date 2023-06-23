package br.com.mateusberger.remotetaskmanager.modules.scheduler;

record SchedulingTask(
        String cronExp,
        Actions action,
        String group
){
}
