package com.codelabs.dokter_mobil_customer.page.about;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.adapter.AboutUsAdapter;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.viewmodel.AboutUs;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener{

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_about_us)
    RecyclerView rvAboutUs;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;

    AboutUsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        initView();
        fetchData();
    }

    private void initView() {
        ivBack.setOnClickListener(this);

        rvAboutUs.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AboutUsAdapter(this);
        mAdapter.setData(new ArrayList<>());
        rvAboutUs.setAdapter(mAdapter);
    }

    private void fetchData() {
        loadDataAbout();
    }

    public void loadDataAbout() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<AboutUs> call = apiService.getAboutUs(auth);
        call.enqueue(new Callback<AboutUs>() {
            @Override
            public void onResponse(@NonNull Call<AboutUs> call, @NonNull Response<AboutUs> data) {
                if (data.isSuccessful()) {
                    AboutUs response = data.body();
                    if (data.code() == 200) {
                        mAdapter.setData(response.getData().getItemsAbout());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(data);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AboutUs> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    showToast(getString(R.string.toast_onfailure));
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