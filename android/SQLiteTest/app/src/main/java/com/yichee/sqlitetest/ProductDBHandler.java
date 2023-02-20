package com.yichee.sqlitetest;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PRODUCT_DB";
    public static final String TABLE_PRODUCTS = "PRODUCT_TABLE";
    public static final String COLUMN_ID = "PRODUCT_ID";
    public static final String COLUMN_PRODUCTNAME = "PRODUCT_NAME";

    public ProductDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCTNAME + " TEXT " +
                ");";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_PRODUCTS;

        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);
    }

    public void addProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.get_productName());
        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public void deleteProduct(String productName) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " = \"" + productName + "\";";
        db.execSQL(query);
        db.close();
    }

    public String databaseToString() {
        StringBuilder sb = new StringBuilder();
        //String s = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + ";";

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String productName = cursor.getString(cursor.getColumnIndex("PRODUCT_NAME"));
            if (productName != null) {
                sb.append(productName + "\n");
                //s += productName + "\n";
            }
            cursor.moveToNext();
        }


        db.close();

        return sb.toString();
        //return s;
    }
}
