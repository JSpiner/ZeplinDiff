package net.jspiner.zeplindiff.utils;

import android.content.Context;
import android.content.SharedPreferences;

import net.jspiner.zeplindiff.ZplinDiffApplication;

public class KeyManager {

    private static final String PREFERENCE_NAME = "zeplindiff";

    private static final String KEY_TOKEN = "token";

    private static SharedPreferences getSharedPreference(){
        return ZplinDiffApplication.getInstance().getSharedPreferences(
                PREFERENCE_NAME,
                Context.MODE_PRIVATE
        );
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
}
