package com.codelabs.sitepat_customer.page.service.booking_service;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.BikeServiceFragment;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.BookingTimeFragment;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.LocationFragment;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.YourInformationFragment;
import com.codelabs.sitepat_customer.viewmodel.BookNow;
import com.codelabs.sitepat_customer.viewmodel.Cart;
import com.codelabs.sitepat_customer.viewmodel.DateSelected;
import com.codelabs.sitepat_customer.viewmodel.MyMotocycleSelected;
import com.codelabs.sitepat_customer.viewmodel.NextBS1;
import com.codelabs.sitepat_customer.viewmodel.NextBS2;
import com.codelabs.sitepat_customer.viewmodel.NextBS3;
import com.codelabs.sitepat_customer.viewmodel.OutletSelected;
import com.codelabs.sitepat_customer.viewmodel.Previous2;
import com.codelabs.sitepat_customer.viewmodel.Previous3;
import com.codelabs.sitepat_customer.viewmodel.Previous4;
import com.codelabs.sitepat_customer.viewmodel.TimeSelected;
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

public class BookingServiceActivity extends AppCompatActivity implements View.OnClickListener {

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
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.btn_next)
//    TextView btnNext;
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.btn_previous)
//    TextView btnPrevious;
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.btn_book_now)
//    TextView btnBookNow;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private double valueLatitude, valueLongitude;

    Integer page = 0;
    private String siteId = "";
    private String petId = "";
    private int serviceType = 1;
    private int cartServiceId = 0;
    private String complainUser = "";
    private String orderDate = "";
    private String time = "";
    private String email = "";
    private String promoCode = "";

    private List<TypeService.ItemsEntity> selectedTypeService = new ArrayList<>();
    private HashMap<String, RequestBody> list = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_service);
        ButterKnife.bind(this);
        initSetup();
        initView();
        changePage();
    }

//        if (page =)

//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (page == 0) {
//                    if (siteId != null) {
//                        if (!siteId.equals("")) {
//                            page += 1;
//                            changePage();
//                            DataManager.getInstance().setSiteId(siteId);
//                        } else {
//                            Toast.makeText(v.getContext(), "No outlet selected", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//
//                if (page == 1){
//                    if (petId != null) {
//                        if (petId.equals("")) {
//                            Toast.makeText(v.getContext(), "No Motocycle selected", Toast.LENGTH_LONG).show();
////                            DataManager.getInstance().setSiteId(siteId);
//                        } else {
//                            page += 1;
//                            changePage();
//                        }
//                    }
//                }
//
//                if (page == 2) {
//
//                        if (etDate.equals("")) {
//                            Toast.makeText(v.getContext(), "Date cannot be empty", Toast.LENGTH_LONG).show();
//                        }
//                        if (etTime.equals("")) {
//                            Toast.makeText(v.getContext(), "Time cannot be empty", Toast.LENGTH_LONG).show();
//
//                        }
//                        else {
//                            page += 1;
//                            changePage();
////                            DataManager.getInstance().setSiteId(siteId);
//                        }
//                }
//            }
//        });
//        btnPrevious.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page -= 1;
//                changePage();
//
//                if (page == 2) {
//                    indicator4.setBackgroundResource(R.color.grey_200);
//                }
//                if (page == 1) {
//                    indicator3.setBackgroundResource(R.color.grey_200);
//                    petId = "";
//                }
//                if (page == 0) {
//                    indicator2.setBackgroundResource(R.color.grey_200);
//                    siteId = "";
//                }
//
//            }
//        });
//
//
//        btnBookNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(BookingServiceActivity.this, ShopActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//
//    }
//
    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
//
    private void initSetup() {
        ivBack.setOnClickListener(this);

//        if (ContextCompat.checkSelfPermission(
//                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                    BookingServiceActivity.this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_CODE_LOCATION_PERMISSION
//            );
//        } else {
//            getCurrentLocation();
//        }
//
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
                petId = "";
                return;
            }
            if (page == 0) {
                indicator2.setBackgroundResource(R.color.grey_200);
                siteId = "";
                return;
            }
