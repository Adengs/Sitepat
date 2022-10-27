package com.codelabs.sitepat_customer.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;

import com.codelabs.sitepat_customer.connection.AppConstant;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class LogOutTimerUtilBackup {
    public interface LogOutListener {
        void doLogout();
        void doLogoutBackground();
    }



    static Timer longTimer;
//    static final int LOGOUT_TIME = 3000; // delay in milliseconds i.e. 5 min = 300000 ms or use timeout argument

    public static synchronized void startLogoutTimer(final Context context, final LogOutListener logOutListener) {
        if (longTimer != null) {
            longTimer.cancel();
            longTimer = null;
        }

        longTimer = new Timer();

        longTimer.schedule(new TimerTask() {

            public void run() {

                cancel();

                longTimer = null;

                try {
                    boolean foreGround = new ForegroundCheckTask().execute(context).get();
                    boolean backGround = new BackgroundCheckTask().execute(context).get();

                    if (foreGround) {
                        logOutListener.doLogout();
                    }

                    if(backGround){
                        logOutListener.doLogoutBackground();

                    }

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }, AppConstant.TIMER_AUTO_LOGOUT);
    }

    public static synchronized void stopLogoutTimer() {
        if (longTimer != null) {
            longTimer.cancel();
            longTimer = null;
        }
    }

    static class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... params) {
            final Context context = params[0].getApplicationContext();
            return isAppOnForeground(context);
        }

        private boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }
    static class BackgroundCheckTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... params) {
            final Context context = params[0].getApplicationContext();
            return isAppOnForeground(context);
        }

        private boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }
}