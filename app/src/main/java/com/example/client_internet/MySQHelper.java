package com.example.client_internet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQHelper extends SQLiteOpenHelper {
    final String SQL1=" drop table if exists mytable " ; // 若当前数据库存在则删除
    final String SQL2="create table mytable(" +
                        "id int," +     // 学号
                        "name String" +  // 姓名
                        ")";
    public MySQHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL1);
        sqLiteDatabase.execSQL(SQL2);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
