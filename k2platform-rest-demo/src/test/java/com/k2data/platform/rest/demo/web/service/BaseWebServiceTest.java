package com.k2data.platform.rest.demo.web.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by guanxine on 18-4-4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class BaseWebServiceTest {
    protected String baseUrl = "http://localhost:8080/rest-demo";
}
