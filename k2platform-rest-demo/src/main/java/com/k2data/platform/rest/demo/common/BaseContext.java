package com.k2data.platform.rest.demo.common;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by guanxine on 18-04-04.
 */
public class BaseContext {

    public Logger LOG = LoggerFactory.getLogger(BaseContext.class);

    private Class configClass = ParamNames.class;
    private Configuration configuration;

    public BaseContext() throws Exception {
        init();
    }

    private void init() throws Exception {

        this.configuration = initConfig();
        loadEnv();
    }


    private Configuration initConfig() {
        return new PropertiesConfiguration();
    }

    public void loadEnv() throws IllegalAccessException, InstantiationException {
        loadEnv(configClass);
    }

    public void loadEnv(Class clazz) throws IllegalAccessException, InstantiationException {
        Field[] keyFields = clazz.getDeclaredFields();
        Object keyObj = clazz.newInstance();

        for (Field keyField : keyFields) {
            String envValue = System.getenv(keyField.getName());
            String javaKey = keyField.get(keyObj).toString();
            configuration.setProperty(javaKey, envValue);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getEnv(String envKey, String defaultValue) {
        String envValue = System.getenv(envKey);
        if (StringUtils.isNotEmpty(envValue)) {
            return envValue;
        }
        else {
            return defaultValue;
        }
    }
}
