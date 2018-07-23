package com.kpmg.cdd.repository;

import java.util.List;
import java.util.Map;

import com.kpmg.cdd.entity.CaseHistory;

public interface CaseHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseHistory record);

    CaseHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseHistory record);

    int updateProcessResultByTaskId(CaseHistory record);

    int updateByPrimaryKey(CaseHistory record);

    List<Map<String, Object>> getCaseHistoryList(Map<String, Object> map);

    List<CaseHistory> getCaseHistoryListByCaseId(String caseId);

    List<CaseHistory> selectByExample(CaseHistory example);

    CaseHistory getSingleCaseHistoryByTaskId(String taskId);
}