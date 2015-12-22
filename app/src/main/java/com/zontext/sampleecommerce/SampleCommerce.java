package com.zontext.sampleecommerce;

import android.provider.BaseColumns;

/**
 * Created by Hairizuan on 20/6/2015.
 */
public final class SampleCommerce {
    public SampleCommerce(){}

    public static abstract class Product implements BaseColumns{
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME_PRODUCT_ID = "productid";
        public static final String COLUMN_NAME_PRODUCT_SKU = "productsku";
        public static final String COLUMN_NAME_PRODUCT_DESC = "productdesc";
        public static final String COLUMN_NAME_PRODUCT_PRICE = "productprice";
    }

    public static abstract  class Cart implements BaseColumns{
        public static final String TABLE_NAME = "cart";
        public static final String COLUMN_NAME_PRODUCT_DESC = "productdesc";
        public static final String COLUMN_NAME_PRODUCT_PRICE = "productprice";
        public static final String COLUMN_NAME_PRODUCT_QTY = "productqty";
    }
}



