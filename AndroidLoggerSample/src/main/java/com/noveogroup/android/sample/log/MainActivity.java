package com.noveogroup.android.sample.log;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.noveogroup.android.log.Log;
import com.noveogroup.android.sample.log.downloader.Downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends Activity {

    private static final Logger SLF_LOGGER = LoggerFactory.getLogger(MainActivity.class);

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

        Log.i("formated %s", "message");
        Log.i("not formated %s");

        SLF_LOGGER.trace("test trace : {}", "message 100%!");
        SLF_LOGGER.debug("test debug : {}", "message 100%!");
        SLF_LOGGER.info("test info  : {}", "message 100%!");
        SLF_LOGGER.warn("test warn  : {}", "message 100%!");
        SLF_LOGGER.error("test error : {}", "message 100%!");
    }

    public void onSampleSimple(View view) {
        Log.i("sample message");
        Log.e("sample error message");
    }

    public void onSampleDownload(View view) {
        Downloader.download();
    }

}
