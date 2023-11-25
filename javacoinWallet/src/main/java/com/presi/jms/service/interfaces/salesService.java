package com.presi.jms.service.interfaces;


public interface salesService {

	void receiveSellerResponseAndSendToBank(int orderRequestId, String dniSeller);


}
