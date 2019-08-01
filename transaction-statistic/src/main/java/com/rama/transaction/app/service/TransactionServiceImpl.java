package com.rama.transaction.app.service;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.rama.transaction.app.bigdecimal.BigDecimalSummaryStatistics;
import com.rama.transaction.app.bigdecimal.MyCollectors;
import com.rama.transaction.app.cleanup.CleanUpTask;
import com.rama.transaction.app.dto.Statistics;
import com.rama.transaction.app.dto.Transaction;
import com.rama.transaction.app.util.DataConstants;

@Service
@EnableAsync
public class TransactionServiceImpl implements TransactionService {
	private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
	private final ReentrantLock lock = new ReentrantLock();
	private final ConcurrentNavigableMap<Long, List<Transaction>> transactionCache = new ConcurrentSkipListMap<>();
	private final TaskExecutor taskexecutor;

	public TransactionServiceImpl(final TaskExecutor taskexecutor) {
		this.taskexecutor = taskexecutor;
	}

	@Override
	public void createTransaction(Transaction transaction) {
		lock.lock();
		try {
			long timestamp = transaction.getTimestamp().toEpochMilli();
			long startingEpochSecond = now().minusSeconds(DataConstants.TIME_INTERVAL).toEpochMilli();
			if (!transactionCache.containsKey(timestamp)) {
				transactionCache.put(timestamp, new ArrayList<Transaction>());
			}
			transactionCache.get(timestamp).add(transaction);
			taskexecutor.execute(new CleanUpTask(transactionCache, startingEpochSecond));
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Statistics getStatistics() {
		List<Transaction> transactions = getLatestTransactions();
		BigDecimalSummaryStatistics summaryStatistics = transactions.parallelStream()
				.collect(MyCollectors.summarizingBigDecimal(Transaction::getAmount));
		Statistics statistics = new Statistics(summaryStatistics.getSum(), summaryStatistics.getAverage(),
				summaryStatistics.getMaximum(), summaryStatistics.getMinimum(), summaryStatistics.getCount());

		log.info("Statistics count : {}", summaryStatistics.getCount());
		return statistics;
	}

	@Override
	public void deleteTransaction() {
		lock.lock();
		try {

			if (transactionCache.size() != 0) {
				transactionCache.clear();
			}
			log.info("Transaction Cache size : {}", transactionCache.size());
		} finally {
			lock.unlock();
		}
	}

	private List<Transaction> getLatestTransactions() {
		lock.lock();
		try {
			long startingEpochSecond = now().minusSeconds(DataConstants.TIME_INTERVAL).toEpochMilli();
			taskexecutor.execute(new CleanUpTask(transactionCache, startingEpochSecond));
			return transactionCache.tailMap(startingEpochSecond).values().parallelStream()
					.flatMap(Collection::parallelStream).collect(toList());
		} finally {
			lock.unlock();
		}
	}
	/*
	 * This is only used to retrieve the cache to check the content
	 */

	protected NavigableMap<Long, List<Transaction>> getTransactionsCache() {
		return Collections.unmodifiableNavigableMap(transactionCache);
	}

}
