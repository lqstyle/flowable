package com.kpmg.cdd.service;

import com.kpmg.cdd.entity.Client;
import com.kpmg.cdd.entity.Document;
import com.kpmg.cdd.entity.ExternalSysResult;
import com.kpmg.cdd.repository.ClientMapper;
import com.kpmg.cdd.repository.DocumentMapper;
import com.kpmg.cdd.repository.ExternalSysResultMapper;
import com.kpmg.cdd.util.UuidUtils;
import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ServiceTaskService {


    private static final Logger log = LoggerFactory.getLogger(ServiceTaskService.class);

    @Autowired
    private DocumentMapper documentMapper;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private ExternalSysResultMapper externalSysResultMapper;

    public void storeRuleResult(DelegateExecution exe) {
        log.debug("will output document list to db");
        List<Client> clients = (List<Client>) exe.getVariable("clients");
        Client client = clients.get(0);
        log.debug("document list: [{}]", client.getRequiredDocumentList());

        String activityId = exe.getCurrentActivityId();
        documentMapper.deleteListByActivity(activityId, client.getCaseId());
        if(null!=client.getRequiredDocumentList()){
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
        clientMapper.updateResultStateById(client.getResultState(),client.getId());
    }


    public void receive(DelegateExecution exe, String systemName, String msg) {
        log.debug("receive msg: [{}]", msg);
        Client client = (Client) exe.getVariable("client");
        ExternalSysResult externalSysResult = new ExternalSysResult();
        externalSysResult.setId(UuidUtils.getSimpleId());
        externalSysResult.setCaseId(client.getCaseId());
        externalSysResult.setCommitTime(new Date());
        externalSysResult.setContent(msg);
        externalSysResult.setName(systemName);
        externalSysResultMapper.insert(externalSysResult);
    }

    public void captureAllFiles(DelegateExecution execution, String systemName, String msg) {
        log.debug("Capture all file and store", msg);
        Client client = (Client) execution.getVariable("client");
        ExternalSysResult externalSysResult = new ExternalSysResult();
        externalSysResult.setId(UuidUtils.getSimpleId());
        externalSysResult.setCaseId(client.getCaseId());
        externalSysResult.setCommitTime(new Date());
        externalSysResult.setContent(msg);
        externalSysResult.setName(systemName);
        externalSysResultMapper.insert(externalSysResult);
    }

    public void getNegativeNews(DelegateExecution execution, String systemName, String msg) {
        log.debug("Retrieve Screening or Negative News", msg);
        Client client = (Client) execution.getVariable("client");
        ExternalSysResult externalSysResult = new ExternalSysResult();
        externalSysResult.setId(UuidUtils.getSimpleId());
        externalSysResult.setCaseId(client.getCaseId());
        externalSysResult.setCommitTime(new Date());
        externalSysResult.setContent(msg);
        externalSysResult.setName(systemName);
        externalSysResultMapper.insert(externalSysResult);
    }

    public void receiveKycInfo(DelegateExecution execution, String systemName, String msg) {
        log.debug("Complete Questionaire on KYC Portal", msg);
        Client client = (Client) execution.getVariable("client");
        ExternalSysResult externalSysResult = new ExternalSysResult();
        externalSysResult.setId(UuidUtils.getSimpleId());
        externalSysResult.setCaseId(client.getCaseId());
        externalSysResult.setCommitTime(new Date());
        externalSysResult.setContent(msg);
        externalSysResult.setName(systemName);
        externalSysResultMapper.insert(externalSysResult);
    }

    public void obtainFinalRiskRating(DelegateExecution execution, String systemName, String msg) {
        log.debug("Obtain Final Risk Rating", msg);
        Client client = (Client) execution.getVariable("client");
        ExternalSysResult externalSysResult = new ExternalSysResult();
        externalSysResult.setId(UuidUtils.getSimpleId());
        externalSysResult.setCaseId(client.getCaseId());
        externalSysResult.setCommitTime(new Date());
        externalSysResult.setContent(msg);
        externalSysResult.setName(systemName);
        externalSysResultMapper.insert(externalSysResult);
    }
}
