package edu.t1.chernykh.service;

import edu.t1.chernykh.entity.Transaction;

public interface TransactionProcessingService {
    boolean doApprovalTransactionProcess(Transaction transaction);
    void doCancelationTransactionalProcess(Long id);
}
