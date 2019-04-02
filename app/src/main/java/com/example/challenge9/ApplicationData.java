package com.example.challenge9;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationData {

    public static final String APP_KEY = "Module 9 challenge 1";

    //save a String value by key
    public static void setStringValueInSharedPreferences(Context context, String key,
                                                         String value) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(ApplicationData.APP_KEY, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //save a boolean value by key
    public static void setBooleanValueInSharedPreferences(Context context, String key,
                                                          boolean value) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(ApplicationData.APP_KEY, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    //delete a value by a key
    public static void deleteValueFromSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(ApplicationData.APP_KEY, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    //delete a value by a key
    public static void deleteAllValuesFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(ApplicationData.APP_KEY, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    // get a String value by key
    public static String getStringValueFromSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(ApplicationData.APP_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    // get a boolean value by key
    public static boolean getBooleanValueFromSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(ApplicationData.APP_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }


}
