package br.com.mateusberger.remotetaskmanager.configurations;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfigs {

    @Bean
    CacheManager cacheManager(){
        return new ConcurrentMapCacheManager(
                "TaskManager.nicknameToProcessInfo",
                "TaskManager.commandToProcessInfo",
                "TaskManager.processNameToProcessInfo"
        );
    }
}