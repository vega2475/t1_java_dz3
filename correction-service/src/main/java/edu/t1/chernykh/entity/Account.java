package edu.t1.chernykh.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    public Account(Long clientId, AccountType type, Double balance, Boolean isBlocked) {
        this.clientId = clientId;
        this.type = type;
        this.balance = balance;
        this.isBlocked = isBlocked;
    }

    public Account(){}

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }
}
