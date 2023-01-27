package com.codelabs.sitepat_customer.page.order;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.NewProductAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.dialog.DialogProgress;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.viewmodel.MyOrder;
import com.codelabs.sitepat_customer.viewmodel.NewProduct;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrders extends BaseActivity implements View.OnClickListener  {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_ongoing)
    TextView onGoing;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_tab_ongoing)
    LinearLayout layOnGoing;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_finished)
    TextView finished;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_tab_finished)
    LinearLayout layTabFinished;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator_tab1)
    View tab1;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator_tab2)
    View tab2;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tab_all)
    TextView tabAll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tab_purchase)
    TextView tabPurchase;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tab_service)
    TextView tabService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_myorders)
    RecyclerView rvMyOrder;

    MyOrderAdapter myOrderAdapter;

    Integer page = 0;
    Integer tab = 0;
    String category = "";
    String all = "0";
    String service = "1";
    String purchase = "2";
    String status = "";
    String statusOnGoing = "0";
    String statusFinished = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ButterKnife.bind(this);
        initSetup();
        changePage();
        changeTab();

        rvMyOrder.setLayoutManager(new LinearLayoutManager(this));
        myOrderAdapter = new MyOrderAdapter(MyOrders.this);
        rvMyOrder.setAdapter(myOrderAdapter);

//        layOnGoing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page = 0;
//                changePage();
//                Log.e("cek", page.toString());
//            }
//        });
//
//        onGoing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page = 0;
//                changePage();
//                Log.e("cek", page.toString());
//            }
//        });
//
//        layTabFinished.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page = 1;
//                changePage();
//                Log.e("cek2", page.toString());
//            }
//        });
//
//        finished.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page = 1;
//                changePage();
//                Log.e("cek2", page.toString());
//            }
//        });

    }

    private void initSetup() {
        ivBack.setOnClickListener(this);
        onGoing.setOnClickListener(this);
        layOnGoing.setOnClickListener(this);
        finished.setOnClickListener(this);
        layTabFinished.setOnClickListener(this);
        tabAll.setOnClickListener(this);
        tabPurchase.setOnClickListener(this);
        tabService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }
        if (onGoing == view) {
            page = 0;
            changePage();
        }
        if (layOnGoing == view) {
            page = 0;
            changePage();
        }
        if (finished == view) {
            page = 1;
            changePage();
        }
        if (layTabFinished == view) {
            page = 1;
            changePage();
        }
        if (tabAll == view) {
            tab = 0;
            changeTab();
        }
        if (tabPurchase == view) {
            tab = 1;
            changeTab();
        }
        if (tabService == view) {
            tab = 2;
            changeTab();
        }
    }

    public void changePage(){
        if (page == 0){
            onGoing.setTextColor(getResources().getColor(R.color.white));
            tab1.setBackgroundResource(R.color.white);
            finished.setTextColor(getResources().getColor(R.color.grey_500));
            tab2.setBackgroundResource(R.color.black);
            tabAll.setBackgroundResource(R.drawable.background_tab_orange);
            tabAll.setTextColor(getResources().getColor(R.color.white));
            tabPurchase.setBackgroundResource(R.drawable.background_tab);
            tabPurchase.setTextColor(getResources().getColor(R.color.black));
            tabService.setBackgroundResource(R.drawable.background_tab);
            tabService.setTextColor(getResources().getColor(R.color.black));

            status = "1";
            category = "";
            loadData();
        }
        if (page == 1){
            finished.setTextColor(getResources().getColor(R.color.white));
            tab2.setBackgroundResource(R.color.white);
            onGoing.setTextColor(getResources().getColor(R.color.grey_500));
            tab1.setBackgroundResource(R.color.black);
            tabAll.setBackgroundResource(R.drawable.background_tab_orange);
            tabAll.setTextColor(getResources().getColor(R.color.white));
            tabPurchase.setBackgroundResource(R.drawable.background_tab);
            tabPurchase.setTextColor(getResources().getColor(R.color.black));
            tabService.setBackgroundResource(R.drawable.background_tab);
            tabService.setTextColor(getResources().getColor(R.color.black));

            status = "3";
            category = "";
            loadData();
        }
    }

    public void changeTab(){
        if (tab == 0){
            tabAll.setBackgroundResource(R.drawable.background_tab_orange);
            tabAll.setTextColor(getResources().getColor(R.color.white));
            tabPurchase.setBackgroundResource(R.drawable.background_tab);
            tabPurchase.setTextColor(getResources().getColor(R.color.black));
            tabService.setBackgroundResource(R.drawable.background_tab);
            tabService.setTextColor(getResources().getColor(R.color.black));

            category = "";
            loadData();
        }
        if (tab == 1){
            tabAll.setBackgroundResource(R.drawable.background_tab);
            tabAll.setTextColor(getResources().getColor(R.color.black));
            tabPurchase.setBackgroundResource(R.drawable.background_tab_orange);
            tabPurchase.setTextColor(getResources().getColor(R.color.white));
            tabService.setBackgroundResource(R.drawable.background_tab);
            tabService.setTextColor(getResources().getColor(R.color.black));

            category = "2";
            loadData();
        }
        if (tab == 2){
            tabAll.setBackgroundResource(R.drawable.background_tab);
            tabAll.setTextColor(getResources().getColor(R.color.black));
            tabPurchase.setBackgroundResource(R.drawable.background_tab);
            tabPurchase.setTextColor(getResources().getColor(R.color.black));
            tabService.setBackgroundResource(R.drawable.background_tab_orange);
            tabService.setTextColor(getResources().getColor(R.color.white));

            category = "1";
            loadData();
        }
    }

    public void loadData(){
//        Toast.makeText(context, myOrderAdapter.orderList.size(), Toast.LENGTH_SHORT).show();
//        Log.e("TAG", "initSetup: " + myOrderAdapter.orderList.size() );
//        showDialogProgress("Getting My Order");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        String custId = String.valueOf(DataManager.getInstance().getCustomerId());

        Call<MyOrder> call = apiService.getListMyOrder(auth, category, custId, status);
        call.enqueue(new Callback<MyOrder>() {
            @Override
            public void onResponse(@NonNull Call<MyOrder> call, @NonNull Response<MyOrder> response) {
//                hideDialogProgress();
                if (response.isSuccessful()) {
                    MyOrder data = response.body();
                    if (response.code() == 200) {
                        myOrderAdapter.setData(data.getData());
                        Log.e("TAG", "onResponse: " + data.getData().size() );
//                        hideDialogProgress();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyOrder> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
//                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

//    public void showDialogProgress(String message) {
//        if (message != null) {
//            dialogProgress = new DialogProgress(this, message, true);
//            dialogProgress.setCancelable(false);
//            dialogProgress.show();
//        } else {
//            dialogProgress = new DialogProgress(this, "Loading ...", false);
//            dialogProgress.setCancelable(false);
//            dialogProgress.show();
//        }
//    }
//
//    public void hideDialogProgress() {
//        if (dialogProgress != null) {
//            if (dialogProgress.isShowing()) {
//                dialogProgress.dismiss();
//            }
//        }
//    }

}