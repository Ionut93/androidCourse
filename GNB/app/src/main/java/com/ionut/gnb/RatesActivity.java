package com.ionut.gnb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ionut.gnb.classes.ConversionRates;

import java.util.ArrayList;
import java.util.List;

public class RatesActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> listOfConversionRates = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);

        listView = (ListView) findViewById(R.id.listView2);
        for ( int i = 0 ; i < MainActivity.conversionRates.size(); i++) {
            ConversionRates conversionRates = MainActivity.conversionRates.get(i);
            listOfConversionRates.add( conversionRates.getFrom() + " -> " + conversionRates.getTo() + " : " + conversionRates.getRate() );
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfConversionRates);
        listView.setAdapter(arrayAdapter);
    }
}
