package com.kpmg.cdd.config;

import org.flowable.common.engine.impl.runtime.Clock;
import org.flowable.common.engine.impl.util.DefaultClockImpl;
import org.flowable.ui.admin.properties.FlowableAdminAppProperties;
import org.flowable.ui.admin.service.engine.CmmnTaskService;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowableUiConfig {
    @Bean
    public FlowableModelerAppProperties flowableModelerAppProperties(){
        return new FlowableModelerAppProperties();
    }

    @Bean
    public FlowableAdminAppProperties flowableAdminAppProperties(){
        return new FlowableAdminAppProperties();
    }

    @Bean(name = "kpmgCmmnTaskService")
    public CmmnTaskService cmmnTaskService(){
        return new CmmnTaskService();
    }

    @Bean
    public Clock clock(){
        return new DefaultClockImpl();
    }
}
