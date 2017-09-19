package net.jspiner.zeplindiff;

import android.content.Context;
import android.content.SharedPreferences;

public class KeyManager {

    private static SharedPreferences getSharedPreference(){
        return ZplinDiffApplication.getInstance().getSharedPreferences(
                "zeplindiff",
                Context.MODE_PRIVATE
        );
    }

    private static SharedPreferences.Editor getEditor(){
        return getSharedPreference().edit();
    }

    public static void putToken(String token){
        getEditor().putString("token", token).commit();
    }

    public static String getToken(){
        return getSharedPreference().getString("token", null);
    }
}
