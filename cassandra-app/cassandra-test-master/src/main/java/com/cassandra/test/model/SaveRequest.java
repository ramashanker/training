package com.cassandra.test.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SaveRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("orderId")
	private String orderId;
	
	@JsonProperty("amount")
	private Float amount;
	
	@JsonProperty("discount")
	private Float discount;

}