package com.example.client_internet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContentProvider1 extends ContentProvider {
    MyDao myDao;
    @Override
    public boolean onCreate() {
        myDao=new MyDao(getContext());
        return true;
    }
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings,
                        @Nullable String s, @Nullable String[] strings1,
                        @Nullable String s1) {
        Log.d("xx",s+strings1+"it's my provider action ");
        Cursor cursor =myDao.queryValues(s,strings1);
        return cursor;
    }
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented ");
    }
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return myDao.insert(contentValues);
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return myDao.delete(s,strings);
    }
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return myDao.updateValues(uri,contentValues,s,strings);
    }
}