//            else {
//                DataManager.getInstance().setPositionLocation(-1);
//                DataManager.getInstance().setClose("");
//                DataManager.getInstance().setPositionMotocycle(-1);
//                finish();
//            }
            DataManager.getInstance().setPositionLocation(-1);
            DataManager.getInstance().setClose("");
            DataManager.getInstance().setPositionMotocycle(-1);
            DataManager.getInstance().setDate("");
            DataManager.getInstance().setTime("");
            finish();
        }
    }


    public void changePage() {
//        FragmentManager fragment = getSupportFragmentManager();
        if (page == 0) {
            title.setText(R.string.booking_service);
            headerMenu.setText(R.string.location);

            indicator1.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new LocationFragment()).commit();
//            btnNext.setVisibility(View.VISIBLE);
//            btnPrevious.setVisibility(View.GONE);
//            btnBookNow.setVisibility(View.GONE);


        }
        if (page == 1) {
            title.setText(R.string.booking_service);
            headerMenu.setText(R.string.bikeservice);

            indicator2.setBackgroundResource(R.color.red_text);
            BikeServiceFragment bikeFragment = new BikeServiceFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, bikeFragment).commit();
            bikeFragment.onNext = new BikeServiceFragment.OnNext() {
                @Override
                public void onNext(String complain, List<TypeService.ItemsEntity> listService) {
                    complainUser = complain ;
                    selectedTypeService = listService;
                }
            };
//            bikeFragment.isAdded();
            Log.e("TAG", "changePage: " + complainUser);
            bikeFragment.setData(complainUser, selectedTypeService);

//            btnNext.setVisibility(View.VISIBLE);
//            btnPrevious.setVisibility(View.VISIBLE);
//            btnBookNow.setVisibility(View.GONE);


        }
        if (page == 2) {
            title.setText(R.string.booking_service);
            headerMenu.setText(R.string.booking_time);

            indicator3.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new BookingTimeFragment()).commit();
