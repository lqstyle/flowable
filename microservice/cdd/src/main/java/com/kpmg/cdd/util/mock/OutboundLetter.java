package com.kpmg.cdd.util.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author weitao
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 14/06/2018 9:51
 */
public class OutboundLetter {

    private static final Logger logger = LoggerFactory.getLogger(OutboundLetter.class);

    public static String sendOutboundLetters(Map<String, Object> map) {
        logger.info("Sending Outbound Letters ...");
        return "success";
    }
}
