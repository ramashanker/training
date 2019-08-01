package com.rama.transaction.app.bigdecimal;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class BigDecimalSummaryStatistics implements Consumer<BigDecimal> {

	public static final int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;

	public static final Integer TRANSACTIONS_SCALE = 2;

	private BigDecimal sum = BigDecimal.ZERO.setScale(TRANSACTIONS_SCALE, ROUND_HALF_UP);
	private BigDecimal minimum = BigDecimal.ZERO.setScale(TRANSACTIONS_SCALE, ROUND_HALF_UP);
	private BigDecimal maximum = BigDecimal.ZERO.setScale(TRANSACTIONS_SCALE, ROUND_HALF_UP);
	private int count;

	public static Collector<BigDecimal, ?, BigDecimalSummaryStatistics> myBigDecimalSummaryStatistics() {

		return Collector.of(BigDecimalSummaryStatistics::new, BigDecimalSummaryStatistics::accept,
				BigDecimalSummaryStatistics::merge);
	}

	@Override
	public void accept(BigDecimal t) {

		if (count == 0) {
			firstElementSetup(t);
		} else {
			sum = sum.add(t);
			minimum = minimum.min(t);
			maximum = maximum.max(t);
			count++;
		}
	}

	public void combine(BigDecimalSummaryStatistics other) {
		count += other.count;
		sum = sum.add(other.sum);
		minimum = minimum.min(other.minimum);
		maximum = maximum.max(other.maximum);
	}

	public BigDecimalSummaryStatistics merge(BigDecimalSummaryStatistics s) {
		if (s.count > 0) {
			if (count == 0) {
				setupFirstElement(s);
			} else {
				sum = sum.add(s.sum);
				minimum = minimum.min(s.minimum);
				maximum = maximum.max(s.maximum);
				count += s.count;
			}
		}
		return this;
	}

	private void setupFirstElement(BigDecimalSummaryStatistics s) {
		count = s.count;
		sum = s.sum;
		minimum = s.minimum;
		maximum = s.maximum;
	}

	private void firstElementSetup(BigDecimal t) {
		count = 1;
		sum = t;
		minimum = t;
		maximum = t;
	}

	public BigDecimal getAverage() {
		if (count == 0) {
			return BigDecimal.ZERO.setScale(TRANSACTIONS_SCALE, ROUND_HALF_UP);
		}
		return sum.divide(BigDecimal.valueOf(count), TRANSACTIONS_SCALE, ROUND_HALF_UP);
	}

	public BigDecimal getSum() {
		return sum.setScale(TRANSACTIONS_SCALE, ROUND_HALF_UP);
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public BigDecimal getMinimum() {
		return minimum.setScale(TRANSACTIONS_SCALE, ROUND_HALF_UP);
	}

	public void setMinimum(BigDecimal minimum) {
		this.minimum = minimum;
	}

	public BigDecimal getMaximum() {
		return maximum.setScale(TRANSACTIONS_SCALE, ROUND_HALF_UP);
	}

	public void setMaximum(BigDecimal maximum) {
		this.maximum = maximum;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "MyBigDecimalSummaryCollector [sum=" + sum + ", minimum=" + minimum + ", maximum=" + maximum + ", count="
				+ count + "]";
	}
}