//            btnNext.setVisibility(View.VISIBLE);
//            btnPrevious.setVisibility(View.VISIBLE);
//            btnBookNow.setVisibility(View.GONE);

        }
        if (page == 3) {
            title.setText(R.string.booking_service);
            headerMenu.setText(R.string.your_information);

            indicator4.setBackgroundResource(R.color.red_text);
            YourInformationFragment informationFragment = new YourInformationFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, informationFragment).commit();
            informationFragment.bookNow = new YourInformationFragment.BookNow() {
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
//
//    @SuppressLint("MissingPermission")
//    private void getCurrentLocation() {
//        LocationRequest locationRequest = new LocationRequest();
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(3000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        LocationServices.getFusedLocationProviderClient(BookingServiceActivity.this)
//                .requestLocationUpdates(locationRequest, new LocationCallback() {
//
//                    @Override
//                    public void onLocationResult(LocationResult locationResult) {
//                        super.onLocationResult(locationResult);
//                        LocationServices.getFusedLocationProviderClient(BookingServiceActivity.this)
//                                .removeLocationUpdates(this);
//
//                        if (locationResult != null && locationResult.getLocations().size() > 0) {
//                            int lastLocationIndex = locationResult.getLocations().size() - 1;
//                            double latitude = locationResult.getLocations().get(lastLocationIndex).getLatitude();
//                            double longitude = locationResult.getLocations().get(lastLocationIndex).getLongitude();
//
//                            DataManager.getInstance().setLatitudeOutlet(String.valueOf(latitude));
//                            DataManager.getInstance().setLongitudeOutlet(String.valueOf(longitude));
////                            valueLatitude = latitude;
////                            valueLongitude = longitude;
//                        }
//                    }
//                }, Looper.getMainLooper());
//    }

    private void eventBookNow(){
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        email = DataManager.getInstance().getEmail();
        orderDate = DataManager.getInstance().getDate();
        time = DataManager.getInstance().getTime();

        for (int i = 0; i < selectedTypeService.size(); i++) {
            list.put("service_id["+i+"]", Utils.INSTANCE.createRequestBody(String.valueOf(selectedTypeService.get(i).getMedicalId())));
            list.put("service_name["+i+"]", Utils.INSTANCE.createRequestBody(selectedTypeService.get(i).getMedicalName()));
            list.put("service_price["+i+"]", Utils.INSTANCE.createRequestBody(String.valueOf(selectedTypeService.get(i).getRetailPrice())));
            list.put("sku["+i+"]", Utils.INSTANCE.createRequestBody(selectedTypeService.get(i).getSku()));
            list.put("tax_rate["+i+"]", Utils.INSTANCE.createRequestBody(selectedTypeService.get(i).getTaxRate()));
        }

//        HashMap param = new HashMap<String, RequestBody>();
        list.put("pet_id", Utils.INSTANCE.createRequestBody(petId));
        list.put("site_id", Utils.INSTANCE.createRequestBody(siteId));
        list.put("service_type", Utils.INSTANCE.createRequestBody(String.valueOf(serviceType)));
        list.put("cart_service_id", Utils.INSTANCE.createRequestBody(String.valueOf(cartServiceId)));
        list.put("complaint", Utils.INSTANCE.createRequestBody(complainUser));

        list.put("order_date", Utils.INSTANCE.createRequestBody(orderDate));
        list.put("time", Utils.INSTANCE.createRequestBody(time));
        list.put("email", Utils.INSTANCE.createRequestBody(email));
        list.put("promo_code", Utils.INSTANCE.createRequestBody(promoCode));

        Call<Cart> call = apiService.createCart(auth, list);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(@NonNull Call<Cart> call, @NonNull Response<Cart> response) {
                if (response.isSuccessful()) {
                    Cart data = response.body();
                    if (response.code() == 200) {
                        Toast.makeText(BookingServiceActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        DataManager.getInstance().setPositionLocation(-1);
                        DataManager.getInstance().setClose("");
                        DataManager.getInstance().setPositionMotocycle(-1);
                        DataManager.getInstance().setDate("");
                        DataManager.getInstance().setTime("");
                        finish();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(BookingServiceActivity.this, error.message(), Toast.LENGTH_SHORT).show();
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
    public void onOutletSelect(OutletSelected outletSelected) {
        siteId = outletSelected.outletSelect;
        Log.e("TAG", "onOutletSelect: " + siteId );
    }

    @Subscribe
    public void onNext1(NextBS1 nextBS1) {
        page += 1;
        changePage();
    }

    @Subscribe
    public void onNext2(NextBS2 nextBS2) {
        page += 1;
        changePage();
    }

    @Subscribe
    public void onPrevious2(Previous2 previous2) {
        page -= 1;
        changePage();

        if (page == 0) {
            indicator2.setBackgroundResource(R.color.grey_200);
        }
    }

    @Subscribe
    public void onNext3(NextBS3 nextBS3) {
        page += 1;
        changePage();
    }

    @Subscribe
    public void onPrevious3(Previous3 previous3) {
        page -= 1;
        changePage();

        if (page == 1) {
            indicator3.setBackgroundResource(R.color.grey_200);
        }
    }

//    @Subscribe
//    public void onBookNow(BookNow bookNow) {
//        finish();
//        Toast.makeText(this, "Succes create data", Toast.LENGTH_LONG).show();
//        DataManager.getInstance().setPositionLocation(-1);
//        DataManager.getInstance().setClose("");
//        DataManager.getInstance().setPositionMotocycle(-1);
//        DataManager.getInstance().setDate("");
//        DataManager.getInstance().setTime("");
////        page += 1;
////        changePage();
//    }

    @Subscribe
    public void onPrevious4(Previous4 previous4) {
        page -= 1;
        changePage();

        if (page == 2) {
            indicator4.setBackgroundResource(R.color.grey_200);
        }
    }

    @Subscribe
    public void onMotocycleSelect(MyMotocycleSelected myMotocycleSelected) {
        petId = myMotocycleSelected.motocycleSelect;
    }

//    @Subscribe
//    public void onDateSelect(DateSelected dateSelected) {
//        orderDate = dateSelected.dataSelect;
//    }
//
//    @Subscribe
//    public void onTimeSelect(TimeSelected timeSelected) {
//        time = timeSelected.timeSelect;
//    }

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