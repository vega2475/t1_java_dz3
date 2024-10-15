package edu.t1.chernykh.controller;

import edu.t1.chernykh.service.TransactionProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    private final TransactionProcessingService transactionProcessingService;

    @Autowired
    public TransactionController(TransactionProcessingService transactionProcessingService) {
        this.transactionProcessingService = transactionProcessingService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelTransaction(@PathVariable(name = "id") Long id){
        try {
            transactionProcessingService.doCancelationTransactionalProcess(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception){
            //TODO handle
            throw exception;
        }

    }
}
