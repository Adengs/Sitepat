package com.codelabs.dokter_mobil_customer.page.support;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    PromoAdapter promoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ButterKnife.bind(this);
        promoAdapter = new PromoAdapter(getApplicationContext());
        viewPager.setAdapter(promoAdapter);
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
                        promoAdapter.setData(data.getDataPromo().getItemsPromo());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Promo> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }
}