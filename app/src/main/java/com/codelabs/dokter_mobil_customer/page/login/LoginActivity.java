package com.codelabs.dokter_mobil_customer.page.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.page.main.MainActivity;
import com.codelabs.dokter_mobil_customer.page.password.ForgotPasswordActivity;
import com.codelabs.dokter_mobil_customer.page.register.RegisterActivity;
import com.codelabs.dokter_mobil_customer.page.splashscreen.SplashScreenActivity;
import com.codelabs.dokter_mobil_customer.utils.BaseActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.DataLogin;
import com.codelabs.dokter_mobil_customer.viewmodel.DoPost;
import com.codelabs.dokter_mobil_customer.viewmodel.GetToken;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

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


    private Boolean showPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();
    }

    private void initView() {

    }

    private void initSetup() {
        tvForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        containerGoogle.setOnClickListener(this);
        containerFb.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        imgEyePassword.setOnClickListener(this);

    }

    private void fetchData() {

    }

    public void doLogin() {
        if (!valid())
            return;

        Map<String, String> params = new HashMap<>();
        params.put("username", edtEmail.getText().toString().trim());
        params.put("password", edtPassword.getText().toString().trim());
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
        Call<DataLogin> call = apiService.doLogin(auth,params);
        call.enqueue(new Callback<DataLogin>() {
            @Override
            public void onResponse(@NonNull Call<DataLogin> call, @NonNull Response<DataLogin> data) {

                if (data.isSuccessful()) {
                    if (data!= null) {
                        if (data.code() == 200) {
                            DataLogin response = data.body();
                            Toast.makeText(LoginActivity.this, response.getMessage(),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                } else {
                    DataLogin response = data.body();
                    Toast.makeText(LoginActivity.this, String.valueOf(data.code()),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataLogin> call, @NonNull Throwable t) {
                if (call.isCanceled()){
                    Toast.makeText(LoginActivity.this,"Network Failure :( please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean valid() {
        if (!RecentUtils.isEmailValid(edtEmail.getText().toString().trim())) {
            Toast.makeText(this,"please enter your valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
            Toast.makeText(this,"please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


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