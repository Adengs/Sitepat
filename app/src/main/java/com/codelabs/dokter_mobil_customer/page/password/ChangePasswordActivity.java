package com.codelabs.dokter_mobil_customer.page.password;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
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

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    /*declare layout component in here*/

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

    /*declare global variable in here*/

    private Boolean showPassword = false;
    private Boolean showPasswordConf = false;
    String otpCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initView();
        getPrevData();
        if (getIntent().getBooleanExtra("IS_HIGHLIGHT_FORGOT",false)){
            highlightForgotPassword();
        }else {
            initSetup();
        }
    }

    private void highlightForgotPassword(){
        Lighter.with(this)
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.container_new_password)
                        .setTipView(Utils.INSTANCE.createCommonTipViewTop(this, "Masukkan password baru anda"))
                        .setLighterShape(new RectShape(5, 5, 30))
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewRelativeOffset(new MarginOffset(150, 0, 30, 0))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.btn_submit)
                        .setTipView(Utils.INSTANCE.createCommonTipViewBottom(this, "Kemudian klik tombol Submit untuk menyelesaikan pergantian password"))
                        .setLighterShape(new RectShape(5, 5, 30))
                        .setTipViewRelativeDirection(Direction.BOTTOM)
                        .setTipViewRelativeOffset(new MarginOffset(0, 0, 0, 0))
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
        ivBack.setOnClickListener(this);
        imgEyePassword.setOnClickListener(this);
        imgEyeConfPassword.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void getPrevData() {
        Intent intent = getIntent();
        otpCode = intent.getStringExtra("otpCode");
    }

    /*declare validation field in here*/

    private boolean valid() {
        if (TextUtils.isEmpty(Objects.requireNonNull(edtPassword.getText()).toString().trim())){
            showToast("please enter your password");
            return false;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(edtConfPassword.getText()).toString().trim())) {
            showToast("please confirm your password");
            return false;
        }

        if (!edtConfPassword.getText().toString().trim().equals(edtPassword.getText().toString().trim())) {
            showToast("password not matching");
            return false;
        }
        return true;
    }

    private void dialogSuccess() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChangePasswordActivity.this);
        alertDialog.setTitle("Successfully");
        alertDialog.setMessage("Your new password has been created!");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Done", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
        alert.getButton(alert.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red_text));
    }

    /*function response data API in here*/

    public void doResetPassword() {
        if (!valid())
            return;

        Map<String, String> params = new HashMap<>();
        params.put("otpCode", otpCode);
        params.put("password", edtPassword.getText().toString().trim());
        params.put("password_confirmation", edtConfPassword.getText().toString().trim());
        showDialogProgress("Send data new password");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
        Call<DoPost> call = apiService.doResetPassword(auth,params);
        call.enqueue(new Callback<DoPost>() {
            @Override
            public void onResponse(@NonNull Call<DoPost> call, @NonNull Response<DoPost> data) {
                hideDialogProgress();
                if (data.isSuccessful()) {
                    if (data.code() == 200) {
                        dialogSuccess();
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

    @Override
    public void onClick(View view) {
        if (ivBack == view) {
           finish();
        }

        if (btnSubmit == view) {
            doResetPassword();
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

        if (imgEyeConfPassword == view) {
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
}