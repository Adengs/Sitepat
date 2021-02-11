package com.codelabs.dokter_mobil_customer.page.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.utils.BaseActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.google.gson.annotations.SerializedName;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_fullname)
    AppCompatEditText edtFullname;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_email)
    AppCompatEditText edtEmail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_password)
    AppCompatEditText edtPassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_eye_password)
    AppCompatImageView imgEyePassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_register)
    AppCompatButton btnRegister;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_login)
    AppCompatTextView tvLogin;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;


    private Boolean showPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();

    }

    private void initView() {

    }

    private void initSetup() {
        btnRegister.setOnClickListener(this);
        imgEyePassword.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

    private void fetchData() {

    }

    private boolean valid() {
        if (TextUtils.isEmpty(edtFullname.getText().toString().trim())) {
            Toast.makeText(this,"please enter your full name",Toast.LENGTH_SHORT).show();
            return false;
        }

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
        if (btnRegister == view) {

        }

        if (tvLogin == view) {
            finish();
        }

        if (ivBack == view) {
            finish();
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