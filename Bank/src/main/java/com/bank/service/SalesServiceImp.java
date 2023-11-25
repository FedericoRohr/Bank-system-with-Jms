package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bank.jmsUtilities.interfaces.JmsSender;
import com.bank.models.Wallet;
import com.bank.repository.interfaces.WalletRepository;
import com.bank.service.intefaces.SalesService;
import com.javacoin.main.models.OrderRequest;

@Service
public class SalesServiceImp implements SalesService {
	
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	JmsSender jmsSender;
	
	@Value("${springjms.succesOrderQueue}")
	private String succesOrderQueue;
	

	@Override
	public void makeSalesAndSendToSeller(OrderRequest orderRequest) {
		 checkSellerWallet(orderRequest);
		 Wallet buyerWallet = walletRepository.get(orderRequest.getBuyerDni());
		 transferCommisionToBank(buyerWallet,orderRequest.getCommision());
		 trasnferDolars(orderRequest,buyerWallet);
		 jmsSender.send(succesOrderQueue,"Operación exitosa, id " + orderRequest.getId());
	}

	
	
	private void trasnferDolars(OrderRequest orderRequest, Wallet buyerWallet) {
		buyerWallet.transferReteinedDolars(orderRequest.getDolars(),walletRepository.get(orderRequest.getSellerDni()));
		
	}



	private void transferCommisionToBank(Wallet buyerWallet, double commision) {
		buyerWallet.payCommision(commision);
		
	}



	private Wallet checkSellerWallet(OrderRequest orderRequest) {
		String sellerDni = orderRequest.getSellerDni();
		Wallet sellerWallet = walletRepository.get(sellerDni);
		if(sellerWallet == null) {
			sellerWallet = walletRepository.createWallet(orderRequest.getSellerDni());
		}
		return sellerWallet;
	}



	

}
