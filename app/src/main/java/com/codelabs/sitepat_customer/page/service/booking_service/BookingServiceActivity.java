package com.codelabs.sitepat_customer.page.service.booking_service;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.BikeServiceFragment;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.BookingTimeFragment;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.LocationFragment;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.YourInformationFragment;
import com.codelabs.sitepat_customer.viewmodel.BookNow;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private String etDate = "";
    private String etTime = "";

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
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new BikeServiceFragment()).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new YourInformationFragment()).commit();
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

    @Subscribe
    public void onOutletSelect(OutletSelected outletSelected) {
        siteId = outletSelected.outletSelect;
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

    @Subscribe
    public void onBookNow(BookNow bookNow) {
        finish();
        Toast.makeText(this, "Succes create data", Toast.LENGTH_LONG).show();
        DataManager.getInstance().setPositionLocation(-1);
        DataManager.getInstance().setClose("");
        DataManager.getInstance().setPositionMotocycle(-1);
        DataManager.getInstance().setDate("");
        DataManager.getInstance().setTime("");
//        page += 1;
//        changePage();
    }

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

    @Subscribe
    public void onDateSelect(DateSelected dateSelected) {
        etDate = dateSelected.dataSelect;
    }

    @Subscribe
    public void onTimeSelect(TimeSelected timeSelected) {
        etTime = timeSelected.timeSelect;
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