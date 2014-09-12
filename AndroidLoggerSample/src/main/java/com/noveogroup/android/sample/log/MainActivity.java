package com.noveogroup.android.sample.log;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.noveogroup.android.log.Log;
import com.noveogroup.android.sample.log.downloader.Downloader;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.v("test VERBOSE");
        Log.d("test DEBUG");
        Log.i("test INFO");
        Log.w("test WARN");
        Log.e("test ERROR");
        Log.a("test ASSERT");
    }

    public void onSampleSimple(View view) {
        Log.i("sample message");
        Log.e("sample error message");
    }

    public void onSampleDownload(View view) {
        Downloader.download();
    }

}
