package com.kpmg.cdd.repository;

import java.util.List;
import java.util.Map;

import com.kpmg.cdd.entity.CaseInfo;
import org.apache.ibatis.annotations.Param;

public interface CaseInfoMapper {

    int insert(CaseInfo record);

    CaseInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseInfo record);

    List<Map<String, Object>> getCaseList(Map<String, Object> map);

    List<Map<String, Object>> getCaseHistory(Map<String, Object> map);

    List<CaseInfo> selectByExample(CaseInfo example);

    int updateTaskId(CaseInfo record);
}