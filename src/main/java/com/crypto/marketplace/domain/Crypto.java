package com.crypto.marketplace.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.Objects;

/**
 * Create By Muzammil on 10/06/2020.
 */
@Entity
public class Crypto {

    @javax.persistence.Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private CoinType coinType;
    private Double orderQty;
    private Double pricePerCoin;
    private TransactionType transactionType;

    //For summary -> will be modular if summary entity is separate
    public Crypto(Double orderQty, Double pricePerCoin, TransactionType transactionType) {
        this.orderQty = orderQty;
        this.pricePerCoin = pricePerCoin;
        this.transactionType = transactionType;
    }

    public Crypto(Long userId,
                  CoinType coinType,
                  Double orderQty,
                  Double pricePerCoin,
                  TransactionType transactionType) {
        this.userId = userId;
        this.coinType = coinType;
        this.orderQty = orderQty;
        this.pricePerCoin = pricePerCoin;
        this.transactionType = transactionType;
    }

    public Crypto() {
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userID) {
        this.userId = userID;
    }

    public CoinType getCoinType() {
        return coinType;
    }

    public void setCoinType(CoinType coinType) {
        this.coinType = coinType;
    }

    public Double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Double orderQty) {
        this.orderQty = orderQty;
    }

    public Double getPricePerCoin() {
        return pricePerCoin;
    }

    public void setPricePerCoin(Double pricePerCoin) {
        this.pricePerCoin = pricePerCoin;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Crypto{" +
                "id=" + id +
                ", userId=" + userId +
                ", coinType=" + coinType +
                ", orderQty=" + orderQty +
                ", pricePerCoin=" + pricePerCoin +
                ", transactionType=" + transactionType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crypto crypto = (Crypto) o;
        return Objects.equals(id, crypto.id) &&
                Objects.equals(userId, crypto.userId) &&
                coinType == crypto.coinType &&
                orderQty.equals(crypto.orderQty) &&
                pricePerCoin.equals(crypto.pricePerCoin) &&
                transactionType == crypto.transactionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, coinType, orderQty, pricePerCoin, transactionType);
    }

}
