package com.codelabs.dokter_mobil_customer.page.password;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
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
import com.codelabs.dokter_mobil_customer.viewmodel.DoPost;

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

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    /*declare layout component in here*/

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
        if (getIntent().getBooleanExtra("IS_HIGHLIGHT_FORGOT",false)){
            highlightForgotPassword();
        }else {
            initSetup();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                break;
        }
    }

    private void highlightForgotPassword(){
        Lighter.with(this)
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.container_edt_email_phone)
                        .setTipView(Utils.INSTANCE.createCommonTipViewTop(this, "Pada halaman forgot password, isikan email atau nomor telepon yang terdaftar"))
                        .setLighterShape(new RectShape(5, 5, 30))
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewRelativeOffset(new MarginOffset(0, 0, 0, 0))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.btn_continue)
                        .setTipView(Utils.INSTANCE.createCommonTipViewBottom(this, "Kemudian klik tombol Continue"))
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
                        Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordVerificationActivity.class);
                        intent.putExtra("identity", "************");
                        intent.putExtra("IS_HIGHLIGHT_FORGOT",true);
                        startActivity(intent);
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
        btnContinue.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    /*validation field in here*/

    private boolean valid() {
        if (TextUtils.isEmpty(edtEmailPhone.getText().toString().trim())) {
            Toast.makeText(this,"please enter your email/ phone", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*function response data from api in here*/

    public void doForgotPassword() {
        if (!valid())
            return;

        showDialogProgress("Send your data");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
        Call<DoPost> call = apiService.doForgotPassword(auth, edtEmailPhone.getText().toString().trim());
        call.enqueue(new Callback<DoPost>() {
            @Override
            public void onResponse(@NonNull Call<DoPost> call, @NonNull Response<DoPost> data) {
                hideDialogProgress();
                if (data.isSuccessful()) {
                    if (data.code() == 200) {
                        showToast("SUCCESS");
                        Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordVerificationActivity.class);
                        intent.putExtra("identity", edtEmailPhone.getText().toString().trim());
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

    /*declare function click in here*/

    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }

        if (btnContinue == view) {
            doForgotPassword();
        }
    }
}