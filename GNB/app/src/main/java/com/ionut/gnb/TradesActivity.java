package com.ionut.gnb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ionut.gnb.classes.ConversionRates;
import com.ionut.gnb.classes.Trades;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TradesActivity extends AppCompatActivity {

    private ListView listView;
    private TextView textView_sum;
    private List<String> tradesList = new ArrayList<>();
    private static boolean found = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trades);
        Intent intent = getIntent();
        String tradeString = intent.getStringExtra("TRADE_NAME");
        Log.i("TradesActivity",intent.getStringExtra("TRADE_NAME"));
        textView_sum = (TextView) findViewById(R.id.textView_sum);
        listView = (ListView) findViewById(R.id.listView);
        BigDecimal total = new BigDecimal("0");
        for ( int i = 0; i < MainActivity.trades.size(); i++ ) {
            Trades trade = MainActivity.trades.get(i);
            if ( trade.getSku().equals(tradeString) ) {
                tradesList.add(tradeString + " : " + trade.getAmount() + " " + trade.getCurrency() );
                if (trade.getCurrency().equals("EUR")) {
                    total = total.add(trade.getAmount());
                }
                else {
                    BigDecimal r = findConversionRate(trade.getCurrency(), "EUR");
                    //r.setScale(2 , BigDecimal.ROUND_HALF_EVEN);
                    //total += trade.getAmount() * r;
                    BigDecimal valueToAdd = trade.getAmount().multiply(r);
                    valueToAdd = valueToAdd.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    total = total.add ( valueToAdd );
                    total = total.setScale (2, BigDecimal.ROUND_HALF_EVEN);
                    found = false;
                }
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tradesList);
        listView.setAdapter(arrayAdapter);
        textView_sum.setText(String.valueOf(total));

    }

    public BigDecimal findConversionRate(String From, String To) {
        BigDecimal rate = new BigDecimal("1");
        for (int i = 0; i < MainActivity.conversionRates.size();i++) {
            ConversionRates conversionRates = MainActivity.conversionRates.get(i);
            if( conversionRates.getFrom().equals(From)) {
                if (conversionRates.getTo().equals(To)) {
                    found = true;
                    return conversionRates.getRate();
                }
            }
        }
        // there is no direct conversion found -> we look for
        for ( int i = 0; i < MainActivity.conversionRates.size(); i++ ) {
            String leftCurrency;
            ConversionRates conversionRates = MainActivity.conversionRates.get(i);
            if (conversionRates.getTo().equals(To)) {
                leftCurrency = conversionRates.getFrom();
                BigDecimal r = findConversionRate(From, leftCurrency);
                rate = rate.multiply(conversionRates.getRate().multiply(r));  // Trade Currency -> Currency that has rate
                if ( found ) {
                    return rate;
                }
                else {
                    rate = new BigDecimal("1");
                }
            }
        }
        return new BigDecimal("-1");
    }
}

 /*if( conversionRates.getFrom().equals(From)) {
                if (conversionRates.getTo().equals("EUR")) {
                    return conversionRates.getRate();
                }
                else {
                    String thirdPartCurrency = conversionRates.getTo();
                    for (int j = 0; j < MainActivity.conversionRates.size(); j++) {
                        ConversionRates secondRate = MainActivity.conversionRates.get(j);
                        if( secondRate.getFrom().equals(thirdPartCurrency) && secondRate.getTo().equals("EUR")) {
                            float rateThirdParty = conversionRates.getRate() * secondRate.getRate();
                            ConversionRates newConversionRate = new ConversionRates(From, rateThirdParty, "EUR" );
                            MainActivity.conversionRates.add(newConversionRate);
                            return rateThirdParty;
                        }
                    }
                }
            }*/
