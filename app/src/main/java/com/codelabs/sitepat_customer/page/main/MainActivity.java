package com.codelabs.sitepat_customer.page.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.ArticleHomePageAdapter;
import com.codelabs.sitepat_customer.adapter.ArticleHomeVerticalAdapter;
import com.codelabs.sitepat_customer.adapter.ArticleHorizontalAdapter;
import com.codelabs.sitepat_customer.adapter.NewProductAdapter;
import com.codelabs.sitepat_customer.adapter.PromoBannerAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.decoration.HorizontalItemDecoration;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.helper.Utils;
import com.codelabs.sitepat_customer.page.Notif.NotificationActivity;
import com.codelabs.sitepat_customer.page.account.MyAccountActivity;
import com.codelabs.sitepat_customer.page.article.ArticleActivity;
import com.codelabs.sitepat_customer.page.order.MyOrders;
import com.codelabs.sitepat_customer.page.outlet.OutletMapActivity;
import com.codelabs.sitepat_customer.page.promo.PromoActivity;
import com.codelabs.sitepat_customer.page.service.ServiceActivity;
import com.codelabs.sitepat_customer.page.setting.SettingActivity;
import com.codelabs.sitepat_customer.page.shop.NewProductActivity;
import com.codelabs.sitepat_customer.page.shop.ShopActivity;
import com.codelabs.sitepat_customer.utils.RecentUtils;
import com.codelabs.sitepat_customer.viewmodel.Articles;
import com.codelabs.sitepat_customer.viewmodel.NewProduct;
import com.codelabs.sitepat_customer.viewmodel.Profile;
import com.codelabs.sitepat_customer.viewmodel.Promo;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

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
    @BindView(R.id.iv_outlet)
    AppCompatImageView containerOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_my_account)
    AppCompatImageView containerMyAccount;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_service)
    AppCompatImageView containerService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_shop)
    AppCompatImageView containerShop;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_my_order)
    AppCompatImageView containerMyOrders;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_setting)
    AppCompatImageView containerSetting;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewpager_promo)
    ViewPager2 viewPagerPromo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tab_dots)
    TabLayout tabDots;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_see_all)
    AppCompatTextView tvSeeAllBanner;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_article_homepage)
    RecyclerView rvArticleHomepage;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_see_more_product)
    AppCompatTextView tvSeeMoreProduct;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_see_more_articel)
    AppCompatTextView tvSeeMoreArticle;
    //    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.rv_popular_article)
