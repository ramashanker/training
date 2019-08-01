package com.rama.transaction.app.service;

import com.rama.transaction.app.dto.Statistics;
import com.rama.transaction.app.dto.Transaction;

public interface TransactionService {
	void createTransaction(Transaction transaction);

	Statistics getStatistics();

	void deleteTransaction();

}
