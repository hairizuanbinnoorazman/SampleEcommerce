package com.zontext.sampleecommerce;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity{

    private static final String CONTAINER_ID = "GTM-NCWQXZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String projectToken = "ab0736526588a100dacc18453390ebab";
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);

        TagManager tagManager = TagManager.getInstance(this);

        // Modify the log level of the logger to print out not only
        // warning and error messages, but also verbose, debug, info messages.
        tagManager.setVerboseLoggingEnabled(true);

        PendingResult<ContainerHolder> pending =
                tagManager.loadContainerPreferFresh(CONTAINER_ID,
                        R.raw.gtm_default_container);

        // The onResult method will be called as soon as one of the following happens:
        //     1. a saved container is loaded
        //     2. if there is no saved container, a network container is loaded
        //     3. the request times out. The example below uses a constant to manage the timeout period.
        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                Container container = containerHolder.getContainer();
                if (!containerHolder.getStatus().isSuccess()) {
                    Log.e("Sample Ecommerce App", "failure loading container");
                    return;
                }

                Log.d("GTM Test", Boolean .toString(container.isDefault()));
                Log.d("GTM Test", Long.toString(container.getLastRefreshTime()));
                Log.d("GTM Test", container.getContainerId());

                ContainerHolderSingleton.setContainerHolder(containerHolder);
                ContainerLoadedCallback.registerCallbacksForContainer(container);
                containerHolder.setContainerAvailableListener(new ContainerLoadedCallback());
            }
        }, 2, TimeUnit.SECONDS);

        //Container container = ContainerHolderSingleton.getContainerHolder().getContainer();

        Log.e("GTM Test", "start");
        //Log.e("GRADLE TEST", getString(R.string.AppBuild));
        //Log.e("GTM Test", container.getContainerId());
        //Log.e("GTM Test", Long.toString(container.getLastRefreshTime()));
        //Log.e("GTM Test", Boolean.toString(container.isDefault()));
        String screenName = "mainScreen";
        DataLayer dataLayer = TagManager.getInstance(this).getDataLayer();
        dataLayer.push("openScreen", DataLayer.mapOf("screenName", screenName));
        tagManager.dispatch();

        mixpanel.getPeople().identify("1234");
        mixpanel.getPeople().set("Plan", "PREMIUM");
        MixpanelAPI.People people = mixpanel.getPeople();
        people.identify("1234");
        people.initPushHandling("AIzaSyAMdcEuiS5HrT7sNJDSN4-mwe9MaCdc7vo");


        // Change the title of the app
        //setTitle("New Title");
        SampleCommerceDbHelper mDbHelper = new SampleCommerceDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //make sure that the insert operation only happen once


        // Add the data to the new database of product
        SharedPreferences settings = getSharedPreferences("SamplePrefs", 0);
        boolean firstTime = settings.getBoolean("firstTimeOpenApp", true);
        if(firstTime){
            mDbHelper.setSampleProductList();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstTimeOpenApp", false);
            editor.commit();
        }
        /*
            Cursor resultSet = db.rawQuery("Select * from " + SampleCommerce.Product.TABLE_NAME, null);
            resultSet.moveToFirst();
            String product_sku = resultSet.getString(1);
            String product_desc = resultSet.getString(2);
            int product_price = resultSet.getInt(3);

            Toast.makeText(this, product_sku, Toast.LENGTH_LONG).show();
            Toast.makeText(this, product_desc, Toast.LENGTH_LONG).show();
            Toast.makeText(this, Integer.toString(product_price), Toast.LENGTH_LONG).show();
        */

        ArrayList<String> stringArrayList = mDbHelper.getProductList();
        final ListView listView = (ListView) findViewById(R.id.productListView);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringArrayList);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = listView.getItemAtPosition(position).toString();
                Intent viewProductDetails = new Intent();
                viewProductDetails.setClass(MainActivity.this, ProductDetails.class);
                viewProductDetails.putExtra("product", item);
                startActivity(viewProductDetails);
                //Toast.makeText(MainActivity.this, listView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        updateMenuTitles(menu);
        return true;
    }

    private void updateMenuTitles(Menu menu){
        MenuItem mainActivityMenuItem = menu.findItem(R.id.action_login);
        SharedPreferences settings = getSharedPreferences("SamplePrefs", 0);
        boolean isLoggedIn = settings.getBoolean("isLoggedIn", false);
        if(!isLoggedIn){
            mainActivityMenuItem.setTitle("Log In");
        } else {
            mainActivityMenuItem.setTitle("Account");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_settings:
                return true;
            case  R.id.action_login:
                Intent loginActivity = new Intent();
                SharedPreferences settings = getSharedPreferences("SamplePrefs", 0);
                boolean isLoggedIn = settings.getBoolean("isLoggedIn", false);
                if(!isLoggedIn){
                    loginActivity.setClass(MainActivity.this,Login.class);
                    GTM.pushEvent(this, "LogIn", "click", "login_main_screen","12");
                    startActivity(loginActivity);
                } else {
                    loginActivity.setClass(MainActivity.this,Account.class);
                    GTM.pushEvent(this, "Account", "click","account_main_screen","12");
                    startActivity(loginActivity);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private static class ContainerLoadedCallback implements ContainerHolder.ContainerAvailableListener {
        @Override
        public void onContainerAvailable(ContainerHolder containerHolder, String containerVersion) {
            // We load each container when it becomes available.
            Container container = containerHolder.getContainer();
            registerCallbacksForContainer(container);
        }

        public static void registerCallbacksForContainer(Container container) {
            // Register two custom function call macros to the container.
            container.registerFunctionCallMacroCallback("increment", new CustomMacroCallback());
            container.registerFunctionCallMacroCallback("mod", new CustomMacroCallback());
            // Register a custom function call tag to the container.
            container.registerFunctionCallTagCallback("custom_tag", new CustomTagCallback());
        }
    }

    private static class CustomMacroCallback implements Container.FunctionCallMacroCallback {
        private int numCalls;

        @Override
        public Object getValue(String name, Map<String, Object> parameters) {
            if ("increment".equals(name)) {
                Log.d("Function Call", "INCREMENT FUNCTION CALL");
                return "lolx";
            } else if ("mod".equals(name)) {
                Log.d("Function Call Mod", "MOD Function Call");
                Log.d("Function Call Mod", (String) parameters.get("key1"));
                return (Long) parameters.get("key1") % Integer.valueOf((String) parameters.get("key2"));
            } else {
                throw new IllegalArgumentException("Custom macro name: " + name + " is not supported.");
            }
        }
    }

    private static class CustomTagCallback implements Container.FunctionCallTagCallback {
        @Override
        public void execute(String tagName, Map<String, Object> parameters) {
            // The code for firing this custom tag.
            Log.i("CuteAnimals", "Custom function call tag :" + tagName + " is fired.");
        }
    }



}
