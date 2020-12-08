/*
package com.rama.artemis.controller;

import com.rama.artemis.producer.ArtemisProducer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebMvcTest(ProducerController.class)
@ActiveProfiles(value = "test")
public class RetryMaxProducerSuccessTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RetryMaxProducerSuccessTest.class);
    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    @EnableRetry
    public static class TestConfig {

        @Bean
        public ArtemisProducer artemisProducer() throws Exception {
            JmsTemplate jmsTemplate = Mockito.mock(JmsTemplate.class);
            doThrow(new UncategorizedJmsException("Connection lost in during message")).doNothing().when(jmsTemplate).convertAndSend(Mockito.anyString(), Mockito.anyString());
            ArtemisProducer messageSendingService = new ArtemisProducer(jmsTemplate);

            return messageSendingService;
        }
    }

    @Test
    public void test_TopsMessage_Failed_Max_Retry()  {
        Long startTime= System.currentTimeMillis();
        String message= "message sent to queue";
        try {
            ResultActions responseEntity = mockMvc.perform(get("/send").param("msg", message));
            responseEntity.andExpect(status().isOk());
            Long endtime= System.currentTimeMillis();
            Long retryTime=endtime-startTime;
            LOGGER.info("Total Retry Time Taken  {} ms ",retryTime);
            assertTrue(retryTime<10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
*/
