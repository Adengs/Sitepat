package com.codelabs.dokter_mobil_customer.page.car_monitoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarMonitoringActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_title)
    AppCompatTextView tvTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_checking)
    FrameLayout btnChecking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_monitoring);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();
    }


    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        tvTitle.setText("Car Monitoring");

    }

    private void initSetup() {
        ivBack.setOnClickListener(this);
        btnChecking.setOnClickListener(this);
    }

    private void fetchData() {

    }


    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }

        if (btnChecking == view) {
            showToast("This menu on develop :)");
        }
    }
}