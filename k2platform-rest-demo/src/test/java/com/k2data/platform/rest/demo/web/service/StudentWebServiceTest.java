package com.k2data.platform.rest.demo.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k2data.platform.rest.demo.entity.JsonResult;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guanxine on 18-4-4.
 */
public class StudentWebServiceTest extends BaseWebServiceTest {

    @Test
    public void create() throws Exception {

        String post = "{\n" +
                "    \"id\":%s\n" +
                "}";

        HttpResponse response = Request.Post(baseUrl + "/students")
                .body(new StringEntity(
                    String.format(post, "1"),
                    ContentType.APPLICATION_JSON)
                )
                .execute()
                .returnResponse();
        Assert.assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = EntityUtils.toString(response.getEntity());
        JsonResult jsonResult = objectMapper.readValue(content, JsonResult.class);
        Assert.assertTrue(jsonResult.getCode() == 0);
        Assert.assertTrue("{\"code\":0,\"message\":\"Create student successfully.\",\"body\":{\"id\":1}}".equals(content));
    }

    @Test
    public void delete() throws Exception {

        HttpResponse response = Request.Delete(baseUrl + "/students/1")
                .execute()
                .returnResponse();
        Assert.assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = EntityUtils.toString(response.getEntity());
        JsonResult jsonResult = objectMapper.readValue(content, JsonResult.class);
        Assert.assertTrue(jsonResult.getCode() == 0);

        Assert.assertTrue("{\"code\":0,\"message\":\"Delete student successfully.\",\"body\":{\"id\":1}}".equals(content));
    }

    @Test
    public void update() throws Exception {

        String update = "{\n" +
                "    \"id\":%s\n" +
                "}";

        HttpResponse response = Request.Put(baseUrl + "/students/1")
                .body(new StringEntity(
                        String.format(update, "1"),
                        ContentType.APPLICATION_JSON)
                )
                .execute()
                .returnResponse();
        Assert.assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = EntityUtils.toString(response.getEntity());
        JsonResult jsonResult = objectMapper.readValue(content, JsonResult.class);
        Assert.assertTrue(jsonResult.getCode() == 0);
        System.out.println(content);
        Assert.assertTrue("{\"code\":0,\"message\":\"Update student successfully.\",\"body\":{\"id\":1}}".equals(content));

    }

    @Test
    public void get() throws Exception {
        HttpResponse response = Request.Get(baseUrl + "/students/1")
                .execute()
                .returnResponse();
        Assert.assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = EntityUtils.toString(response.getEntity());
        JsonResult jsonResult = objectMapper.readValue(content, JsonResult.class);
        Assert.assertTrue(jsonResult.getCode() == 0);
        Assert.assertTrue("{\"code\":0,\"message\":\"Get student successfully.\",\"body\":{\"id\":1}}".equals(content));
        System.out.println(content);
    }

    @Test
    public void list() throws Exception {
        HttpResponse response = Request.Get(baseUrl + "/students?pageNum=1&pageSize=10")
                .execute()
                .returnResponse();
        Assert.assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = EntityUtils.toString(response.getEntity());
        JsonResult jsonResult = objectMapper.readValue(content, JsonResult.class);
        Assert.assertTrue(jsonResult.getCode() == 0);
        System.out.println(content);
        Assert.assertTrue("{\"code\":0,\"message\":\"Get student list successfully.\",\"body\":{\"pageInfo\":{\"total\":0,\"pages\":0,\"pageSize\":10,\"pageNum\":1,\"size\":0},\"items\":[]}}".equals(content));
    }

}