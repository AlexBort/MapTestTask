package com.example.s.maptesttask;

public interface Constants {

    // String SNACK_MESSAGE = App.getAppResources().getString(R.string.snackbar_text);  // snackbar_text
    String SNACK_MESSAGE = App.getGlobalContext().getResources().getString(R.string.snackbar_text);
    String TITLE_NOTIF = App.getGlobalContext().getResources().getString(R.string.app_name);
    String DESCRIP_NOTIF = App.getGlobalContext().getResources().getString(R.string.notif_result_);
    String METERS = App.getGlobalContext().getResources().getString(R.string.meter);
    String RESULT_NOTIF = App.getAppResources().getString(R.string.notif_result_);

    String INTENT_SERVICE_KEY = "step";

    interface ACTION {
        public static String MAIN_ACTION = "com.javirock.coolservice.action.main";
        public static String STARTFOREGROUND_ACTION = "com.javirock.coolservice.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.javirock.coolservice.action.stopforeground";
    }

    interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }


}
