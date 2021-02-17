package com.codelabs.dokter_mobil_customer.page.password;

import androidx.annotation.NonNull;
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
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.helper.EditTextUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.DoPost;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordVerificationActivity extends BaseActivity implements View.OnClickListener {

    /*declare layout component in here*/

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_resend_code)
    AppCompatTextView tvResendCode;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_desc_info_forgot_password)
    AppCompatTextView tvDescInfoUser;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_code_1)
    AppCompatEditText edtCode1;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_code_2)
    AppCompatEditText edtCode2;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_code_3)
    AppCompatEditText edtCode3;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_code_4)
    AppCompatEditText edtCode4;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_code_5)
    AppCompatEditText edtCode5;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_code_6)
    AppCompatEditText edtCode6;

    /*declare global variable in here*/

    String identity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_verification);
        ButterKnife.bind(this);
        getPrevData();
        initView();
        initSetup();

    }

    @Override
    protected void onPause() {
        super.onPause();
//        setEmptyCode();
    }

    private void getPrevData() {
        Intent intent = getIntent();
        identity = intent.getStringExtra("identity");
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        handleTextWatcherCode();
        tvDescInfoUser.setText("We have sent a 6 digits code to" + " " + identity + " " + ", Please input code here!.");
    }

    private void initSetup() {
        btnSubmit.setOnClickListener(this);
        tvResendCode.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }



    /*declare validation field in here*/

    private void handleTextWatcherCode() {
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

    private void setEmptyCode() {
        edtCode6.setText("");
        edtCode5.setText("");
        edtCode4.setText("");
        edtCode3.setText("");
        edtCode2.setText("");
        edtCode1.setText("");
    }

    /*function response data from API in here*/

    public void doSendOtp() {
        if (!valid())
            return;

        showDialogProgress("Send your OTP");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
        String otp =
                edtCode1.getText().toString().trim() +
                edtCode2.getText().toString().trim() +
                edtCode3.getText().toString().trim() +
                edtCode4.getText().toString().trim() +
                edtCode5.getText().toString().trim() +
                edtCode6.getText().toString().trim() ;

        Call<DoPost> call = apiService.doCheckOtp(auth, otp);
        call.enqueue(new Callback<DoPost>() {
            @Override
            public void onResponse(@NonNull Call<DoPost> call, @NonNull Response<DoPost> data) {
                hideDialogProgress();
                if (data.isSuccessful()) {
                    if (data.code() == 200) {
                        showToast("SUCCESS");
                        Intent intent = new Intent(ForgotPasswordVerificationActivity.this, ChangePasswordActivity.class);
                        intent.putExtra("otpCode", otp);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(data);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoPost> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void doResendOtp() {
        showDialogProgress("Resend OTP");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
        Call<DoPost> call = apiService.doResendOtp(auth, identity);
        call.enqueue(new Callback<DoPost>() {
            @Override
            public void onResponse(@NonNull Call<DoPost> call, @NonNull Response<DoPost> data) {
                hideDialogProgress();
                if (data.isSuccessful()) {
                    if (data.code() == 200) {
                        showToast("Please check your email to get OTP code");
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(data);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoPost> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    /*declare function click in here*/

    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }

        if (btnSubmit == view) {
           doSendOtp();
        }

        if (tvResendCode == view) {
           doResendOtp();
        }
    }
}