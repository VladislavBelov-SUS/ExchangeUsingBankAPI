package com.exchangeusingbankapi;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "ExchangeRate")
@Data
@NoArgsConstructor
public class ExchangeRate {
    @Id
    @GeneratedValue
    private long id;
    private Date date;
    private String baseCurrency;
    private String currency;
    private Float saleRateNB;
    private Float purchaseRateNB;
    private Float saleRate;
    private Float purchaseRate;

    public void setDate(String date) {
        try{
            this.date= new Date(new SimpleDateFormat("dd.MM.yyyy").parse(date).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
