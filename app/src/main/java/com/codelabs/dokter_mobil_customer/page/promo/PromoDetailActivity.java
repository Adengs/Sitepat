package com.codelabs.dokter_mobil_customer.page.promo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.PromoDetail;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoDetailActivity extends BaseActivity implements View.OnClickListener {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_title)
    AppCompatTextView tvTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_promo)
    AppCompatImageView ivPromo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_periode_promo)
    AppCompatTextView tvPeriodePromo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_detail_promo)
    AppCompatTextView tvDetailPromo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_promo)
    RelativeLayout containerPromo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_no_data)
    AppCompatTextView tvNoData;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_no_data)
    RelativeLayout containerNoData;

    int idPromo = -1;
    private PromoDetail.DataPromoDetail responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_detail);
        ButterKnife.bind(this);
        getPrevData();
        initView();
        fetchData();
    }

    private void getPrevData() {
        Intent intent = getIntent();
        idPromo = intent.getIntExtra("promo_id",1);
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        ivBack.setOnClickListener(this);
        tvTitle.setText(R.string.promo);
        containerNoData.setVisibility(View.GONE);
    }

    private void fetchData() {
        loadDataPromo();
    }

    public void loadDataPromo() {
        showDialogProgress("Getting data promo detail");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<PromoDetail> call = apiService.getPromoDetail(auth, idPromo);
        call.enqueue(new Callback<PromoDetail>() {
            @Override
            public void onResponse(@NonNull Call<PromoDetail> call, @NonNull Response<PromoDetail> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    PromoDetail data = response.body();
                    if (response.code() == 200) {
                        responseData = data.getDataPromoDetail();
                        dataPromoDetail();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    containerPromo.setVisibility(View.GONE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull  Call<PromoDetail> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    containerNoData.setVisibility(View.VISIBLE);
                    containerPromo.setVisibility(View.GONE);
                    tvNoData.setText(R.string.toast_onfailure);
                }
            }
        });
    }

    public void dataPromoDetail() {
        Picasso.get()
                .load(responseData.getImage())
                .fit().centerCrop()
                .into(ivPromo);

        tvPeriodePromo.setText(RecentUtils.formatDateToDateDM(responseData.getPeriod_start()) + " - " + RecentUtils.formatDateToDateDMY(responseData.getPeriod_end()));
        tvDetailPromo.setText(responseData.getPromo_desc());
    }


    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }
    }
}