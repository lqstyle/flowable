package com.kpmg.cdd.service;

import com.kpmg.cdd.entity.PageFormKey;
import com.kpmg.cdd.repository.PageFormKeyMapper;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 27/06/2018 11:22 
 */
@Service
public class PageFormKeyService {
    @Autowired
    private PageFormKeyMapper pageFormKeyMapper;
    @Autowired
    private TaskService taskService;


    public List<Map<String, Object>> getPageFormKeyList(Map<String, Object> map) {
        return pageFormKeyMapper.getPageFormKeyList(map);
    }

    public PageFormKey getPogeFormKey(String  taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return pageFormKeyMapper.findOneByPageId(Integer.valueOf(task.getFormKey()));
    }
}
