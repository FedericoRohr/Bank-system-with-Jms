   package com.presi.jms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.javacoin.main.models.OrderRequest;
import com.presi.jms.jmsUtilities.interfaces.JmsSender;
import com.presi.jms.repository.interfaces.OrderRequestRepository;
import com.presi.jms.repository.interfaces.WalletRepository;
import com.presi.jms.service.interfaces.PurchaseService;

@Service
public class PurchaseServiceImp implements PurchaseService {
	
	@Autowired 
	WalletRepository walletRepository;
	
	@Autowired
	OrderRequestRepository orderRequestRepository;
	
	@Autowired
	JmsSender jmsSender;
	
	@Value("${springjms.orderPurchaseToSeller}")
	private String sendOrderRequestToSellerQueue;
	
	@Override
	public void checkBuyer(OrderRequest orderRequest) {
		walletRepository.createWallet(orderRequest.getBuyerDni());
		//TODO: En caso que este proceso se vuelva mas grande se podria enviar un mensaje de Ok al banco para que pueda enviarnos la respuesta
	}

	@Override
	public void saveOrderRequestAndSendToSeller(OrderRequest orderRequest) {
		OrderRequest savedRequest =  orderRequestRepository.save(orderRequest);
		jmsSender.send(sendOrderRequestToSellerQueue, savedRequest);
	}






}
