package com.codelabs.dokter_mobil_customer.page.walkthrough;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.adapter.WalkthroughAdapter;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.page.login.LoginActivity;
import com.codelabs.dokter_mobil_customer.viewmodel.GetWalkThrough;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalkthroughActivity extends BaseActivity implements View.OnClickListener {

    /*declare layout component in here*/

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_skip)
    AppCompatTextView tvSkip;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next)
    AppCompatButton btnNext;

    /*declare global variable in here*/

    private WalkthroughAdapter mAdapter;
    Integer position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();
    }

    private void initView() {
        mAdapter = new WalkthroughAdapter(getApplicationContext());
        viewPager.setAdapter(mAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.grayBackground));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void initSetup() {
        btnNext.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
    }

    private void fetchData() {
        loadDataWalkthrough();
    }

    public void loadDataWalkthrough() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
        Call<GetWalkThrough> call = apiService.getWalkthrough(auth, "asc");
        call.enqueue(new Callback<GetWalkThrough>() {
            @Override
            public void onResponse(@NonNull Call<GetWalkThrough> call, @NonNull Response<GetWalkThrough> data) {
                if (data.isSuccessful()) {
                    GetWalkThrough response = data.body();
                    if (data.code() == 200) {
                        mAdapter.setData(response.getData().getItemsWalkthrough());
                        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(data);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetWalkThrough> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    private void launchHomeScreen() {
        DataManager.getInstance().setFirstInstall(false);
        startActivity(new Intent(WalkthroughActivity.this, LoginActivity.class));
        finish();
    }



    @Override
    public void onClick(View view) {
        if (tvSkip == view) {
           launchHomeScreen();
        }

        if (btnNext == view) {
            int current = getItem( +1);
            if (current < mAdapter.getCount()) {
                viewPager.setCurrentItem(current);
            } else {
                launchHomeScreen();
            }
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
            position = i;
            if (i == 0) {
               tvSkip.setText(getString(R.string.skip));
               btnNext.setText(getString(R.string.next));
            } else {
                tvSkip.setText("");
                btnNext.setText(getString(R.string.next));
            }

            if (position == mAdapter.getCount() -1) {
                tvSkip.setText("");
                btnNext.setText(getString(R.string.con));
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}