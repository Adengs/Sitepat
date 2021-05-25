package com.codelabs.dokter_mobil_customer.page.select;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.adapter.SelectBrandCarAdapter;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.viewmodel.BrandCar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCarBrandActivity extends BaseActivity implements View.OnClickListener {

    AppCompatImageView ivBack;
    AppCompatTextView tvTitle, tvNoData;
    AppCompatEditText edtSearchBrand;
    RecyclerView rvBrandCar;
    RelativeLayout containerNoData;

    SelectBrandCarAdapter mAdapter;
    private String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);
        ButterKnife.bind(this);
        initView();
        initEvent();
        initSetup();
        fetchData();
    }


    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        edtSearchBrand = findViewById(R.id.et_search_brand);
        rvBrandCar = findViewById(R.id.rv_brand_car);
        tvNoData = findViewById(R.id.tv_no_data);
        containerNoData = findViewById(R.id.container_no_data);

        rvBrandCar.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SelectBrandCarAdapter(this);
        mAdapter.setData(new ArrayList<>());
        rvBrandCar.setAdapter(mAdapter);

    }

    private void initEvent() {
        ivBack.setOnClickListener(this);
        edtSearchBrand.setOnClickListener(this);
        containerNoData.setVisibility(View.GONE);
    }


    private void initSetup() {
        functionSearch();
    }

    private void fetchData() {
        loadBrandCar();

    }

    private void functionSearch() {
        edtSearchBrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = s.toString();
                searchBrandCar();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void loadBrandCar() {
        showDialogProgress("Getting data brand car");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<BrandCar> call = apiService.getBrandCar(auth, keyword);
        call.enqueue(new Callback<BrandCar>() {
            @Override
            public void onResponse(@NonNull Call<BrandCar> call, @NonNull Response<BrandCar> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    BrandCar data = response.body();
                    if (response.code() == 200) {
                        assert data != null;
                        mAdapter.setData(data.getDataBrandCar().getItemsBrandCars());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BrandCar> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void searchBrandCar() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<BrandCar> call = apiService.getBrandCar(auth, keyword);
        call.enqueue(new Callback<BrandCar>() {
            @Override
            public void onResponse(@NonNull Call<BrandCar> call, @NonNull Response<BrandCar> response) {
                if (response.isSuccessful()) {
                    BrandCar data = response.body();
                    if (response.code() == 200) {
                        assert data != null;
                        mAdapter.setData(data.getDataBrandCar().getItemsBrandCars());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BrandCar> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(getString(R.string.toast_onfailure));
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