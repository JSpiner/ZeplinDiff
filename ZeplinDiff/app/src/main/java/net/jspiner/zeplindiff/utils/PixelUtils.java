package net.jspiner.zeplindiff.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import net.jspiner.zeplindiff.ZeplinDiffApplication;

public class PixelUtils {

    public static int dpToPx(int dp) {
        Resources resources = ZeplinDiffApplication.getInstance().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        Log.i("TAG", "origin : " + dp + " px : " + px);
        return (int) px;
    }
}
