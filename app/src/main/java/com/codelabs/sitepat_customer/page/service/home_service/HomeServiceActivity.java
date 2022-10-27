package com.codelabs.sitepat_customer.page.service.home_service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.page.service.home_service.fragment.BikeServiceFragmentHome;
import com.codelabs.sitepat_customer.page.service.home_service.fragment.BookingTimeFragmentHome;
import com.codelabs.sitepat_customer.page.service.home_service.fragment.LocationFragmentHome;
import com.codelabs.sitepat_customer.page.service.home_service.fragment.YourInformationFragmentHome;
import com.codelabs.sitepat_customer.page.shop.ShopActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next)
    TextView btnNext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_previous)
    TextView btnPrevious;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_book_now)
    TextView btnBookNow;

    Integer page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_service);
        ButterKnife.bind(this);
        initSetup();
        changePage();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                changePage();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                changePage();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page -= 1;
                changePage();

                if (page == 2){
                    indicator4.setBackgroundResource(R.color.grey_200);
                }
                if (page == 1){
                    indicator3.setBackgroundResource(R.color.grey_200);
                }
                if (page == 0){
                    indicator2.setBackgroundResource(R.color.grey_200);
                }

            }
        });


        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeServiceActivity.this, ShopActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initSetup() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }
    }

    public void changePage(){
        if (page == 0){
            title.setText(R.string.home_service);
            headerMenu.setText(R.string.location);

            indicator1.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new LocationFragmentHome()).commit();
            btnNext.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.GONE);
            btnBookNow.setVisibility(View.GONE);


        }
        if (page == 1){
            title.setText(R.string.home_service);
            headerMenu.setText(R.string.bikeservice);

            indicator2.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new BikeServiceFragmentHome()).commit();
            btnNext.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.VISIBLE);
            btnBookNow.setVisibility(View.GONE);


        }
        if (page == 2){
            title.setText(R.string.home_service);
            headerMenu.setText(R.string.booking_time);

            indicator3.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new BookingTimeFragmentHome()).commit();
            btnNext.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.VISIBLE);
            btnBookNow.setVisibility(View.GONE);

        }
        if (page == 3){
            title.setText(R.string.home_service);
            headerMenu.setText(R.string.your_information);

            indicator4.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new YourInformationFragmentHome()).commit();
            btnNext.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.VISIBLE);
            btnBookNow.setVisibility(View.VISIBLE);


        }
    }
}