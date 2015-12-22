package com.zontext.sampleecommerce;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Check for correct email and password
        final EditText emailEditText = (EditText) findViewById(R.id.editEmailAddress);
        final EditText passwordEditText = (EditText) findViewById(R.id.editPassword);
        Button loginButton = (Button) findViewById(R.id.buttonLogIn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailEditText.getText().toString().equals(getString(R.string.email)) &&
                        passwordEditText.getText().toString().equals(getString(R.string.pwd))){
                    SharedPreferences settings = getSharedPreferences("SamplePrefs", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.commit();

                    Intent mainActivityIntent = new Intent();
                    mainActivityIntent.setClass(Login.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                    Login.this.finish();
                } else {
                    Toast.makeText(getParent(), "Wrong Email or Wrong password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
