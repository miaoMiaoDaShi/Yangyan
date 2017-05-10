package com.xxp.updatelibrary;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 钟大爷 on 2017/3/15.
 */

public class DownloadEngine {
    //下载文件
    public static void Librarydownload(final String url, final DownloadListener listener) {
        Context context = CheckUpdater.getInstance().getmContext();
        //记录上一次的Progress,均分100分
        final int[] lastPro = {0};
        final AppUpdater appUpdater = CheckUpdater.getInstance().getmAppUpdater();
        //执行异步任务
        new AsyncTask<String, Integer, File>() {

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);
                if (file != null) {
                    if (file.isFile()) {
                        listener.onSuccess(file);
                    }
                } else {
                    listener.onError(new Throwable("阿欧!下载失败!"));
                }
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                if(values[0]> lastPro[0]){
                    listener.onDownloading(values[0]);
                    lastPro[0] = values[0].intValue();
                }

            }

            @Override
            protected File doInBackground(String... params) {
                HttpURLConnection connection = null;
                FileOutputStream fos = null;
                InputStream is = null;
                File file = null;
                long totalLen;
                try {
                    connection = (HttpURLConnection) new URL(params[0]).openConnection();
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(10000);
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        totalLen = connection.getContentLength();
                        file = new File(
                                Environment
                                        .getExternalStoragePublicDirectory(appUpdater.getFilePath())
                                , appUpdater.getAppName());
                        if (file.isDirectory()) {
                            Log.e("isDirectory", "这是一个文件夹: ");
                        }
                        fos = new FileOutputStream(file);
                        is = connection.getInputStream();
                        byte bytes[] = new byte[1024];
                        int len = -1;
                        int currentLen = 0;
                        while ((len = is.read(bytes)) != -1) {
                            fos.write(bytes, 0, len);
                            currentLen += len;
                            publishProgress((int) (currentLen * 100 / totalLen));
                            Log.e("downloading", "Librarydownload: " + (int) (currentLen * 100 / totalLen));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    try {
                        if (fos != null) {
                            fos.close();
                        }
                        if (is != null) {
                            is.close();
                        }
                        if (connection != null) {
                            connection.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return file;
            }
        }.execute(url);
    }

    //系统下载
    public static void systemDownload(String url,final DownloadListener listener) {
        final Context context = CheckUpdater.getInstance().getmContext();
        AppUpdater appUpdater = CheckUpdater.getInstance().getmAppUpdater();

        //获得下载服务
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(appUpdater.getAppName());
        request.setDescription("下载中....");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(appUpdater.isShowToStatusBar()
                ? DownloadManager.Request.VISIBILITY_VISIBLE
                : DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setDestinationInExternalPublicDir(appUpdater.getFilePath(), appUpdater.getAppName());
        long id = downloadManager.enqueue(request);

        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        final Cursor cursor = downloadManager.query(query);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(cursor != null && cursor.moveToFirst()){
                    int currentPro = cursor
                            .getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int totalPro = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    listener.onDownloading(currentPro*100/totalPro);
                    Log.e("下载中", "run: "+currentPro+""+totalPro );
                }
                Log.e("error", "run: ");
            }
        },0,200);
    }

}
