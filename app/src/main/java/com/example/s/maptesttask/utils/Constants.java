package com.example.s.maptesttask.utils;

import android.content.SharedPreferences;

import com.example.s.maptesttask.App;
import com.example.s.maptesttask.R;

public interface Constants {

    String SWITCH_NOTIF = App.getGlobalContext().getResources().getString(R.string.switch_notif);
    String TITLE_NOTIF = App.getGlobalContext().getResources().getString(R.string.app_name);
    String DESCRIP_NOTIF = App.getGlobalContext().getResources().getString(R.string.notif_result_);
    String METERS = App.getGlobalContext().getResources().getString(R.string.meter);
    String RESULT_NOTIF = App.getAppResources().getString(R.string.notif_result_);

    String INTENT_SERVICE_KEY = "step";

    String SHARED_NAME = "STEP_SHARED";
    String PREF_KEY = "STEP";
    SharedPreferences PREFERENCES = App.getGlobalContext().getSharedPreferences(SHARED_NAME, 0);
    Float STEP = PREFERENCES.getFloat(PREF_KEY, 0);


    interface ACTION {
        public static String MAIN_ACTION = "com.javirock.coolservice.action.main";
        public static String STARTFOREGROUND_ACTION = "com.javirock.coolservice.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.javirock.coolservice.action.stopforeground";
    }

    interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }


}
