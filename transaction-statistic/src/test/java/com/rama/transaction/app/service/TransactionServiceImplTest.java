package com.rama.transaction.app.service;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.NavigableMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.task.TaskExecutor;

import com.rama.transaction.app.dto.Statistics;
import com.rama.transaction.app.dto.Transaction;
import com.rama.transaction.app.service.TransactionServiceImpl;

@RunWith(JUnit4.class)
public class TransactionServiceImplTest {

	@InjectMocks
	private TransactionServiceImpl transactionService;
	@Mock
	TaskExecutor excutor;
	@Before
	public void setInjectMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createTransactionWithSingleValueTest() {
		assertThat(transactionService.getTransactionsCache()).isEmpty();
		Transaction transaction = new Transaction(new BigDecimal("10.50"), now());
		transactionService.createTransaction(transaction);
		NavigableMap<Long, List<Transaction>> transactions = transactionService.getTransactionsCache();
		assertThat(transactions.size()).isEqualTo(1);
		assertThat(transactions.values().stream().findFirst().get().get(0)).isEqualTo(transaction);
	}
	
	@Test
	public void createTransactionWithIntervalTimeAndGetStatisticsTest() {
		assertThat(transactionService.getTransactionsCache()).isEmpty();
		Transaction transaction1 = new Transaction(new BigDecimal("10.50"), now());
		Transaction transaction2 = new Transaction(new BigDecimal("12.50"), now().minusSeconds(30));
		Transaction transaction3 = new Transaction(new BigDecimal("13.50"), now().minusSeconds(70));
		transactionService.createTransaction(transaction1);
		transactionService.createTransaction(transaction2);
		transactionService.createTransaction(transaction3);
		NavigableMap<Long, List<Transaction>> transactions = transactionService.getTransactionsCache();
		assertThat(transactions.size()).isEqualTo(3);
		Statistics statistics= transactionService.getStatistics();
		assertThat(statistics.getCount()).isEqualTo(2);
		assertThat(statistics.getMax()).isEqualTo(new BigDecimal("12.50"));
		assertThat(statistics.getMin()).isEqualTo(new BigDecimal("10.50"));
		assertThat(statistics.getSum()).isEqualTo(new BigDecimal("23.00"));
	}
	
	@Test
	public void createTransactionWithIntervalTimeAndGetStatisticsAndDeleteTest() throws InterruptedException {
		assertThat(transactionService.getTransactionsCache()).isEmpty();
		/*
		 * Create Transaction
		 */
		Transaction transaction1 = new Transaction(new BigDecimal("10.50"), now());
		Transaction transaction2 = new Transaction(new BigDecimal("11.50"), now().minusSeconds(10));
		Transaction transaction3 = new Transaction(new BigDecimal("12.50"), now().minusSeconds(20));
		Transaction transaction4 = new Transaction(new BigDecimal("13.50"), now().minusSeconds(30));
		Transaction transaction5 = new Transaction(new BigDecimal("14.50"), now().minusSeconds(40));
		BigDecimal expectedMin=new BigDecimal("10.50");
		BigDecimal expectedMax=new BigDecimal("14.50");
		BigDecimal expectedSum=new BigDecimal("62.50");
		BigDecimal expectedAvg=new BigDecimal("12.50");
		long expectedCount=5;
		transactionService.createTransaction(transaction1);
		transactionService.createTransaction(transaction2);
		transactionService.createTransaction(transaction3);
		transactionService.createTransaction(transaction4);
		transactionService.createTransaction(transaction5);
		NavigableMap<Long, List<Transaction>> transactions = transactionService.getTransactionsCache();
		assertThat(transactions.size()).isEqualTo(expectedCount);
		/*
		 * Get Statistics
		 */
		Statistics statistics= transactionService.getStatistics();
		assertThat(statistics.getCount()).isEqualTo(expectedCount);
		assertThat(statistics.getMin()).isEqualTo(expectedMin);
		assertThat(statistics.getMax()).isEqualTo(expectedMax);
		assertThat(statistics.getSum()).isEqualTo(expectedSum);
		assertThat(statistics.getAvg()).isEqualTo(expectedAvg);
		/*
		 * Delete Transaction
		 */
		transactionService.deleteTransaction();
		assertThat(transactions.size()).isEqualTo(0);
	}
	
	@Test
	public void createTransactionWithAndGetStatisticsAndDeleteWithDelayTest() throws InterruptedException {
		assertThat(transactionService.getTransactionsCache()).isEmpty();
		/*
		 * Create Transaction
		 */
		Transaction transaction1 = new Transaction(new BigDecimal("10.50"), now());
		Transaction transaction2 = new Transaction(new BigDecimal("11.50"), now().minusSeconds(40));
		Transaction transaction3 = new Transaction(new BigDecimal("12.50"), now().minusSeconds(55));
		
		BigDecimal expectedMin=new BigDecimal("10.50");
		BigDecimal expectedMax=new BigDecimal("11.50");
		BigDecimal expectedSum=new BigDecimal("22.00");
		BigDecimal expectedAvg=new BigDecimal("11.00");
		long expectedCount=3;
		transactionService.createTransaction(transaction1);
		transactionService.createTransaction(transaction2);
		transactionService.createTransaction(transaction3);
		NavigableMap<Long, List<Transaction>> transactions = transactionService.getTransactionsCache();
		assertThat(transactions.size()).isEqualTo(expectedCount);
		/*
		 * Get Statistics
		 */
		Thread.sleep(5000);
		long expectedCountAfterDelay=2;
		Statistics statistics= transactionService.getStatistics();
		assertThat(statistics.getCount()).isEqualTo(expectedCountAfterDelay);
		assertThat(statistics.getMin()).isEqualTo(expectedMin);
		assertThat(statistics.getMax()).isEqualTo(expectedMax);
		assertThat(statistics.getSum()).isEqualTo(expectedSum);
		assertThat(statistics.getAvg()).isEqualTo(expectedAvg);
	}

}
