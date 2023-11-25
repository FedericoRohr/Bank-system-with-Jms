package com.javacoin.main.jmsUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.javacoin.main.models.OrderRequest;

import jakarta.jms.MapMessage;


@Component
public class MessageSender {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	

	@Value("${springjms.orderFirstTopic}")
	private String topic;
	
	@Value("${springjms.sellerResponseQueue}")
	private String sellerResponseQueue;
	
	
	public void sendPurchaseRequest(OrderRequest orderRequest) {
		jmsTemplate.convertAndSend(topic, orderRequest);
	}
	
	public void sendSellerResponse(int orderRequestId, String dniSeller) {
		 jmsTemplate.send(sellerResponseQueue, session -> {
	            MapMessage mapMessage = session.createMapMessage();
	            mapMessage.setIntProperty("orderRequestId", orderRequestId);
	            mapMessage.setString("dniSeller", dniSeller);
	            return mapMessage;
	        });
	}
	
	
}
