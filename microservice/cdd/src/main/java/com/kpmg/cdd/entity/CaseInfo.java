package com.kpmg.cdd.entity;

import java.io.Serializable;
import java.util.Date;

public class CaseInfo implements Serializable {

    private static final long serialVersionUID = -7938805806335450339L;

    private String id;

    private String taskId;

    private String caseKey;

    private String creater;

    private String status;

    private Date createTime;

    private Date claimedTime;


    private String processResult;
    private String backTo;
    private String comments;

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public String getBackTo() {
        return backTo;
    }

    public void setBackTo(String backTo) {
        this.backTo = backTo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getCaseKey() {
        return caseKey;
    }

    public void setCaseKey(String caseKey) {
        this.caseKey = caseKey == null ? null : caseKey.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getClaimedTime() {
        return claimedTime;
    }

    public void setClaimedTime(Date claimedTime) {
        this.claimedTime = claimedTime;
    }
}