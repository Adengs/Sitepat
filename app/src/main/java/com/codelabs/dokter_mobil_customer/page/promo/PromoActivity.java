package com.codelabs.dokter_mobil_customer.page.promo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.adapter.PromoAdapter;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.viewmodel.Promo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_promo)
    RecyclerView rvPromo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_no_data)
    AppCompatTextView tvNoData;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_no_data)
    RelativeLayout containerNoData;

    PromoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        ButterKnife.bind(this);
        initView();
        fetchData();

    }

    private void initView() {
        containerNoData.setVisibility(View.GONE);
        ivBack.setOnClickListener(this);

        rvPromo.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PromoAdapter(this);
        mAdapter.setData(new ArrayList<>());
        rvPromo.setAdapter(mAdapter);
    }

    private void fetchData() {
        loadPromoBanner();
    }

    public void loadPromoBanner() {
        showDialogProgress("Getting data promo");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Promo> call = apiService.getPromo(auth);
        call.enqueue(new Callback<Promo>() {
            @Override
            public void onResponse(@NonNull Call<Promo> call, @NonNull Response<Promo> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    Promo data = response.body();
                    if (response.code() == 200) {
                        mAdapter.setData(data.getDataPromo().getItemsPromo());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Promo> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(R.string.toast_onfailure);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }
    }
}