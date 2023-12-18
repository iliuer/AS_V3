package com.example.client_internet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class connect extends AppCompatActivity {
    Button button,button1,button2;
    TextView textView;
    ImageView imageView;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        button=findViewById(R.id.button);
        button1=findViewById(R.id.button2);
        button2=findViewById(R.id.button3);
        imageView=findViewById(R.id.imageView2);
        textView=findViewById(R.id.textView);
        Context context=this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MyTask myTask=new MyTask();
//                myTask.onPostExecute(myTask.doInBackground());
                new MyTask().execute();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(context)
                        .load("https://img-s-msn-com.akamaized.net/tenant/amp/entityid/AA1l6Y77.img?w=768&h=513&m=6&x=462&y=178&s=176&d=176")
                        .into(imageView);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test_png();
            }
        });
    }
    class  MyTask extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://www.baidu.com");
                // TODO http url connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(reader);
                result = bufferedReader.readLine();
                return result;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }
    }
    public void test_get(){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder()
                .url("https://www.vip.com")
                .addHeader("key","value")
                .get()
                .build();
        // 执行Request请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {// 异步
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("xx",response.body().toString());

            }
        });
        // 同步
        try {
            Response response=okHttpClient.newCall(request).execute();
            response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void test_post(){
        OkHttpClient okHttpClient=new OkHttpClient();
        MediaType mediaType=MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody=RequestBody.create(mediaType,"{}");
        Request request=new Request.Builder()
                .url("http://www.httpbin.org/post")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {// 异步
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("xx",response.body().toString());

            }
        });
        try {
            Response response=okHttpClient.newCall(request).execute();
            response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void test_png(){
        OkHttpClient okHttpClient=new OkHttpClient();
        MediaType mediaType=MediaType.parse("image/png; charset=utf-8");
        RequestBody requestBody=RequestBody.create(mediaType,"{}");
        Request request=new Request.Builder()
                .url("https://img-s-msn-com.akamaized.net/tenant/amp/entityid/AA1l6Y77.img?w=768&h=513&m=6&x=462&y=178&s=176&d=176")
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final Bitmap bitmap= BitmapFactory.decodeStream(response.body().byteStream());
                runOnUiThread(new Runnable() {
                    @Override
                    //10.185.156.113
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }
}