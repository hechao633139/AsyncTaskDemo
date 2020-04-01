package com.example.async_task;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadImageTaskTwo extends AsyncTask<String,Integer,Object> {

    ImageView iv;
    TextView tv;
    ProgressBar pb;
    Context context;

    private Bitmap mBitmap;
    private int defaultWidth;
    private int defaultHeight;
    private Bitmap defaultBitmap;

    public LoadImageTaskTwo(ImageView iv, TextView tv, ProgressBar pb, Context context) {
        this.iv = iv;
        this.tv = tv;
        this.pb = pb;
        this.context = context;

        //TODO  获取原来图片的尺寸
        BitmapFactory.Options ops = new BitmapFactory.Options();
        //这里不需要创建bitmap实例，只需要计算图片数据，设置ops为true
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(),R.mipmap.df,ops);
        defaultHeight = ops.outHeight;
        defaultWidth = ops.outWidth;

        //这里要给defaultBitmap赋值需要创建实例，设置ops为false
        ops.inJustDecodeBounds = false;
        defaultBitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.df,ops).copy(Bitmap.Config.ARGB_8888,true);
    }

    @Override
    protected Object doInBackground(String... strings) {
        for (int i=1;i<100;i++){
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return BitmapFactory.decodeResource(context.getResources(),R.mipmap.bg2);
    }

    @Override
    protected void onPostExecute(Object o) {
//        super.onPostExecute(o);
        iv.setImageBitmap((Bitmap) o);

        pb.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
//        super.onProgressUpdate(values);
        pb.setProgress(values[0]);
        tv.setText(values[0] + "");

        getBitmap(values[0]);
        
        iv.setImageBitmap(mBitmap);
    }

    //根据比例算出宽高
    private void getBitmap(int progress){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        //mBitmap赋值，而不是在onProgressUpdate()方法中每次都创建新的bitmap
        mBitmap = Bitmap.createBitmap(defaultWidth,defaultHeight, Bitmap.Config.ARGB_8888);

        //画矩形,并且以mBitmap为背景
        Canvas canvas = new Canvas(mBitmap);
        //TODO    progress的值是从1-100，不是0.01-1
        canvas.drawRect(0,0,1f * defaultWidth * progress / 100,defaultHeight,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        canvas.drawBitmap(defaultBitmap,0,0,paint);

    }
}
