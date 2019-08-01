package com.rama.transaction.app.service;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.NavigableMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.rama.transaction.app.dto.Transaction;
import com.rama.transaction.app.service.TransactionServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CacheCleanupTest {

	private TransactionServiceImpl transactionService;

	@Configuration
	static class ContextConfiguration {

		// this bean will be injected into the CacheCleanupTest class
		@Bean
		public TaskExecutor threadPoolTaskExecutor() {
			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
			executor.setCorePoolSize(1);
			executor.setMaxPoolSize(1);
			executor.setThreadNamePrefix("clean_up_task_executor_thread");
			executor.initialize();
			return executor;
		}
	}

	@Autowired
	TaskExecutor excutor;

	@Before
	public void setInjectMocks() {
		transactionService = new TransactionServiceImpl(excutor);
	}

	/*
	 * This is test for clean up the transaction data which is older than 60 second
	 * Here I am inserting the data which is 62 second old and then making it sleep for 5 second.
	 * There after accessing it which is empty.
	 */
	
	@Test
	public void createTransactionWithSingleValueTest() throws InterruptedException {
		assertThat(transactionService.getTransactionsCache()).isEmpty();
		Transaction transaction = new Transaction(new BigDecimal("10.50"), now().minusSeconds(62));
		transactionService.createTransaction(transaction);
		NavigableMap<Long, List<Transaction>> transactions = transactionService.getTransactionsCache();
		assertThat(transactions.size()).isEqualTo(1);
		Thread.sleep(3000);
		NavigableMap<Long, List<Transaction>> transactions1 = transactionService.getTransactionsCache();
		assertThat(transactions1.size()).isEqualTo(0);
	}
}
