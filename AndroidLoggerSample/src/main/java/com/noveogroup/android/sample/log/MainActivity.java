package com.noveogroup.android.sample.log;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.noveogroup.android.log.LOG;
import com.noveogroup.android.sample.log.downloader.Downloader;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onSampleSimple(View view) {
        LOG.i("sample message");
        LOG.e("sample error message");
    }

    public void onSampleDownload(View view) {
        Downloader.download();
    }

}
