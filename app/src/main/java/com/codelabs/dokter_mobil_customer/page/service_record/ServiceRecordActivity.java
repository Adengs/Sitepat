package com.codelabs.dokter_mobil_customer.page.service_record;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.adapter.ServiceRecordItemAdapter;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.ServiceRecord;
import com.google.gson.Gson;

import java.util.ArrayList;

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
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_service_record)
    RecyclerView rvServiceRecord;

    String invoiceNumber = "";
    String invoiceDate = "";
    String totalAmount = "";

//    ArrayList<ServiceRecord.Orders> getItemsService = new ArrayList<>();
    ServiceRecord.Orders getItemsService;

    ServiceRecordItemAdapter serviceRecordItemAdapter;

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

//        getItemsService = new Gson().fromJson(intent.getStringExtra("item_service"), ServiceRecord.Orders.class);

        Bundle extra = getIntent().getBundleExtra("extra");
        ArrayList<ServiceRecord.Orders> objects = (ArrayList<ServiceRecord.Orders>) extra.getSerializable("objects");

        rvServiceRecord.setLayoutManager(new LinearLayoutManager(this));
        serviceRecordItemAdapter = new ServiceRecordItemAdapter(this);
        rvServiceRecord.setAdapter(serviceRecordItemAdapter);
        serviceRecordItemAdapter.setData(objects.get(1).getItemsService());



        tvInvoiceNumber.setText(invoiceNumber);
        tvInvoiceDate.setText(RecentUtils.newformatDateTimeToDateYYYYMMDDnotEEEE(invoiceDate));
        tvValueSubtotal.setText("Rp." + " " + RecentUtils.toCurrency(totalAmount.replace(".00", "")));
        tvValueTotal.setText("Rp. " + " " + RecentUtils.toCurrency(totalAmount.replace(".00","")));

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