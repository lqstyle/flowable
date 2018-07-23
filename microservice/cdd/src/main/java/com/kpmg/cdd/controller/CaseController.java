package com.kpmg.cdd.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.kpmg.cdd.entity.*;
import com.kpmg.cdd.repository.DocumentMapper;
import com.kpmg.cdd.service.*;
import com.kpmg.cdd.util.UserUtils;
import com.kpmg.cdd.util.mock.*;
import com.kpmg.cdd.util.vo.CaseVo;
import com.kpmg.cdd.util.vo.Page;
import io.swagger.annotations.Api;
import org.flowable.engine.TaskService;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 27/06/2018 3:03 afternoon
 */
@RestController
@RequestMapping("/v1")
@Api(description = "Case operate")
public class CaseController {
    private static final Logger logger = LoggerFactory.getLogger(CaseController.class);

    @Autowired
    private CaseService caseService;
    @Autowired
    private CaseHistoryService caseHistoryService;
    @Autowired
    private ExternalSysResultService externalSysResultService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private DocumentMapper documentMapper;


    @RequestMapping(value = "/cases", method = RequestMethod.GET)
    public Page getCaseList(@RequestParam(value = "page") int pageIndex,
                            @RequestParam int limit,
                            @RequestParam(required = false) String clientName,
                            @RequestParam(required = false) String entityType,
                            HttpServletRequest request) {
        User user = UserUtils.getCurrentUser(request);

        PageHelper.startPage(pageIndex, limit);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("clientName", clientName);
        map.put("entityType", entityType);
        List<Map<String, Object>> list = caseService.getCaseList(map);

        Page page = new Page();
        PageInfo<Map<String, Object>> pageInfo;
        if (!CollectionUtils.isEmpty(list)) {
            pageInfo = new PageInfo<>(list);
            page.setTotal(pageInfo.getTotal());
        }
        page.setItems(list);
        return page;
    }

    @RequestMapping(value = "/cases/all/history", method = RequestMethod.GET)
    public Page getCaseHistoryList(@RequestParam(value = "page") int pageIndex,
                                   @RequestParam int limit,
                                   @RequestParam(required = false) String clientName,
                                   @RequestParam(required = false) String entityType,
                                   HttpServletRequest request) {

        PageHelper.startPage(pageIndex, limit);
        Map<String, Object> map = new HashMap<>();
        map.put("clientName", clientName);
        map.put("entityType", entityType);
        List<Map<String, Object>> list = caseService.getCaseHistoryList(map);

        Page page = new Page();
        PageInfo<Map<String, Object>> pageInfo;
        if (!CollectionUtils.isEmpty(list)) {
            pageInfo = new PageInfo<>(list);
            page.setTotal(pageInfo.getTotal());
        }
        page.setItems(list);
        return page;
    }

    /**
     * Function Description:add case
     *
     * @param: [Case,
     * createUser]
     * @return: java.lang.String
     * @author: lucasliang
     * @date: 13/06/2018 3:27 afternoon
     */
    @RequestMapping(value = "/cases", method = RequestMethod.POST)
    public CaseInfo createCase(@RequestBody CaseVo caseVo, HttpServletRequest request) {

        User user = UserUtils.getCurrentUser(request);
        return caseService.createCase(caseVo.getClient(), caseVo.getProcessDefinitionId(), user.getId());

    }

    @RequestMapping(value = "/cases/{caseId}", method = RequestMethod.GET)
    public CaseVo fetchCase(@PathVariable String caseId) {
        CaseVo caseVo = new CaseVo();
        caseVo.setCaseInfo(caseService.selectById(caseId));
        caseVo.setClient(clientService.getSingleClient(caseId));
        caseVo.setDocuments(documentMapper.selectByCaseId(caseId));
        caseVo.setCaseHistories(caseHistoryService.getCaseHistoryList(caseId));
        caseVo.setTaskInfo(taskService.createTaskQuery().taskId(caseVo.getCaseInfo().getTaskId()).singleResult(), true);
        caseVo.setExternalSysResults(externalSysResultService.getExternalSysResultList(caseId));
        return caseVo;
    }

    @RequestMapping(value = "/cases", method = RequestMethod.PUT)
    public CaseInfo updateCase(@RequestBody CaseVo caseVo, HttpServletRequest request) {
        User user = UserUtils.getCurrentUser(request);
        return caseService.updateCase(caseVo.getCaseInfo(), caseVo.getClient(), user.getId(), caseVo);
    }

    @RequestMapping(value = "/cases/{caseId}", method = RequestMethod.PUT)
    public CaseInfo turnCase(@PathVariable String caseId, @RequestBody Map<String, Object> map,
                             HttpServletRequest request) {
        User user = UserUtils.getCurrentUser(request);
        CaseVo caseVo = new CaseVo();
        CaseInfo caseInfo = caseService.selectById(caseId);
        String comments = map.get("comments") != null ? map.get("comments").toString() : "";
        caseInfo.setComments(comments);
        String msg = map.get("msg") != null ? map.get("msg").toString() : "";
        caseVo.setMsg(msg);
        String systemName = map.get("systemName") != null ? map.get("systemName").toString() : "";
        caseVo.setSystemName(systemName);
        String processResult = map.get("processResult") != null ? map.get("processResult").toString() : "";
        caseInfo.setProcessResult(processResult);
        String backTo = map.get("backTo") != null ? map.get("backTo").toString() : "";
        if (!StringUtils.isEmpty(processResult) && "rollback".equals(processResult)) {
            caseInfo.setBackTo(backTo);
        }
        caseVo.setCaseInfo(caseInfo);
        Client client = clientService.getSingleClient(caseId);
        caseVo.setClient(client);
        return caseService.updateCase(caseVo.getCaseInfo(), caseVo.getClient(), user.getId(), caseVo);
    }


}
