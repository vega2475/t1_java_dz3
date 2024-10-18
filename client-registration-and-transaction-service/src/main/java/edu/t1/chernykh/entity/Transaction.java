package edu.t1.chernykh.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "is_error")
    private Boolean isError;

    public Transaction(Double amount, Account account, TransactionType type, Boolean isError) {
        this.amount = amount;
        this.account = account;
        this.type = type;
        this.isError = isError;
    }

    public Transaction() {}

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
