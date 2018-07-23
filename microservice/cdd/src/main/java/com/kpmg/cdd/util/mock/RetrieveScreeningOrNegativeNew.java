package com.kpmg.cdd.util.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 19/06/2018 3:12
 */
public class RetrieveScreeningOrNegativeNew  {
    private static final Logger logger = LoggerFactory.getLogger(RetrieveScreeningOrNegativeNew.class);

    public static String identificationAndVerification(Map<String,Object> map) {
        logger.info("************number1*********Identification And Verification Success!***");
        return "success";
    }
}
