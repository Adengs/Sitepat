package com.codelabs.dokter_mobil_customer.page.splashscreen;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.page.login.LoginActivity;
import com.codelabs.dokter_mobil_customer.page.main.MainActivity;
import com.codelabs.dokter_mobil_customer.page.walkthrough.WalkthroughActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.DataLogin;
import com.codelabs.dokter_mobil_customer.viewmodel.GetToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Base64;
import android.util.Log;

public class SplashScreenActivity extends BaseActivity {

    String tokenAccess = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loadAppToken();
        fetchData();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.codelabs.dokter_mobil_customer",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {
        }
        catch (NoSuchAlgorithmException e) {
        }
    }

    private void fetchData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (DataManager.getInstance().isFirstInstall()){
                    goToWalkThrough();
                } else {
                    if (DataManager.getInstance().isLogin()){
                        silentLogin();
                    } else {
                        goToLogin();
                    }
                }
            }
        }, 1200);
    }


    public void loadAppToken() {
        Map<String, String> params = new HashMap<>();
        params.put("client", AppConstant.client);
        params.put("secret", AppConstant.secret);

        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AppToken;
        Call<GetToken> call = apiService.getToken(auth, params);
        call.enqueue(new Callback<GetToken>() {
            @Override
            public void onResponse(@NonNull Call<GetToken> call, @NonNull Response<GetToken> data) {
                if (data.isSuccessful()) {
                    if (data.code() == 200) {
                        GetToken response = data.body();
                        assert response != null;
                        tokenAccess = response.getData().getAccessToken();
                        DataManager.getInstance().setTokenAccess(response.getData().getAccessToken());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(data);
                    showToast(error.message());
                    goToLogin();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetToken> call, @NonNull Throwable t) {
                if (!call.isCanceled()){
                    goToLogin();
                    showToast("Network Failure :( please try again later");
                }
            }
        });
    }

    public void silentLogin() {
        Map<String, String> params = new HashMap<>();
        params.put("username", DataManager.getInstance().getEmail());
        params.put("password", DataManager.getInstance().getPassword());

        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + tokenAccess;
        Call<DataLogin> call = apiService.doLogin(auth,params);
        call.enqueue(new Callback<DataLogin>() {
            @Override
            public void onResponse(@NonNull Call<DataLogin> call,@NonNull Response<DataLogin> data) {
                if (data.isSuccessful()) {
                    DataLogin response = data.body();
                    if (data.code() == 200) {
                        DataManager.getInstance().setToken(response.getData().getToken());
                        goToHome();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(data);
                    showToast(error.message());
                    goToLogin();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataLogin> call, @NonNull Throwable t) {

            }
        });
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
        if (!RecentUtils.checkInternet()){
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