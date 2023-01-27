package com.codelabs.sitepat_customer.page.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.page.service.body_paint.BodyPaintActivity;
import com.codelabs.sitepat_customer.page.service.booking_service.BookingServiceActivity;
import com.codelabs.sitepat_customer.page.service.home_service.HomeServiceActivity;
import com.codelabs.sitepat_customer.viewmodel.CategoryService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView btnBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_booking)
    LinearLayout btnBookingService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_home_service)
    LinearLayout btnHomeService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_body_paint)
    LinearLayout btnBodyPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        initSetup();
        loadCategoryService();
    }

    private void initSetup() {
        btnBack.setOnClickListener(this);
        btnBookingService.setOnClickListener(this);
        btnHomeService.setOnClickListener(this);
        btnBodyPaint.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if (btnBack == view){
            finish();
        }
        if (btnBookingService == view){
            Intent intent = new Intent(ServiceActivity.this, BookingServiceActivity.class);
            startActivity(intent);
        }
        if (btnHomeService == view){
            Intent intent = new Intent(ServiceActivity.this, HomeServiceActivity.class);
            startActivity(intent);
        }
        if (btnBodyPaint == view){
            Intent intent = new Intent(ServiceActivity.this, BodyPaintActivity.class);
            startActivity(intent);
        }
    }

    public void loadCategoryService() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<CategoryService> call = apiService.getCategory(auth);
        call.enqueue(new Callback<CategoryService>() {
            @Override
            public void onResponse(@NonNull Call<CategoryService> call, @NonNull Response<CategoryService> response) {
                if (response.isSuccessful()) {
                    CategoryService data = response.body();
                    if (response.code() == 200) {
                        DataManager.getInstance().setCartId(String.valueOf(data.getData().getItems().get(0).getCategoryId()));
                        Log.e("TAG", "onResponse: " + data.getData().getItems().get(0).getCategoryId() );
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(ServiceActivity.this, error.message(), Toast.LENGTH_LONG).show();
//                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryService> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }
}