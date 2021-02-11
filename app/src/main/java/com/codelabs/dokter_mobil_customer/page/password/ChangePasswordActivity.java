package com.codelabs.dokter_mobil_customer.page.password;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.utils.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_password)
    AppCompatEditText edtPassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_eye_password)
    AppCompatImageView imgEyePassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_conf_password)
    AppCompatEditText edtConfPassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_eye_conf_password)
    AppCompatImageView imgEyeConfPassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;

    private Boolean showPassword = false;
    private Boolean showPasswordConf = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();
    }

    private void initView() {

    }

    private void initSetup() {
        ivBack.setOnClickListener(this);
        imgEyePassword.setOnClickListener(this);
        imgEyeConfPassword.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void fetchData() {

    }

    private boolean valid() {
        if (TextUtils.isEmpty(edtPassword.getText().toString().trim())){
            Toast.makeText(this,"please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(edtConfPassword.getText().toString().trim())) {
            Toast.makeText(this,"please confirm your password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!edtConfPassword.getText().toString().trim().equals(edtPassword.getText().toString().trim())) {
            Toast.makeText(this,"password not matching", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }

        if (btnSubmit == view) {
            Toast.makeText(ChangePasswordActivity.this, "On Develop",Toast.LENGTH_SHORT).show();
        }

        if (!showPassword) {
            imgEyePassword.setImageResource(R.drawable.ic_eye_gone);
            edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showPassword = true;
        } else {
            imgEyePassword.setImageResource(R.drawable.ic_eye_view);
            edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showPassword = false;
        }

        if (!showPasswordConf) {
            imgEyeConfPassword.setImageResource(R.drawable.ic_eye_gone);
            edtConfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showPasswordConf = true;
        } else {
            imgEyeConfPassword.setImageResource(R.drawable.ic_eye_view);
            edtConfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showPasswordConf = false;
        }
    }
}