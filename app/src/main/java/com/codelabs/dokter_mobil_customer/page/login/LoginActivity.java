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

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.page.main.MainActivity;
import com.codelabs.dokter_mobil_customer.page.password.ForgotPasswordActivity;
import com.codelabs.dokter_mobil_customer.page.register.RegisterActivity;
import com.codelabs.dokter_mobil_customer.utils.BaseActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

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
           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);

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