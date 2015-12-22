package com.zontext.sampleecommerce;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Cart extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final SampleCommerceDbHelper mDbHelper = new SampleCommerceDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ArrayList<String> arrayListCart = mDbHelper.getCart();
        ListView listViewCart = (ListView) findViewById(R.id.listViewCart);

        Log.d("LOL",Integer.toString(arrayListCart.size()));

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListCart);
        listViewCart.setAdapter(arrayAdapter);
        listViewCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Delete items from the list - Create notification
                /*
                String item = listView.getItemAtPosition(position).toString();
                Intent viewProductDetails = new Intent();
                viewProductDetails.setClass(Cart.this, ProductDetails.class);
                viewProductDetails.putExtra("product", item);
                startActivity(viewProductDetails);
                */
                //Toast.makeText(MainActivity.this, listView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Button deleteDataCartButton = (Button) findViewById(R.id.buttonDeleteCart);
        Button deliveryDetailsButton = (Button) findViewById(R.id.buttonDeliveryDetails);

        deliveryDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryDetails = new Intent();
                deliveryDetails.setClass(Cart.this, PersonalDetails.class);
                startActivity(deliveryDetails);
                finish();
            }
        });

        deleteDataCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDbHelper.deleteCartData();
                Intent refreshCart = new Intent();
                refreshCart.setClass(Cart.this, Cart.class);
                startActivity(refreshCart);
                finish();
            }
        });

        TextView textViewCartTotal = (TextView) findViewById(R.id.textViewCartTotal);
        int cartTotal = mDbHelper.getCartPriceTotal();
        textViewCartTotal.setText(Integer.toString(cartTotal));

    }


}
