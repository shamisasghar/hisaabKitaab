package com.technology.hisabKitab.utils;


import android.content.Context;
import android.content.Intent;

import com.technology.hisabKitab.Model.User;

public class LoginUtils {

    public static boolean isUserLogin(Context context) {
        return PrefUtils.getBoolean(context, Constants.USER_AVAILABLE, false);
    }

    public static void saveUser(Context context, User user) {
        if (user == null)
            return;
//        if (!AppUtils.isMyServiceRunning(context, WebSocketService.class)) {
//            context.startService(new Intent(context, WebSocketService.class));
//        }
//        PrefUtils.persistString(context, Constants.USER, GsonUtils.toJson(user));
    }

    public static User getUser(Context context) {
        return GsonUtils.fromJson(PrefUtils.getString(context, Constants.USER), User.class);
    }

    public static void userLoggedIn(Context context) {
        PrefUtils.persistBoolean(context, Constants.USER_AVAILABLE, true);
    }

    public static void clearUser(Context context) {
        PrefUtils.remove(context, Constants.USER_AVAILABLE);
        PrefUtils.remove(context, Constants.USER);
        PrefUtils.remove(context, Constants.USER_EMAIL);
//        ScheduleUtils.removeAuthToken(context);
//        if (AppUtils.isMyServiceRunning(context, WebSocketService.class)) {
//            context.stopService(new Intent(context, WebSocketService.class));
//        }
    }
    public static void saveUserEmail(Context context, String email) {
        if (email == null)
            return;

        PrefUtils.persistString(context, Constants.USER_EMAIL, email);
    }
    public static String getUserEmail(Context context){
        return PrefUtils.getString(context,Constants.USER_EMAIL);
    }




}
