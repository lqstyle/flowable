package com.kpmg.cdd.service;

import com.kpmg.cdd.entity.ExternalSysResult;
import com.kpmg.cdd.repository.ExternalSysResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 09/07/2018 11:05 AM
 */
@Service
public class ExternalSysResultService {

    @Autowired
    private ExternalSysResultMapper externalSysResultMapper;

    public int insert(ExternalSysResult externalSysResult) {
        return externalSysResultMapper.insert(externalSysResult);
    }

    public List<ExternalSysResult> getExternalSysResultList(String caseId) {
        return externalSysResultMapper.getExternalSysResultList(caseId);
    }

    public ExternalSysResult getSingleExternalSysResult(String id) {
        return externalSysResultMapper.getSingleExternalSysResult(id);
    }

    public void updateByPrimaryKey(ExternalSysResult externalSysResult) {
        externalSysResultMapper.updateByPrimaryKey(externalSysResult);
    }
}
