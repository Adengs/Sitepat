package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.codelabs.sitepat_customer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_edit)
    AppCompatTextView btnEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_complete)
    AppCompatTextView btnComplete;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_con_pay)
    AppCompatTextView conPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        initSetup();
    }

    private void initSetup() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetContact bottomSheetContact = new BottomSheetContact();
                bottomSheetContact.show(getSupportFragmentManager(), "Dialog");
            }
        });
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetOutlet bottomSheetOutlet = new BottomSheetOutlet();
                bottomSheetOutlet.show(getSupportFragmentManager(), "Dialog");
            }
        });
        conPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });
    }
}