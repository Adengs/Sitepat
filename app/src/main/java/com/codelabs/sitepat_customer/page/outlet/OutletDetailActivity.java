package com.codelabs.sitepat_customer.page.outlet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.helper.Utils;
import com.codelabs.sitepat_customer.viewmodel.OutletDetail;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.samlss.lighter.Lighter;
import me.samlss.lighter.interfaces.OnLighterListener;
import me.samlss.lighter.parameter.Direction;
import me.samlss.lighter.parameter.LighterParameter;
import me.samlss.lighter.parameter.MarginOffset;
import me.samlss.lighter.shape.RectShape;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutletDetailActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_outlet)
    AppCompatImageView ivOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_outlet_name)
    AppCompatTextView tvOutletName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_status_outlet)
    AppCompatTextView tvStatusOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_distance)
    AppCompatTextView tvDistance;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_alamat_outlet)
    AppCompatTextView tvAlamatOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_reservation)
    FrameLayout btnReservation;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_title)
    AppCompatTextView tvTitle;
    @BindView(R.id.tv_status_outlet_close)
    AppCompatTextView tvStatusOutletClose;

    int idOutlet = -1;
    String phoneNumber = "";
    private double valueLatitude, valueLongitude;
    private double latitudePhone, longitudePhone;
    private OutletDetail.DataOutletDetail responseData;

    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mfusedLocationProviderClient;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private String Latitude;
    private String Longitude;
    private double lat;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_detail);
        ButterKnife.bind(this);
        getPrevData();
        initView();
        fetchData();
    }

    private void getPrevData() {
        Intent intent = getIntent();
        idOutlet = intent.getIntExtra("outlet_id", -1);
        valueLatitude = intent.getDoubleExtra("latitude", 0.0);
        valueLongitude = intent.getDoubleExtra("longitude", 0.0);
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        ivBack.setOnClickListener(this);
        tvTitle.setText(R.string.detail_outlet);
        btnReservation.setOnClickListener(this);
    }

    private void fetchData() {
        loadOutletDetail();
    }



    public void loadOutletDetail() {
        showDialogProgress("Getting data outlet detail");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        latitudePhone = Double.parseDouble(DataManager.getInstance().getLatitude());
        longitudePhone = Double.parseDouble(DataManager.getInstance().getLongitude());
        Call<OutletDetail> call = apiService.getOutletDetail(auth, idOutlet, latitudePhone, longitudePhone);
        call.enqueue(new Callback<OutletDetail>() {
            @Override
            public void onResponse(@NonNull Call<OutletDetail> call, @NonNull Response<OutletDetail> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    OutletDetail data = response.body();
                    if (response.code() == 200) {
                        assert data != null;
                        responseData = data.getDataOutletDetail();
                        phoneNumber = responseData.getSitePhone();
                        dataOutletDetail();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<OutletDetail> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    showToast(getString(R.string.toast_onfailure));
                    hideDialogProgress();
                }
            }
        });
    }



    private void dataOutletDetail() {
        if (responseData.getSiteImage().equals("")) {
            ivOutlet.setImageResource(R.drawable.background_outlet_status);
        } else {
            Picasso.get()
                    .load(responseData.getSiteImage())
                    .fit().centerCrop()
                    .into(ivOutlet);
        }

        if (responseData.getIsOpen() == 1) {
            tvStatusOutlet.setVisibility(View.VISIBLE);
            tvStatusOutletClose.setVisibility(View.GONE);
        } else {
            tvStatusOutletClose.setVisibility(View.VISIBLE);
            tvStatusOutlet.setVisibility(View.GONE);
        }
        tvOutletName.setText(responseData.getSiteName());
        tvDistance.setText(responseData.getDistance());
        tvAlamatOutlet.setText(responseData.getSiteAddress());
        if (getIntent().getBooleanExtra("IS_HIGHLIGHT_OUTLET", false)) {
            Lighter.with(OutletDetailActivity.this)
                    .addHighlight(new LighterParameter.Builder()
                            .setHighlightedViewId(R.id.btn_reservation)
                            .setTipView(Utils.INSTANCE.createCommonTipViewTop(OutletDetailActivity.this, "Klik Reservation untuk menghubungi outlet yang dipilih"))
                            .setLighterShape(new RectShape(5, 5, 30))
                            .setTipViewRelativeDirection(Direction.TOP)
                            .setTipViewRelativeOffset(new MarginOffset(0, 0, 0, 0))
                            .build())
                    .setOnLighterListener(new OnLighterListener() {
                        @Override
                        public void onShow(int index) {

                        }

                        @Override
                        public void onDismiss() {
                            finish();
                        }
                    })
                    .show();
        }

    }




    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }

        if (btnReservation == view) {
            String URL= "https://api.whatsapp.com/send?phone=" + phoneNumber;
            try {
                PackageManager packageManager = context.getPackageManager();
                packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                Intent whatsApp = new Intent(Intent.ACTION_VIEW);
                whatsApp.setData(Uri.parse(URL));
                startActivity(whatsApp);
            } catch (PackageManager.NameNotFoundException e) {
                showToast("Whatsapp is not installed in your phone.");
                e.printStackTrace();
            }
        }
    }
}