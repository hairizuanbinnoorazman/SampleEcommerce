package com.zontext.sampleecommerce;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

/**
 * Created by Hairizuan on 2/7/2015.
 */
public class GTM {

    // Do not push actually error message as that is PII
    public static void pushException(Context context, String error_msg, String screenName){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("trackException", DataLayer.mapOf("exceptionDescription", error_msg, "screenName", screenName));
        Log.d("Exception", error_msg);
    }

    public static void pushEvent(Context context, String event_category, String event_action, String event_label, String event_value){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("trackEvent", DataLayer.mapOf("eventDetails.category", event_category, "eventDetails.action", event_action, "eventDetails.label", event_label, "eventDetails.value", event_value));
    }

    public static void pushEvent_noValue(Context context, String screenName, String event_category, String event_action, String event_label){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("trackEvent", DataLayer.mapOf("screenName", screenName, "eventDetails.category", event_category, "eventDetails.action", event_action, "eventDetails.label", event_label));
    }

    public static void pushEvent_userId(Context context, String screenName, String event_category, String event_action, String event_label, String userid){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("userId_trackEvent", DataLayer.mapOf("screenName", screenName, "eventDetails.category", event_category, "eventDetails.action", event_action, "eventDetails.label", event_label, "userID", userid));
    }

    public static void pushEvent_ANDuserId(Context context, String screenName, String event_category, String event_action, String event_label, String userid){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("ANDuserId_trackEvent", DataLayer.mapOf("screenName", screenName, "eventDetails.category", event_category, "eventDetails.action", event_action, "eventDetails.label", event_label, "userID", userid));
    }

    public static void pushScreen(Context context, String screenName){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("openScreen", DataLayer.mapOf("screenName", screenName, "userID", "1234"));
    }

    public static void pushEvent1(Context context, String event_category){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("trackEvent",DataLayer.mapOf("eventDetails.category",event_category));
    }

    public static void addProduct(Context context){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.push("ecommerce",
                DataLayer.mapOf(
                        "currencyCode", "MYR",
                        "detail", DataLayer.mapOf(
                                "actionField",
                                DataLayer.mapOf(
                                        "list", "Sides"),               // detail actions have an optional list property.
                                "products",
                                DataLayer.listOf(
                                        DataLayer.mapOf(
                                                "name", "french_fries",   // Name or ID is required.
                                                "id", "12345",
                                                "price", "5.05",
                                                "brand", "McD",
                                                "category", "Sides",
                                                "variant", "large"
                                        )
                                )
                        )
                )
        );
    }

    public static void pushEventEcommerceMethod1(Context context){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("trackEvent",
                DataLayer.mapOf(
                        "ecommerce", DataLayer.mapOf(
                                "click", DataLayer.mapOf(
                                        "actionField", DataLayer.mapOf(
                                                "list", "Search Results"),                    // Optional list property.
                                        "products", DataLayer.listOf(
                                                DataLayer.mapOf(
                                                        "name", "Triblend Android T-Shirt",       // Name or ID is required.
                                                        "id", "12345",
                                                        "price", "15.25",
                                                        "brand", "Google",
                                                        "category", "Apparel",
                                                        "variant", "Gray"))))));
    }



    public static void addPurchaseModified_Main(Context context){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.push("openScreen", DataLayer.mapOf("screenName", "main Screen", "ecommerce",
                DataLayer.mapOf(
                        "currencyCode", "MYR",
                        "detail", DataLayer.mapOf(
                                "actionField",
                                DataLayer.mapOf(
                                        "list", "Sides"),               // detail actions have an optional list property.
                                "products",
                                DataLayer.listOf(
                                        DataLayer.mapOf(
                                                "name", "french_fries",   // Name or ID is required.
                                                "id", "12345",
                                                "price", "5.05",
                                                "brand", "McD",
                                                "category", "Sides",
                                                "variant", "large"
                                        )
                                )
                        )
                )
        ));
    }

    public static void purchase(Context context){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.push(
                "ecommerce",
                DataLayer.mapOf(
                        "currencyCode", "MYR",
                        "purchase", DataLayer.mapOf(
                                "actionField", DataLayer.mapOf(
                                        "id", "T12345",                             // Transaction ID. Required for purchases and refunds.
                                        "affiliation", "Online Store",
                                        "revenue", "25.65",                         // Total transaction value (incl. tax and shipping)
                                        "tax", "0",
                                        "shipping", "0",
                                        "coupon", ""),
                                "products", DataLayer.listOf(                   // List of productFieldObjects.
                                        DataLayer.mapOf(
                                                "name", "french_fries",     // Name or ID is required.
                                                "id", "12345",
                                                "price", "5.05",
                                                "brand", "McD",
                                                "category", "Sides",
                                                "variant", "large",
                                                "quantity", 2,
                                                "coupon", ""
                                        ),                          // Optional fields may be omitted or set to empty string.
                                        DataLayer.mapOf(
                                                "name", "ayam_mcd_2_pcs",
                                                "id", "67890",
                                                "price", "15.55",
                                                "brand", "McD",
                                                "category", "meal",
                                                "variant", "value_meal",
                                                "quantity", 1
                                        )
                                )
                        )
                )
        );

    }

    public static void pushEcommerce(Context context){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("productClick", DataLayer.mapOf("ecommerce",
                DataLayer.mapOf("click", DataLayer.mapOf("actionField", DataLayer.mapOf("list", "Search Results"),
                        "products", DataLayer.listOf(DataLayer.mapOf("name", "french_fries",
                                "id", "12345",
                                "price", "5.05",
                                "brand", "McD",
                                "category", "Sides",
                                "variant", "large"))))));
    }

    public static void pushEventEcommerceMethod2(Context context){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("productClick",
                DataLayer.mapOf(
                        "ecommerce", DataLayer.mapOf(
                                "click", DataLayer.mapOf(
                                        "actionField", DataLayer.mapOf(
                                                "list", "Search Results"),                    // Optional list property.
                                        "products", DataLayer.listOf(
                                                DataLayer.mapOf(
                                                        "name", "THE ULTIMATE T-Shirt",       // Name or ID is required.
                                                        "id", "12345",
                                                        "price", "15.25",
                                                        "brand", "Google",
                                                        "category", "Apparel",
                                                        "variant", "Gray"))))));
    }

    public static void pushEventEcommerceMethodSpecial(Context context){
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("trackEvent",
                DataLayer.mapOf(
                        "screenname", "TRIBELEND",
                        "eventDetails.category", "TRIBELEND",
                        "eventDetails.action", "TRIBELEND",
                        "eventDetails.label", "TRIBELEND",
                        "ecommerce", DataLayer.mapOf(
                                "click", DataLayer.mapOf(
                                        "actionField", DataLayer.mapOf(
                                                "list", "Search Results"),                    // Optional list property.
                                        "products", DataLayer.listOf(
                                                DataLayer.mapOf(
                                                        "name", "Triblend Android T-Shirt",       // Name or ID is required.
                                                        "id", "12345",
                                                        "price", "15.25",
                                                        "brand", "Google",
                                                        "category", "Apparel",
                                                        "variant", "Gray"))))));
    }


}
