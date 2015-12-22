package com.zontext.sampleecommerce;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Account extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Button logOutButton = (Button) findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logOutIntent = new Intent();
                SharedPreferences settings = getSharedPreferences("SamplePrefs", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.commit();

                logOutIntent.setClass(Account.this, MainActivity.class);
                startActivity(logOutIntent);
                Toast.makeText(Account.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
