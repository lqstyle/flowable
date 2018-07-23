package com.kpmg.cdd.util.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 19/06/2018 3:16
 */
public class TransferFileToBankSystem {


    private static final Logger logger = LoggerFactory.getLogger(TransferFileToBankSystem.class);

    public static String transferFileToBank(Map<String, Object> map) {
        logger.info("************number1*********Transfer File To Bank Success!***");
        return "success";
    }
}
