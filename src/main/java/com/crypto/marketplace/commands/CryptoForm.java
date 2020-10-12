package com.crypto.marketplace.commands;


import com.crypto.marketplace.domain.CoinType;

/**
 * Create By Muzammil on 10/06/2020.
 */
public class CryptoForm {
    private Long   userID;
    private String coinType;
    private Double orderQty;
    private Double pricePerCoin;

    public CryptoForm() {
    }

    private String transactionType;

    public CryptoForm(Long userID, String coinType, Double orderQty, Double pricePerCoin, String transactionType) {
        this.userID = userID;
        this.coinType = coinType;
        this.orderQty = orderQty;
        this.pricePerCoin = pricePerCoin;
        this.transactionType = transactionType;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
