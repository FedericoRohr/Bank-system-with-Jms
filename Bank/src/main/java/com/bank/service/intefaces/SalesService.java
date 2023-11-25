package com.bank.service.intefaces;

import com.javacoin.main.models.OrderRequest;

public interface SalesService {

	void makeSalesAndSendToSeller(OrderRequest orderRequest);


}
