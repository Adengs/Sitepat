package com.codelabs.dokter_mobil_customer.page.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.page.login.LoginActivity;
import com.codelabs.dokter_mobil_customer.page.main.MainActivity;
import com.codelabs.dokter_mobil_customer.page.walkthrough.WalkthroughActivity;
import com.codelabs.dokter_mobil_customer.utils.BaseActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (DataManager.getInstance().isFirstInstall()){
//                    goToWalkThrough();
                    goToLogin();
                } else {
                    if (DataManager.getInstance().isLogin()){

                    } else {
                        goToLogin();
                    }
                }
            }
        }, 1200);
    }

    private void goToLogin() {
        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToWalkThrough() {
        Intent intent = new Intent(SplashScreenActivity.this, WalkthroughActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToHome() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToLoginCheck() {
        if (RecentUtils.checkInternet()){
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            goToHome();
        }

    }


}