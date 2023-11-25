package com.presi.jms.jmsUtilities.interfaces;

import java.io.Serializable;

public interface JmsSender {

	void send(String destination, Serializable serializable);
}