//    RecyclerView rvPopularArticle;
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.rv_article_horizontal)
//    RecyclerView rvArticleHorizontal;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_product_homepage)
    RecyclerView rvProductHomepage;


    PromoBannerAdapter promoAdapter;
    ArticleHomePageAdapter articleHomePageAdapter;
    ArticleHorizontalAdapter articleHorizontalAdapter;
    ArticleHomeVerticalAdapter articleHomeVerticalAdapter;
    NewProductAdapter newProductAdapter;
    private String keyword = "";
    private String sort = "";
    private Integer limitNP = 20;
    private int currentCount = 0;
    private int category = -1;
    private int limit = -1;
    private String minPrice = "";
    private String maxPrice = "";
    private Integer active = 1;
    private String filterBrand = "";
    private String filterType = "";

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private double valueLatitude, valueLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        functionCheckLocationPermission();
        initView();
        initSetup();
        fetchData();
        if (getIntent().getBooleanExtra("IS_HIGHLIGHT_OUTLET", false)) {
            highlightOutlet();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fetchData();
    }

    private void highlightOutlet() {
        Lighter.with(this)
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.container_outlet_menu)
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
                        intent.putExtra("IS_HIGHLIGHT_OUTLET", true);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

    private void initView() {
        promoAdapter = new PromoBannerAdapter(getApplicationContext());
        viewPagerPromo.setAdapter(promoAdapter);
        viewPagerPromo.setClipToPadding(false);
        viewPagerPromo.setClipChildren(false);
        viewPagerPromo.setOffscreenPageLimit(3);
        viewPagerPromo.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

//        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//        compositePageTransformer.addTransformer(new MarginPageTransformer(0));
//        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float r = 1 - Math.abs(position);
//                page.setScaleY(0.85f + r * 0.15f);
//            }
//        });


//        viewPagerPromo.setPageTransformer(compositePageTransformer);

//        new TabLayoutMediator(tabDots, viewPagerPromo,
//                (tab, position) -> tab.setText("" + (position))
//        ).attach();
//        autoPlay(viewPagerPromo);


        new TabLayoutMediator(tabDots, viewPagerPromo,
                (tab, position) -> tab.setText(" ")
        ).attach();
        autoPlay(viewPagerPromo);


        articleHomePageAdapter = new ArticleHomePageAdapter(getApplicationContext());
        rvArticleHomepage.setAdapter(articleHomePageAdapter);
        rvArticleHomepage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        HorizontalItemDecoration itemDecoration = new HorizontalItemDecoration(RecentUtils.ConvertDpToPx(this, 10));
        rvArticleHomepage.addItemDecoration(new RecentUtils.PaddingItemDecoration(60));

        newProductAdapter = new NewProductAdapter(getApplicationContext());
        rvProductHomepage.setAdapter(newProductAdapter);
        rvProductHomepage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvProductHomepage.addItemDecoration(new RecentUtils.PaddingItemDecoration(60));

        articleHorizontalAdapter = new ArticleHorizontalAdapter(getApplicationContext());
//        rvArticleHorizontal.setAdapter(articleHorizontalAdapter);
//        rvArticleHorizontal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        HorizontalItemDecoration itemDecoration1 = new HorizontalItemDecoration(RecentUtils.ConvertDpToPx(this, 0));
//        rvArticleHorizontal.addItemDecoration(itemDecoration1);

//        rvPopularArticle.setLayoutManager(new LinearLayoutManager(this));
        articleHomeVerticalAdapter = new ArticleHomeVerticalAdapter(this);
        articleHomeVerticalAdapter.setData(new ArrayList<>());
//        rvPopularArticle.setAdapter(articleHomeVerticalAdapter);

    }

    private void initSetup() {
        containerOutlet.setOnClickListener(this);
        containerMyAccount.setOnClickListener(this);
        containerService.setOnClickListener(this);
        containerShop.setOnClickListener(this);
        containerMyOrders.setOnClickListener(this);
        containerSetting.setOnClickListener(this);
        tvSeeAllBanner.setOnClickListener(this);
        tvSeeMoreProduct.setOnClickListener(this);
        tvSeeMoreArticle.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        } else {
            getCurrentLocation();
        }
    }

    private void fetchData() {
        loadProfile();
        loadPromoBanner();
        loadDataArticle();
        loadDataDisasters();
        loadDataNews();
        loadDataArticleVertical();
        loadDataArticleDisasters();
    }


    private void functionCheckLocationPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
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

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    functionCheckLocationPermission();
//                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @OnClick(R.id.iv_notification)
    void openNotif() {
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

    public void loadDataArticle() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Articles> call = apiService.getArticles(auth, keyword, 6, 0);
        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(@NonNull Call<Articles> call, @NonNull Response<Articles> response) {
                if (response.isSuccessful()) {
                    Articles data = response.body();
                    if (response.code() == 200) {
                        articleHomePageAdapter.setData(data.getData().getItemsArticles());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Articles> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                    hideDialogProgress();
                }
            }
        });
    }

    public void loadDataDisasters() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Articles> call = apiService.getArticles(auth, keyword, 2, 0);
        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(@NonNull Call<Articles> call, @NonNull Response<Articles> response) {
                if (response.isSuccessful()) {
                    Articles data = response.body();
                    if (response.code() == 200) {
                        articleHorizontalAdapter.setData(data.getData().getItemsArticles());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Articles> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                    hideDialogProgress();
                }
            }
        });
    }

    public void loadDataArticleDisasters() {
//        showDialogProgress("Getting data article");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Articles> call = apiService.getArticles(auth, keyword, 3, 1);
        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(@NonNull Call<Articles> call, @NonNull Response<Articles> response) {
//                hideDialogProgress();
                if (response.isSuccessful()) {
                    Articles data = response.body();
                    if (response.code() == 200) {
                        articleHorizontalAdapter.setData(data.getData().getItemsArticles());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
//                    containerNoData.setVisibility(View.VISIBLE);
//                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Articles> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
//                    containerNoData.setVisibility(View.VISIBLE);
//                    tvNoData.setText(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void loadDataArticleVertical() {
//        showDialogProgress("Getting data article");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Articles> call = apiService.getArticles(auth, keyword, 2, 0);
        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(@NonNull Call<Articles> call, @NonNull Response<Articles> response) {
//                hideDialogProgress();
                if (response.isSuccessful()) {
                    Articles data = response.body();
                    if (response.code() == 200) {
                        articleHomeVerticalAdapter.setData(data.getData().getItemsArticles());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
//                    containerNoData.setVisibility(View.VISIBLE);
//                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Articles> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
//                    containerNoData.setVisibility(View.VISIBLE);
//                    tvNoData.setText(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void loadDataNews() {
        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();

        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<NewProduct> call = apiService.getNewProduct(auth,  limitNP, active, keyword, filterBrand, filterType, minPrice, maxPrice, sort, lat, lon);
        call.enqueue(new Callback<NewProduct>() {
            @Override
            public void onResponse(@NonNull Call<NewProduct> call, @NonNull Response<NewProduct> response) {
                if (response.isSuccessful()) {
                    NewProduct data = response.body();
                    if (response.code() == 200) {
                        newProductAdapter.setData(data.getData().getItems());
//                        DataManager.getInstance().setCustomerId(data.getData().getItems().get(0).getProductId());
                    }else{

                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewProduct> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }

    private void autoPlay(final ViewPager2 viewPager) {
        viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (promoAdapter != null && promoAdapter.getItemCount() > 0) {
                        int position = currentCount % promoAdapter.getItemCount();
                        currentCount++;
                        viewPagerPromo.setCurrentItem(position);
                        autoPlay(viewPagerPromo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5000);
    }


    @Override
    public void onClick(View view) {
        if (containerOutlet == view) {
            Intent intent = new Intent(MainActivity.this, OutletMapActivity.class);
            startActivity(intent);
        }

        if (containerService == view) {
            Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
            startActivity(intent);
        }

        if (containerShop == view) {
//            Toast.makeText(this, "On Develop :(", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ShopActivity.class);
            startActivity(intent);
        }

        if (containerMyOrders == view) {
//            Toast.makeText(this, "On Develop :(", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, MyOrders.class);
            startActivity(intent);
        }

        if (containerSetting == view) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
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

        if (tvSeeMoreProduct == view) {
//            Toast.makeText(this, "On Develop :(", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, NewProductActivity.class);
            startActivity(intent);

        }

        if (tvSeeMoreArticle == view) {
            Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
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

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getCurrentLocation();
//            } else {
//                Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                .removeLocationUpdates(this);

                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int lastLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(lastLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(lastLocationIndex).getLongitude();

                            DataManager.getInstance().setLatitudeOutlet(String.valueOf(latitude));
                            DataManager.getInstance().setLongitudeOutlet(String.valueOf(longitude));
//                            valueLatitude = latitude;
//                            valueLongitude = longitude;
                        }
                    }
                }, Looper.getMainLooper());
    }
}