package com.kpmg.cdd.config;

import com.kpmg.cdd.util.activiti.CustomActivityBehaviorFactory;
import org.flowable.common.engine.impl.EngineDeployer;
import org.flowable.engine.impl.db.DbIdGenerator;
import org.flowable.engine.impl.rules.RulesDeployer;
import org.flowable.job.service.impl.asyncexecutor.AsyncExecutor;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.*;
import org.flowable.spring.boot.app.AppEngineAutoConfiguration;
import org.flowable.spring.boot.app.AppEngineServicesAutoConfiguration;
import org.flowable.spring.boot.app.FlowableAppProperties;
import org.flowable.spring.boot.condition.ConditionalOnProcessEngine;
import org.flowable.spring.boot.idm.FlowableIdmProperties;
import org.flowable.spring.boot.process.FlowableProcessProperties;
import org.flowable.spring.boot.process.ProcessAsync;
import org.flowable.spring.boot.process.ProcessAsyncHistory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class ProcessEngineConfiguration implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {


    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        List<EngineDeployer> deployerList= engineConfiguration.getCustomPostDeployers();
        if(deployerList==null){
            deployerList=new ArrayList<>();
        }
        deployerList.add(new RulesDeployer());
        engineConfiguration.setCustomPostDeployers(deployerList);
        engineConfiguration.setTransactionManager(platformTransactionManager);
        engineConfiguration.setActivityBehaviorFactory(new CustomActivityBehaviorFactory());
        //engineConfiguration.setIdGenerator()
    }
}
