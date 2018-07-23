package com.kpmg.cdd.util.activiti;

import com.kpmg.cdd.CDDApplication;
import com.kpmg.cdd.entity.Client;
import com.kpmg.cdd.entity.Document;
import com.kpmg.cdd.repository.ClientMapper;
import com.kpmg.cdd.repository.DocumentMapper;
import com.kpmg.cdd.util.UuidUtils;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.BusinessRuleTaskActivityBehavior;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CustomeBusinessRuleTaskActivityBehavior extends BusinessRuleTaskActivityBehavior {

    private static final Logger log = LoggerFactory.getLogger(CustomeBusinessRuleTaskActivityBehavior.class);
    protected Set<String> droolsRuleid= new HashSet<>();

    @Override
    public void execute(DelegateExecution execution) {

        KieBase kieBase=null;
        // in this behavior ,the rulenames will just clude a rule file name which configed in gitlab
        if (!rulesExpressions.isEmpty()) {
            Iterator<Expression> itRuleNames = rulesExpressions.iterator();
            while (itRuleNames.hasNext()) {
                Expression ruleName = itRuleNames.next();
               kieBase=com.kpmg.cdd.util.drools.RulesHelper.get(ruleName.getValue(execution).toString());
            }
        } else {
           throw new RuntimeException("rule file name is empty!");
        }

        KieSession ksession = kieBase.newKieSession();

        if (variablesInputExpressions != null) {
            Iterator<Expression> itVariable = variablesInputExpressions.iterator();
            while (itVariable.hasNext()) {
                Expression variable = itVariable.next();
                ksession.insert(variable.getValue(execution));
            }
        }

        ksession.fireAllRules();

        Collection<? extends Object> ruleOutputObjects = ksession.getObjects();
        if (ruleOutputObjects != null && !ruleOutputObjects.isEmpty()) {
            Collection<Object> outputVariables = new ArrayList<>();
            outputVariables.addAll(ruleOutputObjects);
            execution.setVariable(resultVariable, outputVariables);
        }
        ksession.dispose();

        log.debug("will output document list to db");
        List<Client> clients = (List<Client>) execution.getVariable("clients");
        Client client = clients.get(0);
        log.debug("document list: [{}]", client.getRequiredDocumentList());
        if(null != client.getRequiredDocumentList()) {
            DocumentMapper documentMapper = CDDApplication.AC.getBean(DocumentMapper.class);
            String activityId = execution.getCurrentActivityId();
            documentMapper.deleteListByActivity(activityId, client.getCaseId());
            for (String documentName : client.getRequiredDocumentList()) {
                Document document = new Document();
                document.setCaseId(client.getCaseId());
                document.setName(documentName);
                document.setId(UuidUtils.getSimpleId());
                document.setActivityId(activityId);
                document.setStatus("need");
                documentMapper.insert(document);
            }
        }
        ClientMapper clientMapper = CDDApplication.AC.getBean(ClientMapper.class);

        clientMapper.updateResultStateById(client.getResultState(),client.getId());

        leave(execution);
    }

    public void addRuleName(String ruleName){

    }
}
