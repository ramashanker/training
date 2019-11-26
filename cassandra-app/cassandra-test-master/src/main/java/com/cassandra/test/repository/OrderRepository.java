
package com.cassandra.test.repository;

import java.util.List;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import com.cassandra.test.domain.cassandra.Order;

@Repository
public interface OrderRepository extends MapIdCassandraRepository<Order> {

	@Query("select * from ORDER_DATA where orderID = ?0")
	public List<Order> findByOrderId(String orderID);

}
