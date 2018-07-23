package com.kpmg.cdd.controller;

import com.kpmg.cdd.entity.PageFormKey;
import com.kpmg.cdd.entity.User;
import com.kpmg.cdd.service.ModelService;
import com.kpmg.cdd.service.PageFormKeyService;
import com.kpmg.cdd.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1")
@Api( description = "flow models")
public class ModelController {

    private static final Logger log=LoggerFactory.getLogger(ModelController.class);

    @Value("${spring.cloud.config.uri}")
    private String configServerUrl;

    @Autowired
    private ModelService modelService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PageFormKeyService pageFormKeyService;

    @RequestMapping(value = "/models",method = RequestMethod.GET)
    public List getMoleList(@RequestParam  int offset,@RequestParam  int limit,@RequestParam String processDefinitionKey)  {
        List<ProcessDefinition> processDefinitions= modelService.getProcessList(offset,limit,processDefinitionKey);
        List<Map<String,String>> result = new ArrayList<>(processDefinitions.size());
        for (ProcessDefinition processDefinition : processDefinitions) {
            Map<String,String> map=new HashMap<>();
            map.put("id",processDefinition.getId());
            map.put("version", String.valueOf(processDefinition.getVersion()));
            map.put("key",processDefinition.getKey());
            map.put("name",processDefinition.getName());
            result.add(map);
        }
        return result;
    }


    @RequestMapping(value = "/models/deploy/{fileName}",method = RequestMethod.GET)
    private String deployOne(@PathVariable String fileName) throws IOException {
        String rsp= restTemplate.getForObject(configServerUrl+"/processes/default/master/"+fileName+".bpmn20.xml",String.class);
        log.debug("process : \n {}",rsp);
        return modelService.deployModelWithoutRule(fileName+".bpmn20.xml",rsp);
    }

    @RequestMapping(value = "/models/deployAll",method = RequestMethod.GET)
    private List<String> deployAll() throws Exception {
        String rsp= restTemplate.getForObject(configServerUrl+"/processes/default/master/processlist",String.class);
        String[] processList=rsp.split("\n");
        List<String> processDefinitionIds=new ArrayList<>();
        if(processList!=null && processList.length>0){
            for(String process:processList){
                processDefinitionIds.add( this.deployOne(process.trim()) );
            }
           return processDefinitionIds;
        }else{
            throw new Exception("process list is empty!");
        }
    }




//    @ApiOperation("get a process definition basic info and its cofiguration XML. ")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "path", name = "id", dataType = "String", required = true, value = "process definition id")
//    })
//    @RequestMapping(value = "/model/{id}", method = RequestMethod.GET)
//    private Model getModelById(@PathVariable String id) throws IOException {
//        ProcessDefinition processDefinition= modelService.findProcess(id);
//        String xmlString = modelService.findProcessXml(id);
//        Model model = new Model();
//        model.setId(processDefinition.getId());
//        model.setName(processDefinition.getName());
//        model.setVersion(processDefinition.getVersion());
//        model.setDescription(processDefinition.getDescription());
//        model.setXml(xmlString);
//        return model;
//    }

    @ApiOperation("create a new process definition via upload files ")
    @RequestMapping(value = "/models", method = RequestMethod.POST)
    private String deployModel(HttpServletRequest request) throws IOException, XMLStreamException {
        if (!(request instanceof MultipartHttpServletRequest)) {
            throw new FlowableIllegalArgumentException("Multipart request is required");
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        if (multipartRequest.getFileMap().size() == 0) {
            throw new FlowableIllegalArgumentException("Multipart request with file content is required");
        }

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile bpmnFile=null;
        MultipartFile ruleFile=null;
        Boolean isDMN=null;
        for(MultipartFile file : files){
            log.debug("========================================");
            if(file.isEmpty()){
                log.debug("no file uploaded");
            }else{
                log.debug("length: " + file.getSize());
                log.debug("type: " + file.getContentType());
                log.debug("fileName: " + file.getName());
                log.debug("originName: " + file.getOriginalFilename());
                log.debug(IOUtils.toString(file.getInputStream(),"UTF-8"));
                String fileName=file.getOriginalFilename();
                if(fileName.endsWith(".bpmn20.xml")) {
                    bpmnFile=file;
                }else if(fileName.endsWith(".drl")){
                    isDMN=false;
                    ruleFile=file;
                }else if(fileName.endsWith(".dmn")) {
                    isDMN=true;
                    ruleFile=file;
                }
            }
            log.debug("========================================");
        }
        if(isDMN==null){
            return modelService.deployModelWithoutRule(bpmnFile);
        }else if(isDMN){
            return modelService.deployModelWithDMN(bpmnFile,ruleFile);
        }else{
            return modelService.deployModelWithDrl(bpmnFile,ruleFile);
        }
    }

//    @ApiOperation("upate a process definition via xml ")
//    @RequestMapping(value = "/models/{modelId}", method = RequestMethod.PUT)
//    private Model updateModel(@RequestBody Model model) throws IOException, XMLStreamException {
//        Model oldModel =this.getModelById(model.getId());
//        oldModel.setName(model.getName());
//        oldModel.setDescription(model.getDescription());
//        oldModel.setXml(model.getXml());
//        return this.createModel(oldModel);
//    }


    @ResponseBody
    @RequestMapping(value = "/model/{processDefinitionId}/startForm", method = RequestMethod.POST)
    public PageFormKey getStartForm(@RequestParam String processDefinitionId) {
        String formKey= modelService.getStartForm(processDefinitionId);
        return modelService.getFormInfo(formKey);
    }

    @ResponseBody
    @RequestMapping(value = "/usertask/{taskId}/taskForm/", method = RequestMethod.POST)
    public PageFormKey getTaskForm(@RequestParam String taskId) {
        String formKey= modelService.getTaskForm(taskId);
        return modelService.getFormInfo(formKey);
    }

    @RequestMapping(value = "/usertask/claim/{taskId}",method = RequestMethod.PUT)
    public void claimTaks( @PathVariable String taskId,HttpServletRequest request) {
        User user=UserUtils.getCurrentUser(request);
        modelService.claimTask(user.getId(),taskId);
    }

    @RequestMapping(value = "/usertask/unclaim/{taskId}",method = RequestMethod.PUT)
    public void unClaimTaks(@PathVariable String taskId) {
        modelService.unClaimTask(taskId);
    }

    @RequestMapping(value = "/usertasks/{userId}/{state}",method = RequestMethod.GET)
    public List<Task> getUserTasks(@PathVariable String userId, @PathVariable String state) {
        if("assigneed".equals(state)){
            return modelService.getAssigneedTask(userId);
        }else{
            return  modelService.getAssigneeingTask(userId);
        }
    }

    @RequestMapping(value = "/usertasks/{taskId}/form",method = RequestMethod.GET)
    public PageFormKey getPageInfo(@PathVariable String taskId) {
       return pageFormKeyService.getPogeFormKey(taskId);
    }
}
