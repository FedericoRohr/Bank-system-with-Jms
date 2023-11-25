package com.bank.service.intefaces;

import com.javacoin.main.models.OrderRequest;

public interface PurchaseService {

	void purchaseService(OrderRequest orderRequest);

	void rollbackPurchaseService(OrderRequest orderRequest);

}
