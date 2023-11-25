package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bank.jmsUtilities.interfaces.JmsSender;
import com.bank.models.Wallet;
import com.bank.repository.interfaces.WalletRepository;
import com.bank.service.intefaces.PurchaseService;
import com.javacoin.main.models.OrderRequest;

@Component
public class PurchaseServiceImp implements PurchaseService {

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	JmsSender jmsSender;

	@Value("${springjms.orderPurchaseOutQueue}")
	private String sendOrderPurchaseQueue;

	@Value("${springjms.failedOrderQueue}")
	private String failedOrderQueue;

	@Override
	public void purchaseService(OrderRequest orderRequest) {
		final double minimumQyote = 0;
		String errorMsg = null;
		double commision;
		Wallet buyerWallet;
		double dolarsRequest = orderRequest.getDolars();
		String buyerDni = orderRequest.getBuyerDni();

		if (dolarsRequest > minimumQyote) {
			if (walletRepository.exist(buyerDni)) {
				buyerWallet = walletRepository.get(buyerDni);
				commision = calculateCommission(dolarsRequest, buyerWallet.getOperations());
				orderRequest.setCommision(commision);
				if(reteinBalance(dolarsRequest + commision , buyerWallet)) {
	                 System.out.println("Billetera del comprador" + buyerWallet);
	                 System.out.println("Orden con comision calculada" + orderRequest);
					 // TODO hacer que billetera envie un Ok y esperarla con un filtro 
					 // Envia a la cola de la billetera que almacena la orden y envia peticion a comprador
					jmsSender.send(sendOrderPurchaseQueue, orderRequest);
				}else {
					errorMsg = "La cuenta bancaria no tiene saldo suficiente";
				}		
			} else {
				errorMsg = "La cuenta bancaria del comprador es inexistente";
			}
		}else {
			errorMsg = "Cotización es menor al minimo";
		}
		
		if(errorMsg != null) {
			jmsSender.send(failedOrderQueue,errorMsg);
			System.out.println(errorMsg);
		}
	}

	private boolean reteinBalance(double balance, Wallet  wallet) {
		return wallet.reteinBalance(balance);

	}

	private double calculateCommission(double dolars, int operations) {
		double commission = 0;
		if (operations < 3) {
			commission = dolars * 0.05;
		} else if (operations >= 3 && operations <= 6) {
			commission = dolars * 0.03;
		}
		
		System.out.println("Comisión : " + commission);
		return commission;
	}

	@Override
	public void rollbackPurchaseService(OrderRequest orderRequest) {
		Wallet buyerWallet = walletRepository.get(orderRequest.getBuyerDni());
		buyerWallet.releaseReteinedBalance(orderRequest.getCommision()+orderRequest.getDolars());
		
	}

}
