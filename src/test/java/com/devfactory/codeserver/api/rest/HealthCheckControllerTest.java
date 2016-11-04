/**
 * @author yogesh.badke@aurea.com
 */
package com.devfactory.codeserver.api.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.devfactory.codeserver.api.BaseMockUnitTest;
import com.devfactory.codeserver.api.service.SourceApiService;

public class HealthCheckControllerTest extends BaseMockUnitTest {
  
    private MockMvc mockMvc;
    private SourceApiService service;

    @Before
    public void setup() {
        service = Mockito.mock(SourceApiService.class);
        HealthCheckController healthCheckController = new HealthCheckController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(healthCheckController).build();
    }

    @Test
    public void testHealthOk() throws Exception {
        when(service.isHealthOk()).thenReturn(true);
        checkHealthAndAssert(SourceApiService.HEALTH_OK);
    }

    @Test
    public void testHealthDown() throws Exception {
        when(service.isHealthOk()).thenReturn(false);
        checkHealthAndAssert(SourceApiService.HEALTH_NOT_OK);
    }

    private void checkHealthAndAssert(String expectedStatus) throws Exception {
        mockMvc.perform(get("/health"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"status\": \""+expectedStatus+"\"}", true));
    }
}
