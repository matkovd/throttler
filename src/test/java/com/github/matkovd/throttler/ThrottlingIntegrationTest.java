package com.github.matkovd.throttler;

import com.github.matkovd.throttler.example.Application;
import com.github.matkovd.throttler.example.Constant;
import com.github.matkovd.throttler.example.ExampleController;
import com.github.matkovd.throttler.example.ExampleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@EnableWebMvc
@AutoConfigureMockMvc
@SpringBootTest(classes = {Application.class, ExampleController.class, ExampleService.class, Throttle.class,
        ThrottlingInterceptor.class, ThrottleConfig.class})
public class ThrottlingIntegrationTest {

    private static final int TOTAL_ATTEMPTS = 10000;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        int pass = 0;
        for (int i = 0; i < TOTAL_ATTEMPTS; i++) {
            var result = mockMvc.perform(MockMvcRequestBuilders.get("/example")).andReturn();
            if (result.getResponse().getContentAsString().equals(Constant.PASS)) {
                pass += 1;
            }
        }
        assertEquals("", TOTAL_ATTEMPTS * Constant.PERCENTAGE / 100, pass);
    }
}
