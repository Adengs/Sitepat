package com.codelabs.dokter_mobil_customer.page.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

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
import com.codelabs.dokter_mobil_customer.helper.Utils;
import com.codelabs.dokter_mobil_customer.page.login.LoginActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.DoPost;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.samlss.lighter.Lighter;
import me.samlss.lighter.interfaces.OnLighterListener;
import me.samlss.lighter.parameter.Direction;
import me.samlss.lighter.parameter.LighterParameter;
import me.samlss.lighter.parameter.MarginOffset;
import me.samlss.lighter.shape.RectShape;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    /* declare layout component in here*/

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

    /*declare global variable in here*/

    private Boolean showPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        if (getIntent().getBooleanExtra("IS_HIGHLIGHT_REGISTER",false)){
            highlightRegister();
        }else{
            initSetup();
        }

    }

    private void highlightRegister(){
        Lighter.with(this)
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.container_input)
                        .setTipView(Utils.INSTANCE.createCommonTipViewTop(this, "Pada halaman register, isikan semua data"))
                        .setLighterShape(new RectShape(5, 5, 30))
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewRelativeOffset(new MarginOffset(150, 0, 30, 0))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.btn_register)
                        .setTipView(Utils.INSTANCE.createCommonTipViewBottom(this, "Kemudian klik tombol Register"))
                        .setLighterShape(new RectShape(5, 5, 30))
                        .setTipViewRelativeDirection(Direction.BOTTOM)
                        .setTipViewRelativeOffset(new MarginOffset(150, 0, 30, 0))
                        .build())
                .setOnLighterListener(new OnLighterListener() {
                    @Override
                    public void onShow(int index) {

                    }

                    @Override
                    public void onDismiss() {
                        finish();
                    }
                })
                .show();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void initSetup() {
        btnRegister.setOnClickListener(this);
        imgEyePassword.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

    /* validation field in here*/

    private boolean valid() {
        if (TextUtils.isEmpty(Objects.requireNonNull(edtFullname.getText()).toString().trim())) {
            showToast("please enter your full name");
            return false;
        }

        if (!RecentUtils.isEmailValid(Objects.requireNonNull(edtEmail.getText()).toString().trim())) {
            showToast("please enter your valid email");
            return false;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(edtPhone.getText()).toString().trim())) {
            showToast("please enter your phone number");
            return false;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(edtPassword.getText()).toString().trim())) {
            showToast("please enter your password");
            return false;
        }
        return true;
    }

    /*function response data from api in here*/

    public void doRegister() {
        if (!valid())
            return;

        Map<String, String> params = new HashMap<>();
        params.put("fullname", Objects.requireNonNull(edtFullname.getText()).toString().trim());
        params.put("email", Objects.requireNonNull(edtEmail.getText()).toString().trim());
        params.put("phoneNo", Objects.requireNonNull(edtPhone.getText()).toString().trim());
        params.put("password", Objects.requireNonNull(edtPassword.getText()).toString().trim());
        showDialogProgress("Send data register");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
        Call<DoPost> call = apiService.doRegister(auth,params);
        call.enqueue(new Callback<DoPost>() {
            @Override
            public void onResponse(@NonNull Call<DoPost> call, @NonNull Response<DoPost> data) {
                hideDialogProgress();
                if (data.isSuccessful()) {
                    if (data.code() == 200) {
                        showToast("SUCCESS");
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
            public void onFailure(@NonNull Call<DoPost> call, @NonNull Throwable t) {
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