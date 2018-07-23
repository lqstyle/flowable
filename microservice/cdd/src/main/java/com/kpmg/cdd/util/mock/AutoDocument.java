package com.kpmg.cdd.util.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 14/06/2018 9:44 
 */
public class AutoDocument {
    private static final Logger logger = LoggerFactory.getLogger(AutoDocument.class);

    public static String autoRetrieveDocuments(Map<String, Object> map) {
        logger.info("***************number2*****File Extraction Succeesful !！***");
        return "success";
    }
}
