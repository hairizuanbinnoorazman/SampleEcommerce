package com.zontext.sampleecommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Hairizuan on 20/6/2015.
 */
public class SampleCommerceDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            SampleCommerce.Product.TABLE_NAME + " (" +
            SampleCommerce.Product._ID + " INTEGER PRIMARY KEY," +
            SampleCommerce.Product.COLUMN_NAME_PRODUCT_SKU + TEXT_TYPE + COMMA_SEP +
            SampleCommerce.Product.COLUMN_NAME_PRODUCT_DESC + TEXT_TYPE + COMMA_SEP +
            SampleCommerce.Product.COLUMN_NAME_PRODUCT_PRICE + " INTEGER" + " )";
    private static final String SQL_CREATE_ENTRIES_CART = "CREATE TABLE " +
            SampleCommerce.Cart.TABLE_NAME + " (" +
            SampleCommerce.Cart._ID + " INTEGER PRIMARY KEY," +
            SampleCommerce.Cart.COLUMN_NAME_PRODUCT_DESC + TEXT_TYPE + COMMA_SEP +
            SampleCommerce.Cart.COLUMN_NAME_PRODUCT_PRICE + " INTEGER" + COMMA_SEP +
            SampleCommerce.Cart.COLUMN_NAME_PRODUCT_QTY + " INTEGER" + ")";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SampleCommerce.Product.TABLE_NAME;
    private static final String SQL_DELETE_DATA_CART =
            "DELETE FROM " + SampleCommerce.Cart.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SampleCommerce.db";

    public SampleCommerceDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public ArrayList<String> getProductList(){
        ArrayList<String> productQuery = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + SampleCommerce.Product.TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast()==false){
            //productQuery.add(res.getString(res.getColumnIndex(SampleCommerce.Product.COLUMN_NAME_PRODUCT_ID)));
            productQuery.add(res.getString(2));
            res.moveToNext();
        }
        return productQuery;
    }

    // Obtain Product SKU from Product Database
    public String getProductSKU(String product){
        SQLiteDatabase db = this.getReadableDatabase();

        String tableColumns[] = {SampleCommerce.Product.COLUMN_NAME_PRODUCT_SKU};
        String whereClause = SampleCommerce.Product.COLUMN_NAME_PRODUCT_DESC + " = ?";
        String whereArgs[] = {product};

        Cursor cursor = db.query(SampleCommerce.Product.TABLE_NAME, tableColumns, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();
        //return Integer.toString(cursor.getColumnCount());
        //return Integer.toString(cursor.getCount());
        String productSKU = cursor.getString(0);
        return productSKU;
    }

    // Obtain Product Price from Product Database
    public int getProductPrice(String product){
        SQLiteDatabase db = this.getReadableDatabase();

        String tableColumns[] = {SampleCommerce.Product.COLUMN_NAME_PRODUCT_PRICE};
        String whereClause = SampleCommerce.Product.COLUMN_NAME_PRODUCT_DESC + " = ?";
        String whereArgs[] = {product};

        Cursor cursor = db.query(SampleCommerce.Product.TABLE_NAME, tableColumns, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();
        //return Integer.toString(cursor.getColumnCount());
        //return Integer.toString(cursor.getCount());
        int productPrice = cursor.getInt(0);
        return productPrice;
    }

    public void deleteCartData(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(SQL_DELETE_DATA_CART);
    }

    // Insert Records into Cart Database
    public void addProduct(String product){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        int productPrice = this.getProductPrice(product);
        int productqty = 1;

        values.put(SampleCommerce.Cart.COLUMN_NAME_PRODUCT_DESC, product);
        values.put(SampleCommerce.Cart.COLUMN_NAME_PRODUCT_PRICE, productPrice);
        values.put(SampleCommerce.Cart.COLUMN_NAME_PRODUCT_QTY, productqty);
        long newRowId;
        newRowId = db.insert(SampleCommerce.Cart.TABLE_NAME, null, values);
    }

    // Grab records from Cart Database
    public ArrayList<String> getCart(){
        ArrayList<String> cartQuery = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + SampleCommerce.Cart.TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast()==false){
            //productQuery.add(res.getString(res.getColumnIndex(SampleCommerce.Product.COLUMN_NAME_PRODUCT_ID)));
            cartQuery.add(res.getString(1) + " " + Integer.toString(res.getInt(2)));
            res.moveToNext();
        }
        return cartQuery;
    }

    public int getCartPriceTotal(){
        int total = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + SampleCommerce.Cart.TABLE_NAME, null);
        res.moveToFirst();
        while(res.isAfterLast()==false){
            total = total + res.getInt(2);
            res.moveToNext();
        }
        return total;

    }

    // Insert fake sample products into product database
    public void setSampleProductList(){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        long newRowId;
        // First Product
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_DESC,"Apple");
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_SKU, "SKK-1899");
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_PRICE, 12);
        newRowId = db.insert(SampleCommerce.Product.TABLE_NAME, null, values);
        // Second Product
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_DESC,"Pear");
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_SKU, "SKK-1870");
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_PRICE, 2);
        newRowId = db.insert(SampleCommerce.Product.TABLE_NAME, null, values);
        // Third Product
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_DESC,"Mango");
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_SKU, "SKK-1860");
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_PRICE, 4);
        newRowId = db.insert(SampleCommerce.Product.TABLE_NAME, null, values);
        // Fourth Product
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_DESC,"Pineapple");
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_SKU, "SKK-1850");
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_PRICE, 8);
        newRowId = db.insert(SampleCommerce.Product.TABLE_NAME, null, values);
        // Fifth Product
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_DESC,"Grapes");
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_SKU, "SKK-1840");
        values.put(SampleCommerce.Product.COLUMN_NAME_PRODUCT_PRICE, 14);
        newRowId = db.insert(SampleCommerce.Product.TABLE_NAME, null, values);
    }
}
