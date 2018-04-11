package net.jspiner.zeplindiff;

import android.app.Service;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;

import net.jspiner.zeplindiff.databinding.ViewToggleBinding;
import net.jspiner.zeplindiff.databinding.ViewViewerBinding;

public class ViewerService extends Service {

    private ViewViewerBinding viewerBinding;
    private ViewToggleBinding toggleBinding;

    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;

    @Override
    public void onCreate() {
        viewerBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getBaseContext()),
                R.layout.view_viewer,
                null,
                false
        );
        toggleBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getBaseContext()),
                R.layout.view_toggle,
                null,
                false
        );

        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                Build.VERSION.SDK_INT< Build.VERSION_CODES.O ?
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY :
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(viewerBinding.getRoot(), mParams);
        mWindowManager.addView(toggleBinding.getRoot(), new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT< Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY :
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
        ));


        super.onCreate();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            init(intent.getStringExtra("url"));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void init(String url) {
        Log.d("service", "load url : " + url);
        Glide.with(getBaseContext())
                .load(url)
                .into(viewerBinding.image);

        toggleBinding.alpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float alpha = (float) i / seekBar.getMax();
                viewerBinding.image.setAlpha(alpha);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        toggleBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });
        viewerBinding.image.scrollTo(0, 0);

        toggleBinding.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewerBinding.image.scrollBy(0, -10);
            }
        });
        toggleBinding.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewerBinding.image.scrollBy(0, 10);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(
                viewerBinding.getRoot());
        ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(
                toggleBinding.getRoot());
        viewerBinding = null;
        toggleBinding = null;
    }


}
