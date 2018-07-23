package com.kpmg.cdd.util.vo;


import com.kpmg.cdd.entity.CaseInfo;
import com.kpmg.cdd.entity.Client;
import com.kpmg.cdd.entity.Document;
import com.kpmg.cdd.entity.ExternalSysResult;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaseVo {
    private String processDefinitionId;
    private Client client;
    private CaseInfo caseInfo;
    private List<Document> documents;
    private List<ExternalSysResult> externalSysResults;
    private List<Map<String, Object>> caseHistories;
    private Map<String, String> taskInfo;
    private String systemName;
    private String msg;


    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(Task task, boolean convert) {
        this.taskInfo = new HashMap<>();
        this.taskInfo.put("name", task.getName());
        this.taskInfo.put("formKey", task.getFormKey());
        this.taskInfo.put("id", task.getId());
        this.taskInfo.put("processInstanceId", task.getProcessInstanceId());
        this.taskInfo.put("processDefinitionId", task.getProcessDefinitionId());
        this.taskInfo.put("assignee", task.getAssignee());
        String[] processDefinition = task.getProcessDefinitionId().split(":");
        this.taskInfo.put("processDefinitionKey", processDefinition[0]);
        this.taskInfo.put("processDefinitionVersion", processDefinition[1]);
    }

    public List<Map<String, Object>> getCaseHistories() {
        return caseHistories;
    }

    public void setCaseHistories(List<Map<String, Object>> caseHistories) {
        this.caseHistories = caseHistories;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CaseInfo getCaseInfo() {
        return caseInfo;
    }

    public void setCaseInfo(CaseInfo caseInfo) {
        this.caseInfo = caseInfo;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public List<ExternalSysResult> getExternalSysResults() {
        return externalSysResults;
    }

    public void setExternalSysResults(List<ExternalSysResult> externalSysResults) {
        this.externalSysResults = externalSysResults;
    }

    public void setTaskInfo(Map<String, String> taskInfo) {
        this.taskInfo = taskInfo;
    }


}
