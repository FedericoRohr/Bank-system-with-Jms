package com.bank.config;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DestinationResolver;

import jakarta.jms.JMSException;

@Configuration
@EnableJms
public class JmsConfig {
	
	@Autowired 
	DestinationResolver destinationResolver;
	
	@Bean
    public CachingConnectionFactory cachingConnectionFactory() throws JMSException {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");
        CachingConnectionFactory factory = new CachingConnectionFactory(activeMQConnectionFactory);
        factory.setSessionCacheSize(10);
        factory.setCacheProducers(false);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
    	JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory());
    	jmsTemplate.setDestinationResolver(destinationResolver);
        return jmsTemplate;
    }
    
}
