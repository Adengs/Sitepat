package com.codelabs.dokter_mobil_customer.page.password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.page.login.LoginActivity;
import com.codelabs.dokter_mobil_customer.utils.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_email_phone)
    AppCompatEditText edtEmailPhone;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_continue)
    AppCompatButton btnContinue;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();

    }

    private void initView() {

    }

    private void initSetup() {
        btnContinue.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

    private void fetchData(){

    }

    private boolean valid() {
        if (TextUtils.isEmpty(edtEmailPhone.getText().toString().trim())) {
            Toast.makeText(this,"please enter your data", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            onBackPressed();

        }

        if (btnContinue == view) {
            Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordVerificationActivity.class);
            startActivity(intent);

        }

    }
}