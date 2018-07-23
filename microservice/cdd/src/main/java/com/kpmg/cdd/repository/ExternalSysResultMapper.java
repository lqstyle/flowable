package com.kpmg.cdd.repository;

import com.kpmg.cdd.entity.ExternalSysResult;

import java.util.List;

public interface ExternalSysResultMapper {

    int insert(ExternalSysResult externalSysResult);

    List<ExternalSysResult> getExternalSysResultList(String caseId);

    ExternalSysResult getSingleExternalSysResult(String id);

    void updateByPrimaryKey(ExternalSysResult externalSysResult);

}