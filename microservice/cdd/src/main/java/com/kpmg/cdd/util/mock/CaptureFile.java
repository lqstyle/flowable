package com.kpmg.cdd.util.mock;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author lucasliang
 * @version 0.0.1-SNAPSHOT
 * @description: ${todo}
 * @date 14/06/2018 9:41
 */

public class CaptureFile {

    private static final Logger logger = LoggerFactory.getLogger(CaptureFile.class);

    public static String captureAllFilesForTheCase(Map<String, Object> map) {
        String json = JSONUtils.toJSONString(map);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String url = "http://127.0.0.1:8081/restApi/caseInfo";
        JSONObject resutJson = doPost(url, jsonObject);
        return "success";
    }

    @SuppressWarnings({"resource"})
    private static JSONObject doPost(String url, JSONObject json) {
        CloseableHttpClient httpclient = createDefault();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//sending json data , neet to  set contentType
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(entity);// return json format
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private static CloseableHttpClient createDefault() {
        return HttpClientBuilder.create().build();
    }
}
