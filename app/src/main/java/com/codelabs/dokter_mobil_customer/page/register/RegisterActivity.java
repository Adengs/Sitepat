package com.codelabs.dokter_mobil_customer.page.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.page.login.LoginActivity;
import com.codelabs.dokter_mobil_customer.page.main.MainActivity;
import com.codelabs.dokter_mobil_customer.utils.BaseActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.DoPost;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_phone)
    AppCompatEditText edtPhone;


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

    public void doRegister() {
        if (!valid())
            return;

        Map<String, String> params = new HashMap<>();
        params.put("fullname", edtFullname.getText().toString().trim());
        params.put("email", edtEmail.getText().toString().trim());
        params.put("phoneNo", edtPhone.getText().toString().trim());
        params.put("password", edtPassword.getText().toString().trim());
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
        Call<DoPost> call = apiService.doRegister(auth,params);
        call.enqueue(new Callback<DoPost>() {
            @Override
            public void onResponse(@NonNull Call<DoPost> call, @NonNull Response<DoPost> data) {

                if (data.isSuccessful()) {
                    if (data!= null) {
                        if (data.code() == 200) {
                            Toast.makeText(RegisterActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, String.valueOf(data.code()),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoPost> call, @NonNull Throwable t) {
                if (call.isCanceled()){
                    Toast.makeText(RegisterActivity.this,"Network Failure :( please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

        if (TextUtils.isEmpty(edtPhone.getText().toString().trim())) {
            Toast.makeText(this,"please enter your phone number", Toast.LENGTH_SHORT).show();
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
            doRegister();
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