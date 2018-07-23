package com.kpmg.cdd.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: Get the random id
 * @date 09/06/2018 9:56
 */
public class UuidUtils {
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		return str.replace("-", "");
	}

	public static String getSimpleId() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String randomId = sdf.format(now) + (int) ((Math.random() + 1) * 10086);
		return randomId;
	}
}
