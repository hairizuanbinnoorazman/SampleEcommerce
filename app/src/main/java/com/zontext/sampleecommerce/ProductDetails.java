package com.zontext.sampleecommerce;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.TagManager;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        String projectToken = "ab0736526588a100dacc18453390ebab";
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);

        try{
            JSONObject props = new JSONObject();
            props.put("Gender", "Female");
            props.put("Logged in", false);
            mixpanel.track("Product Details - show", props);
        } catch(JSONException e){
            Log.e("Product Details", "Failed to send data");
        }

        String screenName = "productDetailsScreen";

        Container container = ContainerHolderSingleton.getContainerHolder().getContainer();
        String GTMTest = container.getString("test");
        String GTMTest1 = container.getString("test1");
        Log.d("GTM VC Test", GTMTest);
        Log.d("GTM VC Test", GTMTest1);

        final SampleCommerceDbHelper mDbHelper = new SampleCommerceDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        final String product = getIntent().getStringExtra("product");

        String productSKU = mDbHelper.getProductSKU(product);
        int productPrice = mDbHelper.getProductPrice(product);
        //Toast.makeText(ProductDetails.this, productSKU, Toast.LENGTH_SHORT).show();
        TextView textViewProductDesc = (TextView) findViewById(R.id.textViewProductDesc);
        TextView textViewProductSKU = (TextView) findViewById(R.id.textViewSKU);
        TextView textViewProductPrice = (TextView) findViewById(R.id.textViewPrice);

        textViewProductDesc.setText(product);
        textViewProductSKU.setText(productSKU);
        textViewProductPrice.setText(Integer.toString(productPrice));

        final TagManager tagManager = TagManager.getInstance(this);

        Button buttonAddToCart = (Button) findViewById(R.id.buttonAddToCart);
        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDbHelper.addProduct(product);
                Intent cartIntent = new Intent();
                cartIntent.setClass(ProductDetails.this,Cart.class );
                //GTM.pushEvent(ProductDetails.this, "Add to Cart", "Click", "Add fruit", "1");
                //GTM.addProduct(ProductDetails.this);
                //GTM.pushEcommerce(ProductDetails.this);
                //GTM.pushException(ProductDetails.this, "TRIAL ERROR MSG", "Product Details");

                GTM.pushScreen(ProductDetails.this, "Product Details");
                // One way of execution
                // Where GTM is pushed with the same name with an event. Event is fired as well
                GTM.pushEvent_noValue(ProductDetails.this, "Product Details DIFF", "Product Button", "Product Buy Button", "purchase click");
                GTM.pushEventEcommerceMethod1(ProductDetails.this);



                tagManager.dispatch();
                Log.d("First GA dispatch", "Product Buy Button");

                // Second way of execution
                // Custom event fired alone.
                GTM.pushEvent_noValue(ProductDetails.this, "Product Details ULTIMATE", "Product Button ULTIMATE", "Product Buy Button ULTIMATE", "purchase click ULTIMATE");
                GTM.pushEventEcommerceMethod2(ProductDetails.this);

                GTM.pushEventEcommerceMethodSpecial(ProductDetails.this);
                tagManager.dispatch();

                GTM.pushEvent_userId(ProductDetails.this, "Product Details ULTIMATE USERID", "USERID", "USERID", "USERID", "T12345");
                tagManager.dispatch();

                GTM.pushEvent_ANDuserId(ProductDetails.this, "Product Details ULTIMATE ANDUSERID", "ANDUSERID", "ANDUSERID", "ANDUSERID", "Zdfasdfwe");
                tagManager.dispatch();

                tagManager.dispatch();
                Log.d("Second GA dispatch", "Product w/o buy event");

                startActivity(cartIntent);
                finish();
            }
        });
    }


}
