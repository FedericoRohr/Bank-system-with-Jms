package com.presi.jms.jmsUtilities;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.presi.jms.jmsUtilities.interfaces.JmsSender;


@Component
public class MessageSenderImp implements JmsSender{

	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public void send(String destination, Serializable serializable) {
		jmsTemplate.convertAndSend(destination, serializable);
		System.out.println("Mensaje enviado a " + destination);
	}
	
	
}
