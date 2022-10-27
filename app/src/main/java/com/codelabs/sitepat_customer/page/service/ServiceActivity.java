package com.codelabs.sitepat_customer.page.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.page.service.body_paint.BodyPaintActivity;
import com.codelabs.sitepat_customer.page.service.booking_service.BookingServiceActivity;
import com.codelabs.sitepat_customer.page.service.home_service.HomeServiceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView btnBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_booking)
    LinearLayout btnBookingService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_home_service)
    LinearLayout btnHomeService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_body_paint)
    LinearLayout btnBodyPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        initSetup();
    }

    private void initSetup() {
        btnBack.setOnClickListener(this);
        btnBookingService.setOnClickListener(this);
        btnHomeService.setOnClickListener(this);
        btnBodyPaint.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if (btnBack == view){
            finish();
        }
        if (btnBookingService == view){
            Intent intent = new Intent(ServiceActivity.this, BookingServiceActivity.class);
            startActivity(intent);
        }
        if (btnHomeService == view){
            Intent intent = new Intent(ServiceActivity.this, HomeServiceActivity.class);
            startActivity(intent);
        }
        if (btnBodyPaint == view){
            Intent intent = new Intent(ServiceActivity.this, BodyPaintActivity.class);
            startActivity(intent);
        }
    }
}