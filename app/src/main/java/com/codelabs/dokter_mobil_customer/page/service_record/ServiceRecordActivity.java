package com.codelabs.dokter_mobil_customer.page.service_record;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceRecordActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_title)
    AppCompatTextView tvTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_invoice_number)
    AppCompatTextView tvInvoiceNumber;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_invoice_date)
    AppCompatTextView tvInvoiceDate;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_value_subtotal)
    AppCompatTextView tvValueSubtotal;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_value_total)
    AppCompatTextView tvValueTotal;

    String invoiceNumber = "";
    String invoiceDate = "";
    String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_record);
        ButterKnife.bind(this);
        initView();
        getPrefData();
        fetchData();
    }

    private void getPrefData() {
        Intent intent = getIntent();
        invoiceNumber = intent.getStringExtra("invoice_code");
        invoiceDate = intent.getStringExtra("date_invoice");
        totalAmount = intent.getStringExtra("total_amount");

        tvInvoiceNumber.setText(invoiceNumber);
        tvInvoiceDate.setText(RecentUtils.newformatDateTimeToDateYYYYMMDDnotEEEE(invoiceDate));
        tvValueSubtotal.setText(RecentUtils.formatRupiah(Double.parseDouble(totalAmount)));
        tvValueTotal.setText(RecentUtils.formatRupiah(Double.parseDouble(totalAmount)));
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        ivBack.setOnClickListener(this);
        tvTitle.setText(getString(R.string.service_record));
    }

    private void fetchData() {

    }


    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }
    }
}