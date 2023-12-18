package com.example.client_internet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
//    ImageView imageView;
//    Button button;
//    int current = 0;
//    int p[] = new int[]{
//            R.drawable.one,
//            R.drawable.two,
//            R.drawable.three
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        imageView = findViewById(R.id.imageView);
//        button=findViewById(R.id.button1);
//        Handler handler = new Handler() {
//
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                if (msg.what == 0x123) {
//                    imageView.setImageResource(p[current++]);
//                    if (current >= 3) current = 0;
//                }
//            }
//        };
//
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Message message = new Message();
//                message.what = 0x123;
//                handler.sendMessage(message);
//            }
//        }, 0, 1000);
//
//
////        Handler handler1 = new Handler.Callback() {
////            @Override
////            public boolean handleMessage(@NonNull Message message) {
////                return true;
////            }
////        }
//
//    }
//}
    Button button_insert;
    Button button_delete;
    Button button_update;
    Button button_select;
    TextView textView;
    ContentResolver contentResolver;
    MySQHelper mySQHelper;
    private static final Uri NOTIFI_URI=Uri.parse("content://xx.provider/mytable");
    final String SQL1=" drop table if exists mytable " ; // 若当前数据库存在则删除
    final String SQL2="create table mytable(" +
            "id int," +     // 学号
            "name String" +  // 姓名
            ")";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         button_insert=findViewById(R.id.button);
         button_delete=findViewById(R.id.button2);
         button_update=findViewById(R.id.button3);
         button_select=findViewById(R.id.button4);
         textView=findViewById(R.id.textView);
        Uri uri=NOTIFI_URI;
        contentResolver=getContentResolver();
        mySQHelper = new MySQHelper(this, "test.db", null, 1);

        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values=new ContentValues();
                values.put("id",224);
                values.put("name","xiaoming");
                contentResolver.query(uri,null,SQL1, null,null);
                contentResolver.query(uri,null,SQL2, null,null);

                contentResolver.insert(uri,values);
            }
        });
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentResolver.delete(uri,"name=?",new String[]{"xiaoming"});
            }
        });
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values=new ContentValues();
                values.put("name","LiHua");
                contentResolver.update(uri,values,"name=?",new String[]{"xiaoming"});
            }
        });
        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor;
//                Cursor cursor=contentResolver.query(uri,null,"select * from mytable ",new String[]{"xiaoming"},null);
                cursor=contentResolver.query(uri,null,"select * from mytable",null,null);
                Log.d("xx",cursor.toString()+" ======      =======       =======");
                cursor.moveToFirst();
                textView.setText("");
                while (!cursor.isAfterLast()){
                    @SuppressLint("Range") int id=cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
                    textView.append(id+" "+name+" ");
                    cursor.moveToNext();
                }
                cursor.close();
            }
        });
    }
}