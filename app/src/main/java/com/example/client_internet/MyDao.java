package com.example.client_internet;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyDao {
    MySQHelper mySQHelper;
    SQLiteDatabase sqLiteDatabase;

    Context mycontext;
    private Uri uri=Uri.parse("content://xx.provider");
    public MyDao(Context context) {
        mycontext = context;
        mySQHelper=new MySQHelper(context,"myFirst",null,1);
        sqLiteDatabase=mySQHelper.getWritableDatabase();
    }
    public Uri insert(ContentValues values){
        long row_id=sqLiteDatabase.insert("mytable",null,values);
        if (row_id==-1){
            Log.d("xx","insert error!");
            return null;
        }else {
            Uri Insert_uri= ContentUris.withAppendedId(uri,row_id);
            Log.d("xx","ContentUri "+Insert_uri.toString());
            mycontext.getContentResolver().notifyChange(Insert_uri,null);

            return Insert_uri;
        }
        //        ContentValues values = new ContentValues();
//        values.put("name", "张三");
//        values.put("id", 20);
//        sqLiteDatabase.insert("myFirst",null,values);
//        ContentValues values1 = new ContentValues();
//        values.put("name", "李四");
//        values.put("id", 21);
//        sqLiteDatabase.insert("myFirst",null,values1);
//        ContentValues values3 = new ContentValues();
//        values.put("id", 22);
//        values.put("name", "王五");
//
//
//        sqLiteDatabase.insert("myFirst",null,values3);
    }
    public Cursor queryValues(String selection,String[] selectArgs){
        Cursor cursor=sqLiteDatabase.rawQuery(selection,selectArgs);
        return cursor;
    }
    public int updateValues(Uri uri,ContentValues values,String selection,
                            String[] selectionArgs){
        int account=sqLiteDatabase.update("mytable",values,selection,selectionArgs);
        return account;
    }
    public int delete(String selection,String[] selectionArgs){
        return sqLiteDatabase.delete("mytable",selection,selectionArgs);
    }
}
