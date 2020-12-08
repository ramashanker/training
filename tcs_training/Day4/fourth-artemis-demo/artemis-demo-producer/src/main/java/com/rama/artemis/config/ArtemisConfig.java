package com.rama.artemis.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.retry.annotation.EnableRetry;

import java.util.Collections;

@Configuration
@EnableRetry
public class ArtemisConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtemisConfig.class);
    @Value("${artemis.enable.ssl}")
    private String sslEnabled;
    @Value("${artemis.brokerUrl}")
    private String brokerUrl;
    @Value("${artemis.user}")
    private String user;
    @Value("${artemis.password}")
    private String password;
    @Value("${artemis.connection.cache.size}")
    private int sessionCacheSize;

    @Bean(name = "jmsConnectionFactory")
    public CachingConnectionFactory cachingConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setUserName(user);
        activeMQConnectionFactory.setPassword(password);
        activeMQConnectionFactory.setTrustedPackages(Collections.singletonList("com.rama.artemis"));
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(activeMQConnectionFactory);
        cachingConnectionFactory.setSessionCacheSize(sessionCacheSize);
        return cachingConnectionFactory;
    }
}
