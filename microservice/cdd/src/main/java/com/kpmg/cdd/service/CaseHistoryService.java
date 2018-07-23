package com.kpmg.cdd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kpmg.cdd.entity.CaseHistory;
import com.kpmg.cdd.repository.CaseHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 27/06/2018 3:12
 */
@Service
public class CaseHistoryService {
    @Autowired
    private CaseHistoryMapper caseHistoryMapper;

    public int saveCaseHistory(CaseHistory caseHistory) {
        return caseHistoryMapper.insert(caseHistory);
    }

    public int updateByPrimaryKey(CaseHistory caseHistory) {
        return caseHistoryMapper.updateByPrimaryKeySelective(caseHistory);
    }


    public List<CaseHistory> selectByExample(CaseHistory example) {
        return caseHistoryMapper.selectByExample(example);
    }


    public List<Map<String, Object>> getCaseHistoryList(String caseId) {
        Map<String, Object> map=new HashMap<>();
        map.put("caseId",caseId);
        return caseHistoryMapper.getCaseHistoryList(map);
    }
}
