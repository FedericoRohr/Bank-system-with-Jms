package com.presi.jms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.javacoin.main.models.OrderRequest;
import com.presi.jms.jmsUtilities.interfaces.JmsSender;
import com.presi.jms.models.Wallet;
import com.presi.jms.repository.interfaces.OrderRequestRepository;
import com.presi.jms.repository.interfaces.WalletRepository;
import com.presi.jms.service.interfaces.salesService;

@Service
public class SalesServiceImp implements salesService{
	
	
	@Autowired 
	WalletRepository walletRepository;
	
	@Autowired
	OrderRequestRepository orderRequestRepository;
	
	@Autowired
	JmsSender jmsSender;
	
	
	@Value("${springjms.orderSalesQueue}")
	private String orderSalesQueue;
	
	@Value("${springjms.failedOrderQueue}")
	private String failedOrderQueue;
	
	@Value("${springjms.bankRollbackReteinQueue}")
	private String bankRollbackReteinQueue;

	@Override
	public void receiveSellerResponseAndSendToBank( int orderRequestId, String dniSeller) {
		String errorMsg = null;
		OrderRequest orderRequest = orderRequestRepository.get(orderRequestId);
		Wallet sellerWallet = walletRepository.get(dniSeller);	
		if(orderRequest != null) {
			if(sellerWallet != null) {
				if(sellerWallet.getJavacoin() >= orderRequest.getJavacoin()) {
					transferJavacoin(orderRequest,sellerWallet);
					orderRequest.setSellerDni(dniSeller);
					//Envia mensaje a banco para que siga con el procesamiento del pedido de compra
					jmsSender.send(orderSalesQueue, orderRequest);
					System.out.println("Enviado a " + orderSalesQueue);
				}else {
					errorMsg = "El vendedor no cuenta con Javacoins suficientes para transferir";
				}
			}else {
				errorMsg = "El vendedor no tiene billetera de javacoins";
			}
		}else {
			errorMsg = "La orden no existe";
		}
		
		if(errorMsg != null) {
		    System.out.println("Se envian mensajes de error y de rollback a banco, debiado a " + errorMsg);
			jmsSender.send(failedOrderQueue,errorMsg);
			jmsSender.send(bankRollbackReteinQueue, orderRequest);
			rollbackOrder(orderRequest.getId());
		}
	}


	private void transferJavacoin(OrderRequest orderRequest, Wallet sellerWallet) {
		sellerWallet.transferJavacoin(orderRequest.getJavacoin(),walletRepository.get(orderRequest.getBuyerDni()));
		
	}
	
	
	private void rollbackOrder(int orderId) {
		orderRequestRepository.delete(orderId);
	}


}
