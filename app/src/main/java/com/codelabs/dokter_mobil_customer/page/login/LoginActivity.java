package com.codelabs.dokter_mobil_customer.page.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.page.main.MainActivity;
import com.codelabs.dokter_mobil_customer.page.password.ForgotPasswordActivity;
import com.codelabs.dokter_mobil_customer.page.register.RegisterActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.DataLogin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    /*declare layout component in here*/

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_email)
    AppCompatEditText edtEmail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_password)
    AppCompatEditText edtPassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_forgot_password)
    AppCompatTextView tvForgotPassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_google)
    RelativeLayout containerGoogle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_fb)
    RelativeLayout containerFb;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_register)
    AppCompatTextView tvRegister;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_eye_password)
    AppCompatImageView imgEyePassword;

    /* declare global variable in here */

    private Boolean showPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initSetup();

    }

    private void initView() {

//        edtEmail.setText("ruditawangga@gmail.com");
//        edtPassword.setText("Iw0axzyg.");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void initSetup() {
        tvForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        containerGoogle.setOnClickListener(this);
        containerFb.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        imgEyePassword.setOnClickListener(this);
    }

    /*validation field in here */

    private boolean valid() {
        if (!RecentUtils.isEmailValid(Objects.requireNonNull(edtEmail.getText()).toString().trim())) {
            showToast("please enter your valid email");
            return false;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(edtPassword.getText()).toString().trim())) {
            showToast("please enter your password");
            return false;
        }
        return true;
    }

    /*function response data from API in here */

    public void doLogin() {
        if (!valid())
            return;

        Map<String, String> params = new HashMap<>();
        params.put("username", Objects.requireNonNull(edtEmail.getText()).toString().trim());
        params.put("password", Objects.requireNonNull(edtPassword.getText()).toString().trim());
        showDialogProgress("Load data login");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
        Call<DataLogin> call = apiService.doLogin(auth,params);
        call.enqueue(new Callback<DataLogin>() {
            @Override
            public void onResponse(@NonNull Call<DataLogin> call, @NonNull Response<DataLogin> data) {
                hideDialogProgress();
                if (data.isSuccessful()) {
                    DataLogin response = data.body();
                    if (data.code() == 200) {
                        assert response != null;
                        showToast(response.getMessage());
                        DataManager.getInstance().setPassword(edtPassword.getText().toString().trim());
                        DataManager.getInstance().setToken(response.getData().getToken());
                        DataManager.getInstance().setLoginData(response.getData().getDataCustomer());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                        String currrentDateandTime = sdf.format(new Date());
                        DataManager.getInstance().setLastLogin(currrentDateandTime);

                        DataManager.getInstance().setLogoutDuration(response.getData().getLogout_duration());


                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(data);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataLogin> call, @NonNull Throwable t) {
                if (!call.isCanceled()){
                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }


    /*declare function click in here */

    @Override
    public void onClick(View view) {
       if (btnLogin == view) {
           doLogin();
       }

       if (containerGoogle == view) {
           Toast.makeText(LoginActivity.this, "On Develop", Toast.LENGTH_SHORT).show();
       }

       if (containerFb == view) {
           Toast.makeText(LoginActivity.this, "On Develop", Toast.LENGTH_SHORT).show();
       }

       if (tvForgotPassword == view) {
           Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
           startActivity(intent);
       }

       if (tvRegister == view) {
           Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
           startActivity(intent);
       }

       if (imgEyePassword == view) {
           assert imgEyePassword != null;
           if (!showPassword) {
               imgEyePassword.setImageResource(R.drawable.ic_eye_gone);
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showPassword = true;
            } else {
               imgEyePassword.setImageResource(R.drawable.ic_eye_view);
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                showPassword = false;
            }
       }
    }
}