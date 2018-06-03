package net.jspiner.zeplindiff.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;

import net.jspiner.zeplindiff.ZeplinDiffApplication;

public class KeyManager {

    private static final String PREFERENCE_NAME = "zeplindiff";

    private static final String KEY_TOKEN = "token";

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences getSharedPreference(){
        if (sharedPreferences == null) {
            sharedPreferences = ZeplinDiffApplication.getInstance().getSharedPreferences(
                    PREFERENCE_NAME,
                    Context.MODE_PRIVATE
            );
        }
        return sharedPreferences;
    }

    private static SharedPreferences.Editor getEditor(){
        return getSharedPreference().edit();
    }

    public static void putToken(String token){
        getEditor().putString(KEY_TOKEN, token).commit();
    }

    public static String getToken(){
        return getSharedPreference().getString(KEY_TOKEN, null);
    }

    public static void clear() {
        getEditor().clear();
    }
}
