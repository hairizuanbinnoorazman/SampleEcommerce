package com.zontext.sampleecommerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

public class PersonalDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        String screenName = "personalDetails";
        DataLayer dataLayer = TagManager.getInstance(this).getDataLayer();
        dataLayer.pushEvent("openScreen", DataLayer.mapOf("screenName", screenName));

        final EditText editTextAddress1 = (EditText) findViewById(R.id.editTextAddress1);
        final EditText editTextAddress2 = (EditText) findViewById(R.id.editTextAddress2);
        final EditText editTextPostalCode = (EditText) findViewById(R.id.editTextPostalCode);

        Button buttonMastercard = (Button) findViewById(R.id.buttonMastercard);
        Button buttonVisa = (Button) findViewById(R.id.buttonVisa);

        buttonMastercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GTM.pushEvent_noValue(PersonalDetails.this,"Payment","Click","MasterCard");
                Intent mastercardIntent = new Intent();
                mastercardIntent.setClass(PersonalDetails.this, Summary.class);
                mastercardIntent.putExtra("address1", editTextAddress1.getText().toString());
                mastercardIntent.putExtra("address2", editTextAddress2.getText().toString());
                mastercardIntent.putExtra("postalCode", editTextPostalCode.getText().toString());
                mastercardIntent.putExtra("card", "mastercard");
                startActivity(mastercardIntent);
                finish();
            }
        });

        buttonVisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visaIntent = new Intent();
                visaIntent.setClass(PersonalDetails.this, Summary.class);
                visaIntent.setClass(PersonalDetails.this, Summary.class);
                visaIntent.putExtra("address1", editTextAddress1.getText().toString());
                visaIntent.putExtra("address2", editTextAddress2.getText().toString());
                visaIntent.putExtra("postalCode", editTextPostalCode.getText().toString());
                visaIntent.putExtra("card", "visa");
                startActivity(visaIntent);
                finish();
            }
        });

    }
}
