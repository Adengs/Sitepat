package com.codelabs.sitepat_customer.page.select;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.SelectTypeCarAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.viewmodel.BrandTypesCar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCarTypeActivity extends BaseActivity implements View.OnClickListener {

    AppCompatImageView ivBack;
    AppCompatTextView tvTitle, tvNoData;
    AppCompatEditText edtSearchType;
    RecyclerView rvTypeCar;
    RelativeLayout containerNoData;

    SelectTypeCarAdapter mAdapter;
    private String keyword = "";
    private int idTypeCar = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_type);
        ButterKnife.bind(this);
        getPrefData();
        initView();
        initEvent();
        initSetup();
        fetchData();
    }

    private void getPrefData() {
        Intent intent = getIntent();
        idTypeCar = intent.getIntExtra("id_brand",-1);
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        edtSearchType = findViewById(R.id.et_search_type);
        rvTypeCar = findViewById(R.id.rv_type_car);
        tvNoData = findViewById(R.id.tv_no_data);
        containerNoData = findViewById(R.id.container_no_data);

        rvTypeCar.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SelectTypeCarAdapter(this);
        mAdapter.setData(new ArrayList<>());
        rvTypeCar.setAdapter(mAdapter);

    }

    private void initEvent() {
        ivBack.setOnClickListener(this);
        edtSearchType.setOnClickListener(this);
        containerNoData.setVisibility(View.GONE);
    }

    private void initSetup() {
        functionSearch();
    }

    private void fetchData() {
        loadTypeCar();
    }

    private void functionSearch() {
        edtSearchType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = s.toString();
                searchTypeCar();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void loadTypeCar() {
        showDialogProgress("Getting data type car");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<BrandTypesCar> call = apiService.getBrandTypesCar(auth, idTypeCar, keyword);
        call.enqueue(new Callback<BrandTypesCar>() {
            @Override
            public void onResponse(@NonNull Call<BrandTypesCar> call, @NonNull Response<BrandTypesCar> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    BrandTypesCar data = response.body();
                    if (response.code() == 200) {
                        assert data != null;
                        mAdapter.setData(data.getDataBrandTypeCar().getItemsBrandTypes());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BrandTypesCar> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void searchTypeCar() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<BrandTypesCar> call = apiService.getBrandTypesCar(auth, idTypeCar, keyword);
        call.enqueue(new Callback<BrandTypesCar>() {
            @Override
            public void onResponse(@NonNull Call<BrandTypesCar> call, @NonNull Response<BrandTypesCar> response) {
                if (response.isSuccessful()) {
                    BrandTypesCar data = response.body();
                    if (response.code() == 200) {
                        assert data != null;
                        mAdapter.setData(data.getDataBrandTypeCar().getItemsBrandTypes());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BrandTypesCar> call, @NonNull Throwable t) {
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