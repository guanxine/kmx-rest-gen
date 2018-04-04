package com.k2data.platform.rest.demo.common;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by guanxine on 18-4-4.
 */
public class MockBaseContext {


    public static BaseContext mockFileContext() throws Exception {
        BaseContext mockBaseContext = mock(BaseContext.class);
        Configuration configuration = new PropertiesConfiguration();
        when(mockBaseContext.getConfiguration()).thenReturn(configuration);
        return mockBaseContext;
    }
}