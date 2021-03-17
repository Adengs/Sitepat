package com.codelabs.dokter_mobil_customer.page.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.adapter.PromoBannerAdapter;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.helper.Utils;
import com.codelabs.dokter_mobil_customer.page.Notif.NotificationActivity;
import com.codelabs.dokter_mobil_customer.page.about.AboutUsActivity;
import com.codelabs.dokter_mobil_customer.page.account.MyAccountActivity;
import com.codelabs.dokter_mobil_customer.page.car_monitoring.CarMonitoringActivity;
import com.codelabs.dokter_mobil_customer.page.outlet.OutletMapActivity;
import com.codelabs.dokter_mobil_customer.page.promo.PromoActivity;
import com.codelabs.dokter_mobil_customer.page.setting.SettingActivity;
import com.codelabs.dokter_mobil_customer.page.support.SupportActivity;
import com.codelabs.dokter_mobil_customer.viewmodel.Profile;
import com.codelabs.dokter_mobil_customer.viewmodel.Promo;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.samlss.lighter.Lighter;
import me.samlss.lighter.interfaces.OnLighterListener;
import me.samlss.lighter.parameter.Direction;
import me.samlss.lighter.parameter.LighterParameter;
import me.samlss.lighter.parameter.MarginOffset;
import me.samlss.lighter.shape.RectShape;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_username)
    AppCompatTextView tvUsername;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_outlet)
    CardView containerOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_my_account)
    CardView containerMyAccount;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_car_monitoring)
    CardView containerCarMonitoring;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_support)
    CardView containerSupport;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_about)
    CardView containerAbout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_setting)
    CardView containerSetting;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewpager_promo)
    ViewPager viewPagerPromo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tab_dots)
    TabLayout tabDots;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_see_all)
    AppCompatTextView tvSeeAllBanner;

    PromoBannerAdapter promoAdapter;
    private int currentCount = 0;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        functionCheckLocationPermission();
        initView();
        initSetup();
        fetchData();
        if (getIntent().getBooleanExtra("IS_HIGHLIGHT_OUTLET",false)){
            highlightOutlet();
        }
    }

    private void highlightOutlet(){
        Lighter.with(this)
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.container_outlet)
                        .setTipView(Utils.INSTANCE.createCommonTipViewTop(this, "Pada halaman utama, pilih menu Outlet"))
                        .setLighterShape(new RectShape(5, 5, 30))
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewRelativeOffset(new MarginOffset(150, 0, 30, 0))
                        .build())
                .setOnLighterListener(new OnLighterListener() {
                    @Override
                    public void onShow(int index) {

                    }

                    @Override
                    public void onDismiss() {
                        Intent intent = new Intent(MainActivity.this, OutletMapActivity.class);
                        intent.putExtra("IS_HIGHLIGHT_OUTLET",true);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

    private void initView() {
        promoAdapter = new PromoBannerAdapter(getApplicationContext());
        viewPagerPromo.setAdapter(promoAdapter);
        tabDots.setupWithViewPager(viewPagerPromo, true);
        autoPlay(viewPagerPromo);


    }

    private void initSetup() {
        containerOutlet.setOnClickListener(this);
        containerMyAccount.setOnClickListener(this);
        containerCarMonitoring.setOnClickListener(this);
        containerSupport.setOnClickListener(this);
        containerAbout.setOnClickListener(this);
        containerSetting.setOnClickListener(this);
        tvSeeAllBanner.setOnClickListener(this);

    }

    private void fetchData() {
        loadProfile();
        loadPromoBanner();
    }


    private void functionCheckLocationPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    functionCheckLocationPermission();
//                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @OnClick(R.id.iv_notification)
    void openNotif(){
        startActivity(new Intent(this, NotificationActivity.class));
    }

    public void loadProfile() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Profile> call = apiService.getProfile(auth);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                if (response.isSuccessful()) {
                    Profile data = response.body();
                    if (response.code() == 200) {
                        tvUsername.setText(data.getDataProfile().getCustomerName());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
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

    private void autoPlay(final ViewPager viewPager) {
        viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (promoAdapter != null && promoAdapter.getCount() > 0) {
                        int position = currentCount % promoAdapter.getCount();
                        currentCount++;
                        viewPagerPromo.setCurrentItem(position);
                        autoPlay(viewPagerPromo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
    }


    @Override
    public void onClick(View view) {
        if (containerSetting == view) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }

        if (containerAbout == view) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        }

        if (containerOutlet == view) {
            Intent intent = new Intent(MainActivity.this, OutletMapActivity.class);
            startActivity(intent);
        }

        if (containerSupport == view) {

            Intent intent = new Intent(MainActivity.this, SupportActivity.class);
            startActivity(intent);
        }

        if (containerCarMonitoring == view) {
            Intent intent = new Intent(MainActivity.this, CarMonitoringActivity.class);
            startActivity(intent);
        }

        if (containerMyAccount == view) {
            Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
            startActivity(intent);
        }

        if (tvSeeAllBanner == view) {
            Intent intent = new Intent(MainActivity.this, PromoActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }
}