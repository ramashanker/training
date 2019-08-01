package com.rama.transaction.app.cleanup;

import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.function.BiConsumer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rama.transaction.app.dto.Transaction;

@Component
@Scope("prototype")
public class CleanUpTask implements Runnable {
	ConcurrentNavigableMap<Long, List<Transaction>> transactionCache;
	long startingESecond;

	public CleanUpTask(ConcurrentNavigableMap<Long, List<Transaction>> transactionCache, long startingESecond) {
		this.transactionCache = transactionCache;
		this.startingESecond = startingESecond;
	}

	@Override
	public void run() {
		ConcurrentNavigableMap<Long, List<Transaction>> subMap = transactionCache.headMap(startingESecond);
		subMap.forEach(new BiConsumer<Long, List<Transaction>>() {
			@Override
			public void accept(Long key, List<Transaction> u) {
				transactionCache.remove(key);
			}
		});
	}
}