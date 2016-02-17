package com.ionut.gnb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ionut.gnb.classes.ConversionRates;
import com.ionut.gnb.classes.MyApplication;
import com.ionut.gnb.classes.Trades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private String urlTrades = "http://gnb.dev.airtouchmedia.com/transactions.json";
    private String urlRates = "http://gnb.dev.airtouchmedia.com/rates.json";
    public static List<Trades> trades = new ArrayList<>();
    public static List<ConversionRates> conversionRates = new ArrayList<>();
    private Set<String> tradeNames = new HashSet<>();
    private ListView listView;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        listView = (ListView) findViewById(R.id.listView);
        makeJsonArrayRatesRequest();
        makeJsonArrayTradesRequest();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, TradesActivity.class);
                intent.putExtra("TRADE_NAME", listView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
    }

    private void makeJsonArrayRatesRequest() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlRates, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        ConversionRates conversionRate = new ConversionRates( jsonObject.getString("from"),
                                new BigDecimal(jsonObject.getString("rate").toString()),
                                jsonObject.getString("to"));
                        conversionRates.add(conversionRate);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                Log.i("Finished","Finished Rates");
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error","Errors Rates");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept", "application/json; charset=utf-8");
                return headers;
            }
        };
        MyApplication.getInstance().addToRequestQueue(jsonArrayRequest, "jsonArrayRequest");
    }
    private void makeJsonArrayTradesRequest() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlTrades, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        Log.i("JSON",jsonObject.getString("sku"));
                        Trades trade = new Trades( new BigDecimal(jsonObject.getString("amount").toString()),
                                jsonObject.getString("currency"),
                                jsonObject.getString("sku"));
                        trades.add(trade);
                        tradeNames.add(trade.getSku());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                Log.i("Finished","Finished Trades");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1,tradeNames.toArray(new String[tradeNames.size()]));
                listView.setAdapter(arrayAdapter);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Errors Trades");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept", "application/json; charset=utf-8");
                return headers;
            }
        };
        MyApplication.getInstance().addToRequestQueue(jsonArrayRequest, "jsonArrayRequest");


    }

    public void viewRates(View view) {
        Intent intent = new Intent(context, RatesActivity.class);
        startActivity(intent);
    }
}


