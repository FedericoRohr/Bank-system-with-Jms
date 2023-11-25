package com.presi.jms.repository.interfaces;

import com.javacoin.main.models.OrderRequest;

public interface OrderRequestRepository {

	OrderRequest save(OrderRequest orderRequest);

	OrderRequest get(int orderRequestId);

	void delete(int orderId);

}
