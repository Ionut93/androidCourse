package com.ionut.gnb.classes;

import java.math.BigDecimal;

/**
 * Created by Ionut on 2/16/2016.
 */
public class Trades {
    private String sku;
    private BigDecimal amount;
    private String currency;

    public Trades(BigDecimal amount, String currency, String sku) {
        this.amount = amount;
        this.currency = currency;
        this.sku = sku;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
