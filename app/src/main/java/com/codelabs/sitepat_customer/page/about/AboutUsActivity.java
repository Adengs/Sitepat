package com.codelabs.sitepat_customer.page.about;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.AboutUsAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.viewmodel.AboutUs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener{

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_about_us)
    RecyclerView rvAboutUs;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_about)
    RelativeLayout containerAbout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_no_data)
    RelativeLayout containerNoData;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_no_data)
    AppCompatTextView tvNoData;

    AboutUsAdapter mAdapter;
    List<AboutUs.ItemsAbout> responseAbout = new ArrayList<>();

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
        containerNoData.setVisibility(View.GONE);

        rvAboutUs.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AboutUsAdapter(this);
        mAdapter.setData(new ArrayList<>());
        rvAboutUs.setAdapter(mAdapter);
    }

    private void fetchData() {
        loadDataAbout();
    }

    private void emptyData() {

    }


    public void loadDataAbout() {
        showDialogProgress("Getting data about us");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<AboutUs> call = apiService.getAboutUs(auth);
        call.enqueue(new Callback<AboutUs>() {
            @Override
            public void onResponse(@NonNull Call<AboutUs> call, @NonNull Response<AboutUs> data) {
                hideDialogProgress();
                if (data.isSuccessful()) {
                    AboutUs response = data.body();
                    if (data.code() == 200) {
                        mAdapter.setData(response.getData().getItemsAbout());
                    } else {
                        containerNoData.setVisibility(View.VISIBLE);
                        containerAbout.setVisibility(View.GONE);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(data);
                    containerNoData.setVisibility(View.VISIBLE);
                    containerAbout.setVisibility(View.GONE);
                    tvNoData.setText(error.message());

                }
            }

            @Override
            public void onFailure(@NonNull Call<AboutUs> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    containerNoData.setVisibility(View.VISIBLE);
                    containerAbout.setVisibility(View.GONE);
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