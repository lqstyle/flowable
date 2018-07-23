package com.kpmg.cdd.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kpmg.cdd.entity.Client;
import com.kpmg.cdd.entity.ExternalSysResult;
import com.kpmg.cdd.repository.CaseHistoryMapper;
import com.kpmg.cdd.repository.CaseInfoMapper;
import com.kpmg.cdd.repository.ClientMapper;
import com.kpmg.cdd.repository.ExternalSysResultMapper;
import com.kpmg.cdd.util.vo.CaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.kpmg.cdd.entity.CaseHistory;
import com.kpmg.cdd.entity.CaseInfo;
import com.kpmg.cdd.util.UuidUtils;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 27/06/2018 3:49
 */
@Service
public class CaseService {

    @Autowired
    private CaseInfoMapper caseInfoMapper;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private CaseHistoryMapper caseHistoryMapper;
    @Autowired
    private CaseHistoryService caseHistoryService;
    @Autowired
    private ExternalSysResultMapper externalSysResultMapper;

    @Autowired
    private ModelService modelService;


    public List<Map<String, Object>> getCaseList(Map<String, Object> map) {
        return caseInfoMapper.getCaseList(map);
    }

    public List<Map<String, Object>> getCaseHistoryList(Map<String, Object> map) {
        return caseInfoMapper.getCaseHistory(map);
    }


    public CaseInfo createCase(Client client, String processDefinitionId, String userId) {
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.setId(UuidUtils.getSimpleId());
        caseInfo.setCreater(userId);
        caseInfo.setCaseKey(caseInfo.getId());

        client.setId(UuidUtils.getSimpleId());
        client.setCaseId(caseInfo.getId());
        clientMapper.insert(client);


        Map<String, Object> vars = new HashMap<>();
        vars.put("client", client);
        String taskId = modelService.startProcessInstance(userId, vars, caseInfo.getCaseKey(), processDefinitionId);

        caseInfo.setTaskId(taskId);
        caseInfo.setCreateTime(new Date());
        caseInfoMapper.insert(caseInfo);

        CaseHistory caseHis = new CaseHistory();
        caseHis.setId(UuidUtils.getSimpleId());
        caseHis.setCaseKey(caseInfo.getCaseKey());
        caseHis.setCaseId(caseInfo.getId());
        caseHis.setTaskId(taskId);
        caseHis.setCreator(caseInfo.getCreater());
        caseHistoryService.saveCaseHistory(caseHis);

        return caseInfo;

    }

    public List<CaseInfo> selectByExample(CaseInfo caseInfo) {
        return caseInfoMapper.selectByExample(caseInfo);
    }

    public void updateByPrimaryKeySelective(CaseInfo newCase) {
        caseInfoMapper.updateByPrimaryKeySelective(newCase);
    }

    public CaseInfo selectById(String caseId) {
        return caseInfoMapper.selectByPrimaryKey(caseId);
    }

    public CaseInfo updateCase(CaseInfo caseInfo, Client client, String userId, CaseVo caseVo) {
        CaseHistory caseHistory = new CaseHistory();
        caseHistory.setTaskId(caseInfo.getTaskId());
        caseHistory.setCaseStatus(caseInfo.getProcessResult());
        caseHistory.setComments(caseInfo.getComments());
        caseHistory.setOperator(userId);
        caseHistory.setOperationTime(new Date());
        caseHistoryMapper.updateProcessResultByTaskId(caseHistory);
        clientMapper.updateByPrimaryKey(client);

        Map<String, Object> vars = new HashMap<>();
        vars.put("client", client);
        String newtTaskId = modelService.completeTask(userId, caseInfo.getTaskId(), vars, caseInfo.getProcessResult(), caseInfo.getBackTo());
        String systemName = caseVo.getSystemName();
        if (!StringUtils.isEmpty(userId) && "18071908574916324".equals(userId)) {
            String msg = caseVo.getMsg();
            ExternalSysResult externalSysResult = new ExternalSysResult();
            externalSysResult.setId(UuidUtils.getSimpleId());
            externalSysResult.setCaseId(client.getCaseId());
            externalSysResult.setCommitTime(new Date());
            externalSysResult.setContent(msg);
            externalSysResult.setName(systemName);
            externalSysResultMapper.insert(externalSysResult);
        }

        CaseHistory caseHis = new CaseHistory();
        caseHis.setId(UuidUtils.getSimpleId());
        caseHis.setCaseKey(caseInfo.getCaseKey());
        caseHis.setCaseId(caseInfo.getId());
        caseHis.setTaskId(newtTaskId);
        caseHis.setCreator(caseInfo.getCreater());
        caseHistoryService.saveCaseHistory(caseHis);

        caseInfo.setTaskId(newtTaskId);
        caseInfoMapper.updateTaskId(caseInfo);

        return caseInfo;
    }
}
