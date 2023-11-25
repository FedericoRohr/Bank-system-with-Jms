package com.javacoin.main.config;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;


import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DestinationResolver;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;

@Configuration
@EnableJms
public class JmsConfig {
	
	@Autowired
	DestinationResolver destinationResolver;
    @Bean
    public ConnectionFactory connectionFactory() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616"); // URL del broker JMS
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) throws JMSException {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setDestinationResolver(destinationResolver);
        return jmsTemplate;
    }
    
}
