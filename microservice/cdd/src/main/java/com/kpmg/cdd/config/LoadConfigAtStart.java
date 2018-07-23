package com.kpmg.cdd.config;

import com.kpmg.cdd.util.drools.RulesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;



@Component
public class LoadConfigAtStart implements CommandLineRunner
{
    private static final Logger log=LoggerFactory.getLogger(LoadConfigAtStart.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.cloud.config.uri}")
    private String configServerUrl;

    @Override
    public void run(String... args) throws Exception
    {
        log.debug("loading drools rules .....");
        this.refreshAll();

        log.debug("loaded");
    }

    private String refreshOne( String ruleFileName){
        String rsp= restTemplate.getForObject(configServerUrl+"/rules/default/master/"+ruleFileName+".drl",String.class);
        //   log.debug("rule : \n {}",rsp);
        RulesHelper.deploy(ruleFileName,rsp);
        return "success";
    }

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
}
