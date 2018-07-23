package com.kpmg.cdd.service;

import com.kpmg.cdd.entity.CaseHistory;
import com.kpmg.cdd.entity.CaseInfo;
import com.kpmg.cdd.entity.PageFormKey;
import com.kpmg.cdd.repository.PageFormKeyMapper;
import com.kpmg.cdd.util.CollectionUtils;
import com.kpmg.cdd.util.activiti.cmd.RollbackCmd;
import org.apache.commons.io.IOUtils;
import org.flowable.dmn.api.DmnRepositoryService;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(value = "kpmgService")
public class ModelService {
    private static final Logger log = LoggerFactory.getLogger(ModelService.class);

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private CaseHistoryService caseHistoryService;
    @Autowired
    private CaseService caseService;

    @Autowired
    private PageFormKeyMapper pageFormKeyMapper;

    @Autowired
    private ManagementService managementService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private DmnRepositoryService dmnRepositoryService;
    @Autowired
    private ProcessEngine processEngine;

    /**
     * start a new process instance
     *
     * @param userId
     * @param variables
     * @param businessKey
     * @param processDefinitionId
     * @return
     */
    public String startProcessInstance(String userId, Map<String, Object> variables, String businessKey, String processDefinitionId) {

        identityService.setAuthenticatedUserId(userId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey,
                variables);
        /*
         * processEngine.getRepositoryService() .createProcessDefinitionQuery()
         * .processDefinitionId(processDefinitionId).singleResult() .getName(); String processInstanceName =
         * processDefinitionName + "-" + userConnector.findById(userId).getDisplayName() + "-" + new
         * SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
         * processEngine.getRuntimeService().setProcessInstanceName( processInstance.getId(), processInstanceName);
         */

        return taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult().getId();
    }


    /**
     * complete a user task
     *
     * @param userId
     * @param currentTaskId
     * @param variables
     * @param flag
     * @param historicTaskId
     */
    public String completeTask(String userId, String currentTaskId, Map<String, Object> variables, String flag, String historicTaskId) {
        Task task = taskService.createTaskQuery().taskId(currentTaskId).singleResult();

        if ("rollback".equals(flag)) {
            managementService.executeCommand(new RollbackCmd(currentTaskId, historicTaskId, processEngine));
        } else {
            variables.put("result", flag);

            if (task == null) {
                throw new IllegalStateException("task is not existed ");
            }
            identityService.setAuthenticatedUserId(userId);
            taskService.complete(currentTaskId, variables);
        }
        Task nextTask = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (nextTask == null) {
            return "";
        }
        return nextTask.getId();
    }

    public String getStartForm(String processDefinitionId) {
        return formService.getStartFormKey(processDefinitionId);
    }

    public String getTaskForm(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return task.getFormKey();
    }

    public PageFormKey getFormInfo(String formKey) {
        PageFormKey pageFormKey = new PageFormKey();
        pageFormKey.setPageId(Integer.parseInt(formKey));
        return pageFormKeyMapper.selectByExample(pageFormKey).get(0);
    }


    /**
     * query process definition list
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<ProcessDefinition> getProcessList(int offset, int limit,String processDefinitionKey) {
        return repositoryService.createNativeProcessDefinitionQuery().sql("SELECT a.* " +
                "FROM act_re_procdef a " +
                "INNER JOIN ( " +
                "SELECT key_, MAX(version_) version_ " +
                "FROM act_re_procdef " +
                "GROUP BY key_)b ON a.key_=b.key_ AND a.version_=b.version_ " +
                "ORDER BY a.key_").list();
    }

    /**
     * find one  process definition
     *
     * @param processDefinitionId
     * @return
     */
    public ProcessDefinition findProcess(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }

    /**
     * find one  process definition
     *
     * @param processDefinitionId
     * @return
     */
    public String findProcessXml(String processDefinitionId) throws IOException {
        String xmlString = IOUtils.toString(repositoryService.getProcessModel(processDefinitionId), "UTF-8");
        return xmlString;
    }

    /**
     * claim a task
     *
     * @param userId
     * @param taskId
     * @return
     */
    public void claimTask(String userId, String taskId) {
        taskService.claim(taskId, userId);
        //first update caseHistory claim time
        Date date = new Date();
        CaseHistory caseHistory = new CaseHistory();
        caseHistory.setTaskId(taskId);
        List<CaseHistory> caseHistoryList = caseHistoryService.selectByExample(caseHistory);
        if (CollectionUtils.isNotEmpty(caseHistoryList)) {
            CaseHistory newCaseHistory = caseHistoryList.get(0);
            newCaseHistory.setClaimTime(date);
            caseHistoryService.updateByPrimaryKey(newCaseHistory);
        }
        //update caseInfo claim time
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.setTaskId(taskId);
        List<CaseInfo> caseInfoList = caseService.selectByExample(caseInfo);
        if (CollectionUtils.isNotEmpty(caseInfoList)) {
            CaseInfo newCase = caseInfoList.get(0);
            newCase.setClaimedTime(date);
            caseService.updateByPrimaryKeySelective(newCase);
        }
    }

    /**
     * unclaim a task
     *
     * @param taskId
     * @return
     */
    public void unClaimTask(String taskId) {
        taskService.unclaim(taskId);
    }

    /**
     * get user's all assigneed task
     *
     * @param userId
     * @return
     */
    public List<Task> getAssigneedTask(String userId) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<Task> todoList = taskService.createTaskQuery().taskAssignee(userId).active().list();
        return todoList;
    }


    /**
     * get all task that a user can assignee it
     *
     * @param userId
     * @return
     */
    public List<Task> getAssigneeingTask(String userId) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<Task> toClaimList = taskService.createTaskQuery().taskCandidateUser(userId).active().list();
        return toClaimList;
    }

    /**
     * get a instance 's tasks which completed
     *
     * @param processInstanceId
     * @return
     */
    public List<HistoricTaskInstance> getHistoricTasks(String processInstanceId) {
        List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByHistoricTaskInstanceEndTime().desc().list();
        return hisTasks;
    }


    public String deployModelWithoutRule(MultipartFile bpmnFile) throws IOException {
        Deployment deployment = repositoryService.createDeployment()
                .addInputStream(bpmnFile.getOriginalFilename(), bpmnFile.getInputStream())
                .deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        return processDefinition.getId();
    }

    public String deployModelWithoutRule(String fileName, String bpmnString) throws IOException {
        Deployment deployment = repositoryService.createDeployment()
                .addString(fileName, bpmnString)
                .deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        return processDefinition.getId();
    }

    public String deployModelWithDMN(MultipartFile bpmnFile, MultipartFile ruleFile) throws IOException {
        dmnRepositoryService.createDeployment()
                .addInputStream(ruleFile.getOriginalFilename(), ruleFile.getInputStream())
                .deploy();
        return this.deployModelWithoutRule(bpmnFile);
    }

    public String deployModelWithDrl(MultipartFile bpmnFile, MultipartFile ruleFile) throws IOException {
        Deployment deployment = repositoryService.createDeployment()
                .addInputStream(bpmnFile.getOriginalFilename(), bpmnFile.getInputStream())
                .addInputStream(ruleFile.getOriginalFilename(), ruleFile.getInputStream())
                .deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        return processDefinition.getId();
    }


}
