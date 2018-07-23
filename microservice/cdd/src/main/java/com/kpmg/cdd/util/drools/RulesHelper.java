package com.kpmg.cdd.util.drools;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.common.engine.api.repository.EngineDeployment;
import org.flowable.common.engine.api.repository.EngineResource;
import org.flowable.engine.impl.persistence.deploy.DeploymentManager;
import org.flowable.engine.impl.rules.RulesAgendaFilter;
import org.flowable.engine.impl.rules.RulesDeployer;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RulesHelper {
    private static final Logger log = LoggerFactory.getLogger(RulesHelper.class);

    private static Map<String,KieBase> kieBaseCache =new HashMap<>();
    public static void deploy(String ruleFileName,String rule ) {
        log.debug("Processing rules deployment \n {}", rule);
        KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        Resource droolsResource = ResourceFactory.newByteArrayResource(rule.getBytes());
        knowledgeBuilder.add(droolsResource, ResourceType.DRL);
        KieBase kieBase = knowledgeBuilder.newKieBase();
        RulesHelper.kieBaseCache.put(ruleFileName, kieBase);
        RulesHelper.makeDefault();
    }

    public static void remove(String ruleFileName){
        if(RulesHelper.kieBaseCache.containsKey(ruleFileName)){
            RulesHelper.kieBaseCache.remove(ruleFileName);
        }
        RulesHelper.makeDefault();
    }

    private static void makeDefault(){
        InternalKnowledgeBase newKieBase=KnowledgeBaseFactory.newKnowledgeBase((KieBaseConfiguration)null);
        for(KieBase kieBase:RulesHelper.kieBaseCache.values()){
            newKieBase.addPackages(kieBase.getKiePackages());
        }
        RulesHelper.kieBaseCache.put("default",newKieBase);
    }

    public static Object run(String ruleFileName,Object input ) {
        log.debug("run rule  {}", ruleFileName);

        KieBase kieBase = RulesHelper.kieBaseCache.get(ruleFileName);
        KieSession ksession = kieBase.newKieSession();
        ksession.insert(input);
        ksession.fireAllRules();
        ksession.dispose();

        return input;
    }

    public static void clear(){
        RulesHelper.kieBaseCache.clear();
    }

    public static KieBase get(String ruleFileName){
        KieBase kieBase=RulesHelper.kieBaseCache.get(ruleFileName);
        if(kieBase==null){
            throw new RuntimeException("this is no["+ruleFileName+"] rule");
        }
        return kieBase;
    }
}
