# AsyncTaskDemo
用AsyncTask异步实现图片加载

LoadImageTask/Two/Three代码相同，其原理就是在Activity的主线程去调用自定义AsyncTask的execute()方法，然后在重写的doInBackground()方法中
模拟耗时操作，通过publishProgress()方法发送进度，以便以在onProgressUpdate()方法中给progress设置进度，最后在onPostExecute()方法中
显示UI的更新
