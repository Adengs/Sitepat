package com.codelabs.sitepat_customer.page.service_record;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.ServiceRecordHorizontalAdapter;
import com.codelabs.sitepat_customer.adapter.ServiceRecordItemAdapter;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.utils.RecentUtils;
import com.codelabs.sitepat_customer.viewmodel.ServiceRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_value_ppn)
    AppCompatTextView tvValuePpn;

    String invoiceNumber = "";
    String invoiceDate = "";
    String totalAmount = "";
    String subtotal = "";
    String ppn = "";

    ServiceRecordItemAdapter serviceRecordItemAdapter;
    ServiceRecordHorizontalAdapter serviceRecordHorizontalAdapter;
    private List<ServiceRecord.Orders> ordersList;
    private List<ServiceRecord.Orders> serviceRecord;
    private List<ServiceRecord.ItemsService> itemsServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_record);
        ButterKnife.bind(this);
        initView();
        getPrefData();
        fetchData();
    }

    @SuppressLint("SetTextI18n")
    private void getPrefData() {
        Intent intent = getIntent();
//        getItemsService = (ServiceRecord.Orders) intent.getSerializableExtra("DATA");

        invoiceNumber = intent.getStringExtra("invoice_code");
        invoiceDate = intent.getStringExtra("date_invoice");
        totalAmount = intent.getStringExtra("total_amount");
        subtotal = intent.getStringExtra("subtotal");
        ppn = intent.getStringExtra("ppn");
        serviceRecord = (List<ServiceRecord.Orders>) intent.getSerializableExtra("servicelist");



        tvInvoiceNumber.setText(invoiceNumber);
        tvInvoiceDate.setText(RecentUtils.newformatDateTimeToDateYYYYMMDDnotEEEE(invoiceDate));
        tvValueSubtotal.setText("Rp." + " " + RecentUtils.toCurrency(subtotal.replace(".00", "")));
        tvValueTotal.setText("Rp. " + " " + RecentUtils.toCurrency(totalAmount.replace(".00","")));
        tvValuePpn.setText("Rp. " + " " + RecentUtils.toCurrency(ppn.replace(".00","")));

        rvServiceRecord.setLayoutManager(new LinearLayoutManager(this));
        serviceRecordItemAdapter = new ServiceRecordItemAdapter(this);
        for (int itemService = 0; itemService<serviceRecord.size(); itemService++) {
            serviceRecordItemAdapter.setData(serviceRecord.get(itemService).getItemsService());
        }

        rvServiceRecord.setAdapter(serviceRecordItemAdapter);


    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        ivBack.setOnClickListener(this);
        tvTitle.setText(getString(R.string.service_record));

      /*  rvServiceRecord.setLayoutManager(new LinearLayoutManager(this));
        serviceRecordItemAdapter = new ServiceRecordItemAdapter(this);
        serviceRecordItemAdapter.setData(new ArrayList<>());
        rvServiceRecord.setAdapter(serviceRecordItemAdapter);*/

    }

    private void fetchData() {
//        loadCustomerCarDetail();
    }

    private void loadCustomerCarDetail() {
        showDialogProgress("Getting data detail");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<ServiceRecord>  call = apiService.getDetailCar(auth, DataManager.getInstance().getCustomerCarId());
        call.enqueue(new Callback<ServiceRecord>() {
            @Override
            public void onResponse(@NonNull Call<ServiceRecord> call,@NonNull Response<ServiceRecord> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    ServiceRecord data = response.body();
                    if (response.code() == 200) {
                        assert data != null;

//                        serviceRecordItemAdapter.setData(data.getDataServiceRecord().getServiceRecords().get(0).getOrders().get(0).getItemsService());

                        for (int service=0; service<data.getDataServiceRecord().getServiceRecords().size(); service++) {
                            ordersList = data.getDataServiceRecord().getServiceRecords().get(service).getOrders();
                            for (int order=0; order<ordersList.size(); order++) {
                                itemsServices = ordersList.get(order).getItemsService();
                                serviceRecordItemAdapter.setData(itemsServices);
                            }


                        }

                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<ServiceRecord> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();

                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void dataInvoiceNumber() {
//
    }


    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }
    }
}