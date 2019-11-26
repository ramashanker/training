package com.cassandra.test.service;

import com.cassandra.test.model.SaveRequest;

public interface OrderService {

	public boolean save(SaveRequest request);

}
