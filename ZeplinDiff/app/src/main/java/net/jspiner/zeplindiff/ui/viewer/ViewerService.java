package net.jspiner.zeplindiff.ui.viewer;

import android.app.Service;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import net.jspiner.zeplindiff.R;
import net.jspiner.zeplindiff.databinding.ViewToggleBinding;
import net.jspiner.zeplindiff.databinding.ViewViewerBinding;
import net.jspiner.zeplindiff.utils.PixelUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class ViewerService extends Service {

    private PublishSubject<Boolean> buttonEventSubject = PublishSubject.create();
    private Disposable eventDisposable;

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
                Build.VERSION.SDK_INT < Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY :
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(viewerBinding.getRoot(), mParams);
        mWindowManager.addView(toggleBinding.getRoot(), new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT < Build.VERSION_CODES.O ?
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
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        viewerBinding.scrollView.scrollTo(0, PixelUtils.dpToPx(1000));
                        viewerBinding.image.setImageDrawable(resource);
                    }
                });

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

        toggleBinding.close.setOnClickListener(view -> stopSelf());
        setButtonTouchEvent(toggleBinding.up, true);
        setButtonTouchEvent(toggleBinding.down, false);

        buttonEventSubject.subscribe(
                isUp -> {
                    if (isUp) {
                        viewerBinding.scrollView.scrollBy(0, -10);
                    }
                    else {
                        viewerBinding.scrollView.scrollBy(0, 10);
                    }
                }
        );
    }

    private void setButtonTouchEvent(View view, boolean isUp) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    buttonEventSubject.onNext(isUp);
                    eventDisposable = Observable.interval(500, 50, TimeUnit.MILLISECONDS).subscribe(
                            __ -> {
                                buttonEventSubject.onNext(isUp);
                            }
                    );
                    break;
                case MotionEvent.ACTION_UP:
                    eventDisposable.dispose();
                    break;
            }
            return false;
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "onDestroy");
        ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(
                viewerBinding.getRoot());
        ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(
                toggleBinding.getRoot());
        viewerBinding = null;
        toggleBinding = null;
    }


}
