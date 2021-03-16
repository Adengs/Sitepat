package com.codelabs.dokter_mobil_customer.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;

import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class LogOutTimerUtil {
    private static final String TAG = com.codelabs.dokter_mobil_customer.utils.LogOutTimerUtil.class.getSimpleName();

    public interface LogOutListener {
        void doLogoutDialog();
        void doLogout();
        void doLogoutBackground();
    }

    static boolean dialogStat = false;

    static CountDownTimer longTimer;
//    static final int LOGOUT_TIME = 3000; // delay in milliseconds i.e. 5 min = 300000 ms or use timeout argument

    public static void checkDialogOpen(LogOutListener logOutListener){

        if(!dialogStat) {
            logOutListener.doLogoutDialog();
            dialogStat = true;
        }

    }

    public static synchronized void startLogoutTimer(final Context context, final LogOutListener logOutListener) {
        Log.e(TAG, "startLogoutTimer: " );
        if(!DataManager.getInstance().isLogin())
            return;

        if (longTimer != null) {
            longTimer.cancel();
            longTimer = null;
            dialogStat = false;
        }



        long logoutDuration = AppConstant.TIMER_AUTO_LOGOUT;

        if(!TextUtils.isEmpty(DataManager.getInstance().getLogoutDuration())){
            logoutDuration = Long.parseLong(DataManager.getInstance().getLogoutDuration()) * 60000;
        }
        final long fewTimes = logoutDuration / 5;
//        final long fewTimes = 180000;

        Log.e(TAG, "startLogoutTimer: "+logoutDuration + " - "+fewTimes );

        longTimer = new CountDownTimer(logoutDuration,60000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e(TAG, "onTick: "+millisUntilFinished );
                if(millisUntilFinished < fewTimes){
                    checkDialogOpen(logOutListener);
                }
            }

            @Override
            public void onFinish() {
                try {
                    boolean foreGround = new LogOutTimerUtilBackup.ForegroundCheckTask().execute(context).get();
                    boolean backGround = new LogOutTimerUtilBackup.BackgroundCheckTask().execute(context).get();

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
        };

        longTimer.start();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        DataManager.getInstance().setLastLogin(currentDateandTime);
    }

    public static synchronized void stopLogoutTimer() {
        if (longTimer != null) {
            longTimer.cancel();
            longTimer = null;
            dialogStat = false;
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

