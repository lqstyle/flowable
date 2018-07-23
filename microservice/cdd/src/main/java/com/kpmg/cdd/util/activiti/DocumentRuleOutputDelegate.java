package com.kpmg.cdd.util.activiti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kpmg.cdd.entity.Client;
import com.kpmg.cdd.entity.Document;
import com.kpmg.cdd.util.UuidUtils;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kpmg.cdd.CDDApplication;
import com.kpmg.cdd.repository.ClientMapper;
import com.kpmg.cdd.repository.DocumentMapper;

public class DocumentRuleOutputDelegate implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(DocumentRuleOutputDelegate.class);

    @Override
    public void execute(DelegateExecution exe) {
        log.debug("will output document list to db");
        List<Client> clients = (List<Client>) exe.getVariable("clients");
        Client client = clients.get(0);
         log.debug("document list: [{}]", client.getRequiredDocumentList());
         if(null != client.getRequiredDocumentList()) {
             DocumentMapper documentMapper = CDDApplication.AC.getBean(DocumentMapper.class);
             String activityId = exe.getCurrentActivityId();
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
    }

}