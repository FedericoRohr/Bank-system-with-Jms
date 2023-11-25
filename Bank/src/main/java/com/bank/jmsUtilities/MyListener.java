package com.bank.jmsUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.bank.service.intefaces.PurchaseService;
import com.bank.service.intefaces.SalesService;
import com.javacoin.main.models.OrderRequest;

import jakarta.jms.JMSException;

@Component
public class MyListener {

	@Autowired
	PurchaseService purchaseService;
	
	@Autowired
	SalesService salesService;


	// Check buyer wallet / Retein dolars /Complete OrderRequest comision / Send to Billetera
	@JmsListener(destination = "${springjms.orderFirstTopic}")
	public void receive(OrderRequest orderRequest) throws JMSException {
		System.out.println();
		System.out.println("Topico: Recibe desde banco. Orden :  " + orderRequest);
		System.out.println();
		System.out.println(
				"Comienzo procesamiento chequeo de comprador, calculo de comision, completa OrderRequest y envia respuesta a billetera -------------------");
		purchaseService.purchaseService(orderRequest);
		System.out.println(
				"Finalizo procesamiento chequeo de comprador, calculo de comision, completa OrderRequest y envia respuesta a billetera -------------------");

	}

	// checkSellerWallet / make trx / send Response to seller
	@JmsListener(destination = "${springjms.orderSalesQueue}")
	public void receiveSalesOrder(OrderRequest orderRequest) throws JMSException {
		System.out.println();
		System.out.println("Queue: Recibe desde billetera. Orden :  " + orderRequest);
		System.out.println();
		System.out.println(
				"Comienzo procesamiento de venta, chequea wallet de venta, realiza la trasnferencia de dolares y responde al vendedor -------------------");
		salesService.makeSalesAndSendToSeller(orderRequest);
		System.out.println(
				"Finalizo procesamiento de venta, chequea wallet de venta, realiza la trasnferencia de dolares y responde al vendedor -------------------");
		
	}
	
	// Rollback retien dolars
		@JmsListener(destination = "${springjms.bankRollbackReteinQueue}")
		public void rollbackOrder(OrderRequest orderRequest) throws JMSException {
			System.out.println();
			System.out.println("Queue: Recibe desde billetera. Orden :  " + orderRequest);
			System.out.println();
			System.out.println(
					"Comienzo procesamiento rollback, devolución de saldo retenido  --------------------------------------------------------------------");
			purchaseService.rollbackPurchaseService(orderRequest);
			System.out.println(
					"Finalizo procesamiento rollback, devolución de saldo retenido  --------------------------------------------------------------------");
			
		}


}
