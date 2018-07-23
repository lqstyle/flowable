package com.kpmg.cdd.util.activiti.cmd;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.ProcessEngine;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

;

public class RollbackCmd implements Command<Void> {


    private String currentTaskId;
    private String historicTaskId;
    private ProcessEngine processEngine;

    public RollbackCmd(String currentTaskId, String historicTaskId, ProcessEngine processEngine) {
        this.currentTaskId = currentTaskId;
        this.historicTaskId = historicTaskId;
        this.processEngine = processEngine;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        Task taskEntity= processEngine.getTaskService().createTaskQuery().taskId(currentTaskId).singleResult();
        HistoricTaskInstance hisTask = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery().taskId(historicTaskId)
                .singleResult();
        BpmnModel definition =  processEngine.getRepositoryService().getBpmnModel(hisTask.getProcessDefinitionId());
        FlowElement hisActivity = definition.getFlowElement(hisTask.getTaskDefinitionKey());
        processEngine.getRuntimeService().createChangeActivityStateBuilder()
                .processInstanceId(taskEntity.getProcessInstanceId()).moveExecutionToActivityId(taskEntity.getExecutionId(), hisActivity.getId())
                .changeState();
        return null;
    }

}