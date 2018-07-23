package com.kpmg.cdd.controller;

import com.kpmg.cdd.entity.CaseInfo;
import com.kpmg.cdd.util.drools.RulesHelper;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1")
public class DroolsController {
    private static final Logger log=LoggerFactory.getLogger(DroolsController.class);

    @Value("${spring.cloud.config.uri}")
    private String configServerUrl;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation("builder a new rule via upload files ")
    @RequestMapping(value = "/rules", method = RequestMethod.POST)
    private void buildNewRule(HttpServletRequest request) throws IOException {
        if (!(request instanceof MultipartHttpServletRequest)) {
            throw new FlowableIllegalArgumentException("Multipart request is required");
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        if (multipartRequest.getFileMap().size() == 0) {
            throw new FlowableIllegalArgumentException("Multipart request with file content is required");
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");

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
                RulesHelper.deploy(fileName.substring(0,fileName.length()-4),IOUtils.toString(file.getInputStream()));
            }
            log.debug("========================================");
        }
    }

    @RequestMapping(value = "/rules/refresh/{fileName}",method = RequestMethod.GET)
    private String refreshOne(@PathVariable String fileName){
        String rsp= restTemplate.getForObject(configServerUrl+"/rules/default/master/"+fileName+".drl",String.class);
    //    log.debug("rule : \n {}",rsp);
        RulesHelper.deploy(fileName,rsp);
        return "success";
    }

    @RequestMapping(value = "/rules/refreshAll",method = RequestMethod.GET)
    private String refreshAll() throws Exception {
        String rsp= restTemplate.getForObject(configServerUrl+"/rules/default/master/rulelist",String.class);
        String[] ruleList=rsp.split("\n");
        if(ruleList!=null && ruleList.length>0){
            RulesHelper.clear();
            for(String rule:ruleList){
                this.refreshOne(rule.trim());
            }
            return "success";
        }else{
            throw new Exception("rule list is empty!");
        }
    }

    @ApiOperation("run demo  ")
    @RequestMapping(value = "/rules/test/{fileName}", method = RequestMethod.POST)
    private Map<String,String> run(@PathVariable String fileName, CaseInfo caseInfo) throws IOException {
        KieBase kieBase=RulesHelper.get(fileName);
        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(caseInfo);
        int ruleFiredCount = kieSession.fireAllRules();
        log.debug("fired {} rules",ruleFiredCount);
        kieSession.dispose();
        Map<String,String> map=new HashMap<>();
       /* map.put("country",caseInfo.getClientLocation());
        map.put("entityType",caseInfo.getEntityType());
        map.put("documents",caseInfo.getDocuments());
        map.put("result",caseInfo.getResultState());*/
        return map;
    }




}
