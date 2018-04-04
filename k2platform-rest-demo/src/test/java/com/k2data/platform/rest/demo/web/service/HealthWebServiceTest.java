package com.k2data.platform.rest.demo.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k2data.platform.rest.demo.entity.JsonResult;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guanxine on 18-4-2.
 */
public class HealthWebServiceTest extends BaseWebServiceTest {

    @Test
    public void check() throws Exception {
        HttpResponse response = Request.Get(baseUrl + "/health")
                .execute()
                .returnResponse();
        Assert.assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
        String content = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonResult jsonResult = objectMapper.readValue(content, JsonResult.class);
        Assert.assertTrue(jsonResult.getCode() == 0);
        Assert.assertTrue("The rest-demo service is healthy.".equals(jsonResult.getMessage()));
    }

}