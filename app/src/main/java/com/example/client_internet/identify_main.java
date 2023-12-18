package com.example.client_internet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

//import com.google.type.Date;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class identify_main extends AppCompatActivity{
    private Button btn_scan;
    public static final int TAKE_PHOTO = 1;// 系统相机

    private Uri Imageuri;
    private File outputImage;
    private Identify_result result;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identity_main);
        btn_scan = (Button) findViewById(R.id.btn1);
//        picture = (ImageView) findViewById(R.id.picture);
        textView = (TextView) findViewById(R.id.tv1);
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                try {
                    if(!outputImage.exists()){//照片不存在
                        outputImage.createNewFile();//重新创建文件
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT>=24){
                    Imageuri= FileProvider.getUriForFile(identify_main.this,
                            "com.example.client_internet.fileprovider",outputImage);
                }else {
                    Imageuri=Uri.fromFile(outputImage);
                }
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,Imageuri);
                launcher.launch(takePictureIntent);
            }
        });
    }
    public ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        //使用身份证识别接口获取
                        IdCardDetails(getHandler_IdCard);

                    }
                }
            });
     Handler getHandler_IdCard = new Handler(){
         @Override
         public void handleMessage(@NonNull Message msg) {
             super.handleMessage(msg);
             String str=msg.getData().getString("request");
             JSONObject jsonObject=null;
             try {
                 jsonObject=new JSONObject(str);
             }catch (JSONException e){
                 e.printStackTrace();
             }
             try {
                 jsonObject.get("Response");
                 result=new Gson().fromJson(jsonObject.get("Response").toString(),Identify_result.class);
                 textView.setText(getGson(jsonObject));
             }catch (JSONException e){
                 e.printStackTrace();
             }
         }
     };
    /**
     * 身份证扫描结果
     */
    public void IdCardDetails(final Handler handler){
        String str = null;
        if (outputImage.exists()) {
            str = outputImage.toString();
            toast("找到图片:"+str);
        } else {
            toast("找不到图片");
            return;
        }
        //利用腾讯云V3签名算法封装图片，将封装后的数据上传至腾讯云服务器，再获得服务器返回数据
        TecentHttpUtil.getIdCardDetails(str,
                new TecentHttpUtil.SimpleCallBack() {
                    @Override
                    public void Succ(String result) {
                        Message message=new Message();
                        Bundle bundle=new Bundle();
                        bundle.putString("request",result);
                        message.setData(bundle);
                        handler.sendMessage(message);
                        toast("识别成功！");
                    }
                    @Override
                    public void error() {
                        toast("识别错误！");
                    }
                });
    }


    public String getGson(JSONObject jsonObject) throws JSONException {
        JSONObject jsonObject1= (JSONObject) jsonObject.get("Response");
        String str="姓名:"+jsonObject1.getString("Name")+" \n"
                +"性别: "+jsonObject1.getString("Sex")+" "+
                "民族： "+jsonObject1.getString("Nation")+"\n"+
                "出生：" +jsonObject1.getString("Birth")+"\n"+
                "地址: "  +jsonObject1.getString("Address")+"\n";
        return str;
    }

    public void toast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

}