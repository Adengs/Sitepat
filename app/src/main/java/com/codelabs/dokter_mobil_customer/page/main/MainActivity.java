package com.codelabs.dokter_mobil_customer.page.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.codelabs.dokter_mobil_customer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        tvUsername.setOnClickListener(this);
//        containerOutlet.setOnClickListener(this);
//        containerMyAccount.setOnClickListener(this);
//        containerCarMonitoring.setOnClickListener(this);
//        containerSupport.setOnClickListener(this);
//        containerAbout.setOnClickListener(this);
//        containerSetting.setOnClickListener(this);

    }

    private void fetchData() {

    }


    @Override
    public void onClick(View view) {
    }
}