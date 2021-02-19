package com.codelabs.dokter_mobil_customer.page.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.page.about.AboutUsActivity;
import com.codelabs.dokter_mobil_customer.page.account.MyAccountActivity;
import com.codelabs.dokter_mobil_customer.page.setting.SettingActivity;
import com.codelabs.dokter_mobil_customer.viewmodel.Profile;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_username)
    AppCompatTextView tvUsername;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_outlet)
    CardView containerOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_my_account)
    CardView containerMyAccount;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_car_monitoring)
    CardView containerCarMonitoring;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_support)
    CardView containerSupport;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_about)
    CardView containerAbout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_setting)
    CardView containerSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();
    }

    private void initView() {

    }

    private void initSetup() {
        containerOutlet.setOnClickListener(this);
        containerMyAccount.setOnClickListener(this);
        containerCarMonitoring.setOnClickListener(this);
        containerSupport.setOnClickListener(this);
        containerAbout.setOnClickListener(this);
        containerSetting.setOnClickListener(this);

    }

    private void fetchData() {
        loadProfile();
    }

    public void loadProfile() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Profile> call = apiService.getProfile(auth);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> data) {
                if (data.isSuccessful()) {
                    Profile response = data.body();
                    if (data.code() == 200) {
                        tvUsername.setText(response.getDataProfile().getCustomerName());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(data);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (containerSetting == view) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }

        if (containerAbout == view) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        }

        if (containerOutlet == view) {
            showToast("This menu on develop :(");
        }

        if (containerSupport == view) {
            showToast("This menu on develop :(");
        }

        if (containerCarMonitoring == view) {
            showToast("This menu on develop :(");
        }

        if (containerMyAccount == view) {
            Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
            startActivity(intent);
        }
    }
}