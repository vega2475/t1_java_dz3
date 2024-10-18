package edu.t1.chernykh.service.implementation;

import edu.t1.chernykh.client.AccountUnlockClient;
import edu.t1.chernykh.dto.AccountDto;
import edu.t1.chernykh.entity.Transaction;
import edu.t1.chernykh.entity.TransactionType;
import edu.t1.chernykh.repository.AccountRepository;
import edu.t1.chernykh.repository.TransactionRepository;
import edu.t1.chernykh.service.AccountUnlockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultAccountUnlockService implements AccountUnlockService {

    private static final Logger log = LoggerFactory.getLogger(DefaultAccountUnlockService.class);
    private final AccountUnlockClient accountUnlockClient;
    private final TransactionRepository transactionRepository;

    public DefaultAccountUnlockService(AccountUnlockClient accountUnlockClient, TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.accountUnlockClient = accountUnlockClient;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void processMessage(AccountDto accountDto, Long transactionId) {
        String response = accountUnlockClient.sendUnlockRequest(accountDto, transactionId);
        if (response.equals("Account successfully unlocked")) {
            transactionRepository.deleteById(transactionId);
        } else {
            // Непонятно как сохранить транзакцию в БД если у нас нет по ней информации кроме Id и так как нам нужно сохранить ее в БД то в самой БД ее быть не может
            /*
            Раз в установленный параметром период времени запускать функцию, которая достает из БД
            записи о таких транзакциях и повторно отправить на обработку в сервис 1.
            Соответственно такой функционал не может быть добавлен так как нечего брать из БД
             */
            log.warn("Account is not unblocked");
        }
    }
}
