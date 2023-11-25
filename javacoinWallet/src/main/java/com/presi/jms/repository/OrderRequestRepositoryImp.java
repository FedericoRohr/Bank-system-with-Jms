package com.presi.jms.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javacoin.main.models.OrderRequest;
import com.presi.jms.repository.interfaces.OrderRequestRepository;

@Component
public class OrderRequestRepositoryImp implements OrderRequestRepository {

	private Map<Integer, OrderRequest> orderRequests = new HashMap<>(); // mocking a repository TODO: Make a DB

	@Override
	public OrderRequest save(OrderRequest orderRequest) {
		int id = orderRequests.size() + 1;
		
		orderRequest.setId(id);
		if (orderRequests.get(orderRequest.getId()) == null) {
			orderRequests.put(id, orderRequest);
			System.out.println("Se guarda orderRequest id:" + id);
		}
		return orderRequest;
	}

	@Override
	public OrderRequest get(int orderRequestId) {
		return orderRequests.get(orderRequestId);
	}

	@Override
	public void delete(int orderId) {
		OrderRequest deletedOrderRequest = orderRequests.remove(orderId);
        if (deletedOrderRequest != null) {
            System.out.println("Se borra orderRequest id:" + orderId);
        }
	}

}
