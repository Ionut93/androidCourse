package com.ionut.gnb.classes;

import java.math.BigDecimal;

/**
 * Created by Ionut on 2/16/2016.
 */
public class ConversionRates {
    private String from;
    private String to;
    private BigDecimal rate;

    public ConversionRates(String from, BigDecimal rate, String to) {
        this.from = from;
        this.rate = rate;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


}
