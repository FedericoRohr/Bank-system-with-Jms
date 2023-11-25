package com.presi.jms.jmsUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.javacoin.main.models.OrderRequest;
import com.presi.jms.service.interfaces.PurchaseService;
import com.presi.jms.service.interfaces.salesService;

import jakarta.jms.JMSException;
import jakarta.jms.MapMessage;

@Component
public class MyListener {

	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	PurchaseService purchaseService;

	@Autowired

	salesService salesService;

	// Receive buyer order / check buyer
	@JmsListener(destination = "${springjms.orderFirstTopic}")
	public void receiveOrderRequestTopic(OrderRequest orderRequest) {
		System.out.println();
		System.out.println("Topico: Recibe desde el test (comprador). Orden:  " + orderRequest);
		System.out.println();
		System.out.println(
				"Comienzo procesamiento de chequeo existencia de billetera javacoin para comprador o creacion de la misma -------------------------------------");
		purchaseService.checkBuyer(orderRequest);
		System.out.println(
				"Finalizo procesamiento de chequeo existencia de billetera javacoin para comprador o creacion de la misma -------------------------------------");

	}

	// Receive bank order / save OrderRquest (with out sellerDni) / send order to
	// seller
	@JmsListener(destination = "${springjms.orderPurchaseInQueue}")
	public void receiveQueueAndSendSalesOrder(OrderRequest orderRequest) {
		System.out.println();
		System.out.println("Queue: Recibe desde banco. Orden :  " + orderRequest);
		System.out.println();
		System.out.println(
				"Comienzo procesamiento de guardado de orden sin vendedor y envio a de respuesta a vendedor ---------------------------------------------------");
		purchaseService.saveOrderRequestAndSendToSeller(orderRequest);
		System.out.println(
				"Finalizo procesamiento de guardado de orden sin vendedor y envio a de respuesta a vendedor ---------------------------------------------------");

	}

	// Recieve seller response / check seller wallet / make trx / send OrderRequest
	// with sellerId to Bank
	@JmsListener(destination = "${springjms.sellerResponseQueue}")
	public void receiveSellerResponse(MapMessage mapMessage) throws JMSException {
		int orderRequestId = mapMessage.getIntProperty("orderRequestId");
		String dniSeller = mapMessage.getString("dniSeller");
		System.out.println();
		System.out.println(
				"Queue: Recibe desde test (vendedor). orderRequestId :  " + orderRequestId + " dniSeller " + dniSeller);
		System.out.println();
		System.out.println(
				"Comienzo procesamiento de chequeo de vendedor , hace trasnferencia javacoin y envia a banco para que continue procesamiento ------------------");
		salesService.receiveSellerResponseAndSendToBank(orderRequestId, dniSeller);
		System.out.println(
				"Finalizo procesamiento de chequeo de vendedor , hace trasnferencia javacoin y envia a banco para que continue procesamiento ------------------");
	}

}
