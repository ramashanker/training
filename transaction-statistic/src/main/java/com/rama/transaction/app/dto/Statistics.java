package com.rama.transaction.app.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal sum;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal avg;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal max;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal min;
	private long count;

}
