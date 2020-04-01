package com.example.async_task;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    String path="http://img.pconline.com.cn/images/photoblog/5/3/7/5/5375781/20096/6/1244302842840.jpg";//下载图片路径
    ProgressBar pb;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);
        pb = findViewById(R.id.pb);
        tv = findViewById(R.id.tv);
    }


    public void doAsyncTask(View view) {
//        new InnerAsyncTask().execute("");
        new LoadImageTaskThree(iv, tv, pb, this).execute("");
        pb.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);
    }

    //doInBackground()   和   onPostExecute()必须重写   第一个参数对应execute()中传的参数
   private class InnerAsyncTask extends AsyncTask<String,Integer,Bitmap>{

       @Override
       protected Bitmap doInBackground(String... strings) {

           for (int i=0;i<100;i++){
               try {
                   Thread.sleep(30);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

               publishProgress(i);
           }
           return BitmapFactory.decodeResource(getResources(),R.mipmap.bg2);
       }

       @Override
       protected void onPostExecute(Bitmap bitmap) {
//           super.onPostExecute(bitmap);
           pb.setVisibility(View.GONE);
           tv.setVisibility(View.GONE);
            iv.setImageBitmap(bitmap);
       }

       @Override
       protected void onProgressUpdate(Integer... values) {
//           super.onProgressUpdate(values);
           pb.setProgress(values[0]);
           tv.setText(values[0] + "%");
       }
   }

}