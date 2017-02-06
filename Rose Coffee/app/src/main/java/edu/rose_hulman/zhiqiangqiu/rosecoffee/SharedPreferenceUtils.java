package edu.rose_hulman.zhiqiangqiu.rosecoffee;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JerryQiu on 2/5/17.
 */

public class SharedPreferenceUtils {
    public static String getCurrentUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        return prefs.getString(Constants.UID_KEY, "");
    }

    public static void setCurrentUser(Context context, String uid) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.UID_KEY, uid);
        editor.commit();
    }

//    public static void removeCurrentUser(Context context) {
//        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.remove(Constants.UID_KEY);
//        editor.apply();
//    }
//
//    public static String getCurrentCourseKey(Context context) {
//        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
//        return prefs.getString(Constants.COURSE_KEY, "");
//    }
//
//    public static void setCurrentCourseKey(Context context, String courseKey) {
//        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(Constants.COURSE_KEY, courseKey);
//        editor.commit();
//    }
//
//    public static void removeCurrentCourseKey(Context context) {
//        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.remove(Constants.COURSE_KEY);
//        editor.apply();
//    }
}
