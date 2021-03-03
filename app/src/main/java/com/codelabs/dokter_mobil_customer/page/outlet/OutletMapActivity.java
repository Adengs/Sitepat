package com.codelabs.dokter_mobil_customer.page.outlet;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.adapter.OutletAdapter;
import com.codelabs.dokter_mobil_customer.adapter.PromoAdapter;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.viewmodel.Outlet;
import com.codelabs.dokter_mobil_customer.viewmodel.Promo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutletMapActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_title)
    AppCompatTextView tvTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_sheet)
    NestedScrollView bottomSheet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_search_outlet)
    AppCompatEditText etSearchOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_outlet)
    RecyclerView rvOutlet;

    private GoogleMap mMap;
    private BottomSheetBehavior mBottomSheetBehavior;
    OutletAdapter mAdapter;
    private String keyword = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_map);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        ivBack.setOnClickListener(this);
        tvTitle.setText(R.string.outlet);

        rvOutlet.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new OutletAdapter(this);
        mAdapter.setData(new ArrayList<>());
        rvOutlet.setAdapter(mAdapter);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(300);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void initSetup() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        functionSearch();

    }

    private void fetchData() {
        loadOutlet();
    }

    public void loadOutlet() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Outlet> call = apiService.getOutlet(auth, keyword);
        call.enqueue(new Callback<Outlet>() {
            @Override
            public void onResponse(@NonNull Call<Outlet> call,@NonNull Response<Outlet> response) {
                if (response.isSuccessful()) {
                    Outlet data = response.body();
                    if (response.code() == 200) {
                        mAdapter.setData(data.getData().getItemsOutlet());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Outlet> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    private void functionSearch() {
        etSearchOutlet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = s.toString();
                loadOutlet();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Changing marker icon
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_baloon_marker);

        LatLng yogya = new LatLng(-6.273076, 106.739045);
        mMap.addMarker(new MarkerOptions().position(yogya).icon(icon));

        CameraUpdate zoom= CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(yogya));
        mMap.animateCamera(zoom);


    }


    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }

    }
}