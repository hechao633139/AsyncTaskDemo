package com.example.async_task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.Image;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadImageTask extends AsyncTask<String,Integer,Object> {

    ImageView iv;
    TextView tv;
    ProgressBar pb;
    Context context;

    Bitmap defaultImage;
    int defaultWidth;
    int defaultHeight;
     Bitmap progressImage;

    public LoadImageTask(ImageView iv, TextView tv, ProgressBar pb, Context context) {
        this.iv = iv;
        this.tv = tv;
        this.pb = pb;
        this.context = context;

        BitmapFactory.Options ops = new BitmapFactory.Options();
        //TODO  如果值为true将不返回实际的bitmap，也不会给其分配内存空间，这样避免内存溢出oom
        //TODO  这里不需要创建对象，只需要获取数据，所以设置为true，通过ops可以查询到图片原始宽高
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(),R.mipmap.df,ops);
        defaultWidth = ops.outWidth;
        defaultHeight = ops.outHeight;

        //这里需要创建bitmap真实的对象
        ops.inJustDecodeBounds = false;
        defaultImage = BitmapFactory.decodeResource(context.getResources(),R.mipmap.df,ops).copy(Bitmap.Config.ARGB_8888,true);
    }

    @Override
    protected Object doInBackground(String... strings) {
        for (int i=1;i<100;i++){
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //todo  发送进度
            publishProgress(i);
        }
//        bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.bg2);
        return BitmapFactory.decodeResource(context.getResources(),R.mipmap.bg2);
    }

    @Override
    protected void onPostExecute(Object o) {
//        super.onPostExecute(0x2);
        iv.setImageBitmap((Bitmap) o);
        pb.setVisibility(View.GONE);

        tv.setVisibility(View.GONE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
//        super.onProgressUpdate(values);
        pb.setProgress(values[0]);
        tv.setText(values[0] + "");

        getProgressImage(values[0]);
        iv.setImageBitmap(progressImage);
    }

    //根据进度画出Bitmap
    private void getProgressImage(int progress){
        /**
         * 1.画一个绿色长方形
         * 2.设置这个长方形和原来的灰色长方形重叠
         * 3.在同一张画布上画原图形
         */
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);

        progressImage = Bitmap.createBitmap(defaultWidth,defaultHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(progressImage);
        canvas.drawRect(0,0,progress * 1f * defaultWidth / 100,defaultHeight,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        canvas.drawBitmap(defaultImage,0,0,paint);
    }
}
