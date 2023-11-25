package com.javacoin.main.jmsUtilities;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.javacoin.main.models.OrderRequest;

@Component
public class MessageReceiver {

	private OrderRequest receivedOrderRequest;
	

	@JmsListener(destination = "${springjms.orderPurchaseToSellerQueue}")
	public void receiveMessage(OrderRequest orderRequest) {
		System.out.println("Mensaje recibido: " + orderRequest);
		this.receivedOrderRequest = orderRequest;
	}

	public OrderRequest getReceivedOrderRequest() {
		return receivedOrderRequest;
	}

	@JmsListener(destination = "${springjms.succesOrderQueue}")
	public void receiveSuccesMessage(String succesMsg) {
		System.out.println("Mensaje recibido: " + succesMsg);
		
	}
	
	
	@JmsListener(destination = "${springjms.failedOrderQueue}")
	public void receiveErrorMessage(String errorMsg) {
		System.out.println("Mensaje recibido: " + errorMsg);
		
	}

	
}
