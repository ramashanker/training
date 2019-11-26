package com.cassandra.test.domain.cassandra;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Table("ORDER_DATA")
@Getter
@Setter
public class Order implements Serializable {

	private static final long serialVersionUID = 2404160767202085990L;

	@Id
	@PrimaryKeyColumn(name = "orderID", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
	private String orderID;

	private Float amount;

	private Float discount;

}
