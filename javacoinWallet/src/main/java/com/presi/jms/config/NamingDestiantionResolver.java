package com.presi.jms.config;

import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.stereotype.Component;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Session;

@Component
public class NamingDestiantionResolver implements DestinationResolver {

	@Override
	public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)
			throws JMSException {
		if (destinationName.endsWith("Queue")) {
			return session.createQueue(destinationName);
		} else if (destinationName.endsWith("Topic")) {
			return session.createTopic(destinationName);
		}
		throw new RuntimeException("Naming convention not respected for destination " + destinationName);
	}
}
