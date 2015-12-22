package com.zontext.sampleecommerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Summary extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView textViewCartTotal = (TextView) findViewById(R.id.textViewCartTotal);
        TextView textViewAddress1 = (TextView) findViewById(R.id.textViewAddress1);
        TextView textViewAddress2 = (TextView) findViewById(R.id.textViewAddress2);
        TextView textViewPostalCode = (TextView) findViewById(R.id.textViewPostalCode);
        TextView textViewCard = (TextView) findViewById(R.id.textViewCard);

        String address1 = getIntent().getStringExtra("address1");
        String address2 = getIntent().getStringExtra("address2");
        String postalCode = getIntent().getStringExtra("postalCode");
        String card = getIntent().getStringExtra("card");

        final SampleCommerceDbHelper mDbHelper = new SampleCommerceDbHelper(getApplicationContext());
        textViewCartTotal.setText(Integer.toString(mDbHelper.getCartPriceTotal()));
        textViewAddress1.setText(address1);
        textViewAddress2.setText(address2);
        textViewPostalCode.setText(postalCode);
        textViewCard.setText(card);

        Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDbHelper.deleteCartData();

                Intent thankYouIntent = new Intent();
                thankYouIntent.setClass(Summary.this, ThankYou.class);
                startActivity(thankYouIntent);
                finish();
            }
        });
    }
}
