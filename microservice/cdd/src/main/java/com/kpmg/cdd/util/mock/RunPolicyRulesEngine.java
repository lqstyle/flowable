package com.kpmg.cdd.util.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 20/06/2018 7:37
 */
public class RunPolicyRulesEngine {

    private static final Logger logger = LoggerFactory.getLogger(RunPolicyRulesEngine.class);

    public static String runPolicyRulesEngine(Map<String, Object> map) {
        logger.info("************number2*********Run Policy Rules Engine Success!***");
        return "success";
    }
}
