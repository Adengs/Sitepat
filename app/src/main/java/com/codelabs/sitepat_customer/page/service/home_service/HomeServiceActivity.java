package com.codelabs.sitepat_customer.page.service.home_service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
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
import com.codelabs.sitepat_customer.helper.Utils;
import com.codelabs.sitepat_customer.page.service.booking_service.BookingServiceActivity;
import com.codelabs.sitepat_customer.page.service.home_service.fragment.BikeServiceFragmentHome;
import com.codelabs.sitepat_customer.page.service.home_service.fragment.BookingTimeFragmentHome;
import com.codelabs.sitepat_customer.page.service.home_service.fragment.LocationFragmentHome;
import com.codelabs.sitepat_customer.page.service.home_service.fragment.YourInformationFragmentHome;
import com.codelabs.sitepat_customer.page.shop.ShopActivity;
import com.codelabs.sitepat_customer.viewmodel.Cart;
import com.codelabs.sitepat_customer.viewmodel.MyMotocycleHomeSelected;
import com.codelabs.sitepat_customer.viewmodel.MyMotocycleSelected;
import com.codelabs.sitepat_customer.viewmodel.NextBS1;
import com.codelabs.sitepat_customer.viewmodel.NextBS2;
import com.codelabs.sitepat_customer.viewmodel.NextBS3;
import com.codelabs.sitepat_customer.viewmodel.NextHS1;
import com.codelabs.sitepat_customer.viewmodel.NextHS2;
import com.codelabs.sitepat_customer.viewmodel.NextHS3;
import com.codelabs.sitepat_customer.viewmodel.Previous2;
import com.codelabs.sitepat_customer.viewmodel.Previous3;
import com.codelabs.sitepat_customer.viewmodel.Previous4;
import com.codelabs.sitepat_customer.viewmodel.PreviousHS2;
import com.codelabs.sitepat_customer.viewmodel.PreviousHS3;
import com.codelabs.sitepat_customer.viewmodel.PreviousHS4;
import com.codelabs.sitepat_customer.viewmodel.TypeService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeServiceActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_title)
    TextView title;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.header_menu)
    TextView headerMenu;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator1)
    View indicator1;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator2)
    View indicator2;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator3)
    View indicator3;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator4)
    View indicator4;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;

    Integer page = 0;
    private String siteId = "";
    private String petId = "";
    private int serviceType = 1;
    private int cartServiceId = 0;
    private String complainUser = "";
    private String address = "";
    private String note = "";
    private String orderDate = "";
    private String time = "";
    private String email = "";
    private String promoCode = "";
    private String latitude = "";
    private String longitude = "";

    private List<TypeService.ItemsEntity> selectedTypeService = new ArrayList<>();
    private HashMap<String, RequestBody> list = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_service);
        ButterKnife.bind(this);
        initView();
        initSetup();
        changePage();

//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page += 1;
//                changePage();
//            }
//        });
//
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page += 1;
//                changePage();
//            }
//        });
//        btnPrevious.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page -= 1;
//                changePage();
//
//                if (page == 2){
//                    indicator4.setBackgroundResource(R.color.grey_200);
//                }
//                if (page == 1){
//                    indicator3.setBackgroundResource(R.color.grey_200);
//                }
//                if (page == 0){
//                    indicator2.setBackgroundResource(R.color.grey_200);
//                }
//
//            }
//        });
//
//
//        btnBookNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeServiceActivity.this, ShopActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void initSetup() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (ivBack == view) {
//            finish();

            page -= 1;
            changePage();

            if (page == 2) {
                indicator4.setBackgroundResource(R.color.grey_200);
                return;
            }
            if (page == 1) {
                indicator3.setBackgroundResource(R.color.grey_200);
                return;
            }
            if (page == 0) {
                indicator2.setBackgroundResource(R.color.grey_200);
                return;
            }

            DataManager.getInstance().setPositionLocation(-1);
            DataManager.getInstance().setClose("");
            DataManager.getInstance().setPositionMotocycle(-1);
            DataManager.getInstance().setDate("");
            DataManager.getInstance().setTime("");
            DataManager.getInstance().setAddress("");
            DataManager.getInstance().setNote("");
            DataManager.getInstance().setLat("");
            DataManager.getInstance().setLong("");
            finish();
        }
    }

    public void changePage(){
        if (page == 0){
            title.setText(R.string.home_service);
            headerMenu.setText(R.string.location);

            indicator1.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new LocationFragmentHome()).commit();
//            btnNext.setVisibility(View.VISIBLE);
//            btnPrevious.setVisibility(View.GONE);
//            btnBookNow.setVisibility(View.GONE);


        }
        if (page == 1){
            title.setText(R.string.home_service);
            headerMenu.setText(R.string.bikeservice);

            indicator2.setBackgroundResource(R.color.red_text);
            BikeServiceFragmentHome bikeServiceFragmentHome = new BikeServiceFragmentHome();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, bikeServiceFragmentHome).commit();
            bikeServiceFragmentHome.onNext = new BikeServiceFragmentHome.OnNext() {
                @Override
                public void onNext(String complain, List<TypeService.ItemsEntity> listService) {
                    complainUser = complain ;
                    selectedTypeService = listService;
                }
            };

            bikeServiceFragmentHome.setData(complainUser, selectedTypeService);
