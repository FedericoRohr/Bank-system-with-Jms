package com.presi.jms.service.interfaces;

import com.javacoin.main.models.OrderRequest;

public interface PurchaseService {
	
	void checkBuyer(OrderRequest orderRequest);

    void saveOrderRequestAndSendToSeller(OrderRequest orderRequest);

}
