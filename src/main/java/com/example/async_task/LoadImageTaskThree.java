package com.example.async_task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadImageTaskThree extends AsyncTask<String,Integer,Object> {

    ImageView iv;
    TextView tv;
    ProgressBar pb;
    Context context;

    Bitmap mBitmap;
    int defaultWidth;
    int defaultHeight;
    Bitmap defaultBitmap;

    public LoadImageTaskThree(ImageView iv, TextView tv, ProgressBar pb, Context context) {
        this.iv = iv;
        this.tv = tv;
        this.pb = pb;
        this.context = context;

        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(),R.mipmap.df,ops);
        defaultWidth = ops.outWidth;
        defaultHeight = ops.outHeight;

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

        pb.setProgress(View.GONE);
        tv.setVisibility(View.GONE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
//        super.onProgressUpdate(values);
        pb.setProgress(values[0]);
        tv.setText(values[0] + "");

        getmBitmap(values[0]);
        iv.setImageBitmap(mBitmap);
    }

    private void getmBitmap(int progress){
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        mBitmap = Bitmap.createBitmap(defaultWidth,defaultHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(mBitmap);
        canvas.drawRect(0,0,defaultWidth * progress / 100,defaultHeight,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        canvas.drawBitmap(defaultBitmap,0,0,paint);
    }
}
