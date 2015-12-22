package com.zontext.sampleecommerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThankYou extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        GTM.pushScreen(this, "Thank You");
        GTM.purchase(ThankYou.this);

        // Using the button, send the phone screen back to product page
        Button buttonThankYou = (Button) findViewById(R.id.buttonThankYou);
        buttonThankYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent();
                mainActivityIntent.setClass(ThankYou.this, MainActivity.class);
                ThankYou.this.finish();
            }
        });
    }


}
