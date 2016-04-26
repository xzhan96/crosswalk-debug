package org.crosswalkproject.xwalkdownloading6678;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkDownloadListener;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

public class MainActivity extends XWalkActivity {
    private static final String LOGTAG = "XIAOFENG";
    private XWalkView mXWalkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mXWalkView = (XWalkView) findViewById(R.id.xwalkview);

        mXWalkView.setUIClient(new XWalkUIClient(mXWalkView) {
            @Override
            public void onPageLoadStarted(XWalkView view, String url) {
                Log.i(LOGTAG, "onPageLoadStarted " + url);
            }

            @Override
            public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
                Log.i(LOGTAG, "onPageLoadStopped");
            }
        });
        mXWalkView.setResourceClient(new XWalkResourceClient(mXWalkView) {
            @Override
            public void onLoadFinished(XWalkView view, String url) {
                Log.i(LOGTAG, "onLoadFinished");
            }

//            @Override
//            public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
//                Log.i(LOGTAG, "shouldOverrideUrlLoading  ");
//                if (url.endsWith(".pdf")) {
//                    Log.i(LOGTAG, "download start here  ");
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                    // if want to download pdf manually create AsyncTask here
//                    // and download file
//                    return true;
//                }
//                return false;
//            }
        });

      }

        public void openSystemFile(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        //系统调用Action属性
        intent.setType("*/*");
        //设置文件类型
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // 添加Category属性
        try{
            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(this, "没有正确打开文件管理器", 1).show();
        }
    }

    @Override
    protected void onXWalkReady() {

        mXWalkView.setDownloadListener(new XWalkDownloadListener(this) {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Log.i(LOGTAG, "start onDownloadStart  ");
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setTitle("Downloading");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("确认")
                        .setMessage("确定吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        openSystemFile();
                                    }
                                }
                        )
                        .setNegativeButton("否", null)
                        .show();
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "img.pngf");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Log.i(LOGTAG, "end of onDownloadStart  ");
                dm.enqueue(request);
            }
        });

        //mXWalkView.load("http://www.baidu.com", null);
        mXWalkView.load("http://haier.com/bigfiles/directory/cn/Notebook/20150806/bf1572842517.pdf", null);
    }

}
