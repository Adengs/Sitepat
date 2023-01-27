package com.codelabs.sitepat_customer.page.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.page.main.MainActivity;
import com.codelabs.sitepat_customer.page.shop.PaymentMethodActivity;
import com.codelabs.sitepat_customer.page.shop.ShopActivity;
import com.codelabs.sitepat_customer.viewmodel.DetailMyOrder;
import com.codelabs.sitepat_customer.viewmodel.MyOrder;
import com.codelabs.sitepat_customer.viewmodel.TypeService;
import com.google.android.material.imageview.ShapeableImageView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderActivity extends BaseActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView btnBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.code_text)
    TextView tvCode;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.date_order)
    TextView tvDateOrder;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.card_status)
    CardView cardStatus;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_status)
    TextView textStatus;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tp_price)
    TextView tpPrice;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tp_price2)
    TextView tpPrice2;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.discount)
    TextView discount;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.total_price)
    TextView totalPrice;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.name)
    TextView name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.address)
    TextView address;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.contact)
    TextView contact;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.email)
    TextView email;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.province)
    TextView province;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.city)
    TextView city;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.outlet)
    TextView outlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.datetime)
    TextView dateTime;

    DetailOrderAdapter detailOrderAdapter;
    private List<DetailMyOrder.ItemsEntity> itemOrderList = new ArrayList<>();

    Integer transactionId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        ButterKnife.bind(this);
        getPrevData();
        initView();
        fetchData();
        initSetup();
    }

    private void getPrevData() {
        Intent intent = getIntent();
        transactionId = intent.getIntExtra("transaction_id", 0);

//        DataManager.getInstance().setTransactionId("");
    }

    private void initSetup() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataManager.getInstance().getTransactionId() != ""){
                    Intent intent = new Intent(DetailOrderActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    DataManager.getInstance().setTransactionId("");
                }
                else{
                    finish();
                }
            }
        });

        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        detailOrderAdapter = new DetailOrderAdapter(DetailOrderActivity.this);
        rvOrder.setAdapter(detailOrderAdapter);
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void fetchData() {
        loadItemDetail();
    }

    private void loadItemDetail() {
        showDialogProgress("Getting Detail Order");
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();

        Call<DetailMyOrder> call = apiService.getMyOrderDetail(auth, transactionId);
        call.enqueue(new Callback<DetailMyOrder>() {
            @Override
            public void onResponse(@NonNull Call<DetailMyOrder> call, @NonNull Response<DetailMyOrder> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    DetailMyOrder data = response.body();
                    if (response.code() == 200) {
//                        showToast(String.valueOf(detailOrderAdapter.detailOrderList.size()));
//                        showToast(String.valueOf(data.getData().getOrders().get(0).getItems().size()));
                        detailOrderAdapter.setData(data.getData().getOrders().get(0).getItems());
//                        for (int i = 0; i < data.getData().getOrders().get(0).getItems().size(); i++) {
//                            detailOrderAdapter.setData(data.getData().getOrders().get(0).getItems());
//                        }

                        String formatTpPrice = rupiah.format(new BigDecimal(data.getData().getTotalTransaction()).longValue());
                        String formatTpPrice2 = rupiah.format(new BigDecimal(data.getData().getTotalTransaction()).longValue());
                        String formatTotalPrice = rupiah.format(new BigDecimal(data.getData().getTotalTransaction()).longValue());

                        tvCode.setText(data.getData().getTransactionInvoiceCode());
                        tvDateOrder.setText(formatDateToDateDMY(data.getData().getCreatedAt()));
                        textStatus.setText(data.getData().getTransactionStatus());
                        if (data.getData().getTransactionStatus().equals("On Going")){
                            cardStatus.setCardBackgroundColor(DetailOrderActivity.this.getResources().getColor(R.color.green_text));
                        }else{
                            cardStatus.setCardBackgroundColor(DetailOrderActivity.this.getResources().getColor(R.color.grey_500));
                            textStatus.setText("Finish");
                        }
                        tpPrice.setText(formatTpPrice.replace("Rp","").replace(",00",""));
                        tpPrice2.setText(formatTpPrice.replace("Rp","").replace(",00",""));
                        totalPrice.setText(formatTpPrice.replace("Rp","").replace(",00",""));

                        if (data.getData().getOrderContactInformation() != null){
                            name.setText(data.getData().getOrderContactInformation().getName());
                            address.setText(data.getData().getOrderContactInformation().getAddress());
                            contact.setText(data.getData().getOrderContactInformation().getContact());
                            email.setText(data.getData().getOrderContactInformation().getEmail());
                        }

                        if (data.getData().getOrderOutletInformation() != null){
                            province.setText(data.getData().getOrderOutletInformation().getProvince());
                            city.setText(data.getData().getOrderOutletInformation().getCity());
                            outlet.setText(data.getData().getOrderOutletInformation().getOutletName());
                            dateTime.setText(formatDateToDateDMY(data.getData().getOrderOutletInformation().getCreatedAt()));
                        }
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailMyOrder> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public static String formatDateToDateDMY(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat toFormat = new SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (DataManager.getInstance().getTransactionId() != ""){
            Intent intent = new Intent(DetailOrderActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            DataManager.getInstance().setTransactionId("");
        }
        else{
            finish();
        }
    }
}