//            btnNext.setVisibility(View.VISIBLE);
//            btnPrevious.setVisibility(View.VISIBLE);
//            btnBookNow.setVisibility(View.GONE);


        }
        if (page == 2){
            title.setText(R.string.home_service);
            headerMenu.setText(R.string.booking_time);

            indicator3.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new BookingTimeFragmentHome()).commit();
//            btnNext.setVisibility(View.VISIBLE);
//            btnPrevious.setVisibility(View.VISIBLE);
//            btnBookNow.setVisibility(View.GONE);

        }
        if (page == 3){
            title.setText(R.string.home_service);
            headerMenu.setText(R.string.your_information);

            indicator4.setBackgroundResource(R.color.red_text);
            YourInformationFragmentHome yourInformationFragmentHome = new YourInformationFragmentHome();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, yourInformationFragmentHome).commit();
            yourInformationFragmentHome.bookNow = new YourInformationFragmentHome.BookNow() {
                @Override
                public void bookNow() {
                    eventBookNow();
                }
            };
//            btnNext.setVisibility(View.GONE);
//            btnPrevious.setVisibility(View.VISIBLE);
//            btnBookNow.setVisibility(View.VISIBLE);


        }
    }

    private void eventBookNow(){
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        email = DataManager.getInstance().getEmail();
        address = DataManager.getInstance().getAddress();
        note = DataManager.getInstance().getNote();
        orderDate = DataManager.getInstance().getDate();
        time = DataManager.getInstance().getTime();
        latitude = DataManager.getInstance().getLat();
        longitude = DataManager.getInstance().getLong();

        for (int i = 0; i < selectedTypeService.size(); i++) {
            list.put("service_id["+i+"]", Utils.INSTANCE.createRequestBody(String.valueOf(selectedTypeService.get(i).getMedicalId())));
            list.put("service_name["+i+"]", Utils.INSTANCE.createRequestBody(selectedTypeService.get(i).getMedicalName()));
            list.put("service_price["+i+"]", Utils.INSTANCE.createRequestBody(String.valueOf(selectedTypeService.get(i).getRetailPrice())));
            list.put("sku["+i+"]", Utils.INSTANCE.createRequestBody(selectedTypeService.get(i).getSku()));
            list.put("tax_rate["+i+"]", Utils.INSTANCE.createRequestBody(selectedTypeService.get(i).getTaxRate()));
        }

//        HashMap param = new HashMap<String, RequestBody>();
        list.put("pet_id", Utils.INSTANCE.createRequestBody(petId));
//        list.put("site_id", Utils.INSTANCE.createRequestBody(siteId));
//        list.put("service_type", Utils.INSTANCE.createRequestBody(String.valueOf(serviceType)));
        list.put("cart_service_id", Utils.INSTANCE.createRequestBody(String.valueOf(cartServiceId)));
        list.put("latitude", Utils.INSTANCE.createRequestBody(String.valueOf(latitude)));
        list.put("longitude", Utils.INSTANCE.createRequestBody(String.valueOf(longitude)));
        list.put("address", Utils.INSTANCE.createRequestBody(String.valueOf(address)));
        list.put("note", Utils.INSTANCE.createRequestBody(String.valueOf(note)));
        list.put("complaint", Utils.INSTANCE.createRequestBody(complainUser));

        list.put("order_date", Utils.INSTANCE.createRequestBody(orderDate));
        list.put("time", Utils.INSTANCE.createRequestBody(time));
        list.put("email", Utils.INSTANCE.createRequestBody(email));
        list.put("promo_code", Utils.INSTANCE.createRequestBody(promoCode));

        Call<Cart> call = apiService.createCartHome(auth, list);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(@NonNull Call<Cart> call, @NonNull Response<Cart> response) {
                if (response.isSuccessful()) {
                    Cart data = response.body();
                    if (response.code() == 200) {
                        Toast.makeText(HomeServiceActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        DataManager.getInstance().setPositionLocation(-1);
                        DataManager.getInstance().setClose("");
                        DataManager.getInstance().setPositionMotocycle(-1);
                        DataManager.getInstance().setDate("");
                        DataManager.getInstance().setTime("");
                        DataManager.getInstance().setAddress("");
                        DataManager.getInstance().setNote("");
                        DataManager.getInstance().setLat("");
                        DataManager.getInstance().setLong("");
                        finish();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(HomeServiceActivity.this, error.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cart> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }

    @Subscribe
    public void onNext1(NextHS1 nextHS1) {
        page += 1;
        changePage();
    }

    @Subscribe
    public void onNext2(NextHS2 nextHS2) {
        page += 1;
        changePage();
    }

    @Subscribe
    public void onPrevious2(PreviousHS2 previous2) {
        page -= 1;
        changePage();

        if (page == 0) {
            indicator2.setBackgroundResource(R.color.grey_200);
        }
    }

    @Subscribe
    public void onNext3(NextHS3 nextHS3) {
        page += 1;
        changePage();
    }

    @Subscribe
    public void onPrevious3(PreviousHS3 previous3) {
        page -= 1;
        changePage();

        if (page == 1) {
            indicator3.setBackgroundResource(R.color.grey_200);
        }
    }

    @Subscribe
    public void onPrevious4(PreviousHS4 previous4) {
        page -= 1;
        changePage();

        if (page == 2) {
            indicator4.setBackgroundResource(R.color.grey_200);
        }
    }

    @Subscribe
    public void onMotocycleSelect(MyMotocycleHomeSelected myMotocycleSelected) {
        petId = myMotocycleSelected.motocycleSelect;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}