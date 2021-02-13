package com.codelabs.dokter_mobil_customer.page.password;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.helper.EditTextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordVerificationActivity extends BaseActivity implements View.OnClickListener {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_resend_code)
    AppCompatTextView tvResendCode;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.edt_code_1)
    AppCompatEditText edtCode1;
    @BindView(R.id.edt_code_2)
    AppCompatEditText edtCode2;
    @BindView(R.id.edt_code_3)
    AppCompatEditText edtCode3;
    @BindView(R.id.edt_code_4)
    AppCompatEditText edtCode4;
    @BindView(R.id.edt_code_5)
    AppCompatEditText edtCode5;
    @BindView(R.id.edt_code_6)
    AppCompatEditText edtCode6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_verification);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();

    }

    private void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        edtCode1.addTextChangedListener(EditTextUtils.getTextWatcher(edtCode1, edtCode2,false));
        edtCode2.addTextChangedListener(EditTextUtils.getTextWatcher(edtCode2, edtCode3, false));
        edtCode3.addTextChangedListener(EditTextUtils.getTextWatcher(edtCode3, edtCode4, false));
        edtCode4.addTextChangedListener(EditTextUtils.getTextWatcher(edtCode4, edtCode5, false));
        edtCode5.addTextChangedListener(EditTextUtils.getTextWatcher(edtCode5, edtCode6, false));
        edtCode6.addTextChangedListener(EditTextUtils.getTextWatcher(edtCode6, null, true));

        edtCode1.setOnKeyListener(EditTextUtils.onClickDelete(null, edtCode1, true));
        edtCode2.setOnKeyListener(EditTextUtils.onClickDelete(edtCode1, edtCode2, false));
        edtCode3.setOnKeyListener(EditTextUtils.onClickDelete(edtCode2, edtCode3, false));
        edtCode4.setOnKeyListener(EditTextUtils.onClickDelete(edtCode3, edtCode4, false));
        edtCode5.setOnKeyListener(EditTextUtils.onClickDelete(edtCode4, edtCode5, false));
        edtCode6.setOnKeyListener(EditTextUtils.onClickDelete(edtCode5, edtCode6, false));

    }

    private void initSetup() {
        btnSubmit.setOnClickListener(this);
        tvResendCode.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

    private void fetchData() {

    }

    private boolean valid() {
        if (TextUtils.isEmpty(edtCode1.getText().toString().trim()) ||
            TextUtils.isEmpty(edtCode2.getText().toString().trim()) ||
            TextUtils.isEmpty(edtCode3.getText().toString().trim()) ||
            TextUtils.isEmpty(edtCode4.getText().toString().trim()) ||
            TextUtils.isEmpty(edtCode5.getText().toString().trim()) ||
            TextUtils.isEmpty(edtCode6.getText().toString().trim())) {
            Toast.makeText(this,"input your OTP", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(ForgotPasswordVerificationActivity.this, ChangePasswordActivity.class);
            startActivity(intent);

        }

        if (tvResendCode == view) {
            Toast.makeText(this,"On Develop", Toast.LENGTH_SHORT).show();
        }

    }
}