package com.codelabs.sitepat_customer.page.order;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.codelabs.sitepat_customer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrders extends AppCompatActivity implements View.OnClickListener  {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_ongoing)
    TextView onGoing;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_tab_ongoing)
    LinearLayout layOnGoing;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_finished)
    TextView finished;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_tab_finished)
    LinearLayout layTabFinished;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator_tab1)
    View tab1;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator_tab2)
    View tab2;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tab_all)
    TextView tabAll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tab_purchase)
    TextView tabPurchase;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tab_service)
    TextView tabService;

    Integer page = 0;
    Integer tab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ButterKnife.bind(this);
        initSetup();
        changePage();
        changeTab();

//        layOnGoing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page = 0;
//                changePage();
//                Log.e("cek", page.toString());
//            }
//        });
//
//        onGoing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page = 0;
//                changePage();
//                Log.e("cek", page.toString());
//            }
//        });
//
//        layTabFinished.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page = 1;
//                changePage();
//                Log.e("cek2", page.toString());
//            }
//        });
//
//        finished.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                page = 1;
//                changePage();
//                Log.e("cek2", page.toString());
//            }
//        });

    }

    private void initSetup() {
        ivBack.setOnClickListener(this);
        onGoing.setOnClickListener(this);
        layOnGoing.setOnClickListener(this);
        finished.setOnClickListener(this);
        layTabFinished.setOnClickListener(this);
        tabAll.setOnClickListener(this);
        tabPurchase.setOnClickListener(this);
        tabService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }
        if (onGoing == view) {
            page = 0;
            changePage();
        }
        if (layOnGoing == view) {
            page = 0;
            changePage();
        }
        if (finished == view) {
            page = 1;
            changePage();
        }
        if (layTabFinished == view) {
            page = 1;
            changePage();
        }
        if (tabAll == view) {
            tab = 0;
            changeTab();
        }
        if (tabPurchase == view) {
            tab = 1;
            changeTab();
        }
        if (tabService == view) {
            tab = 2;
            changeTab();
        }
    }

    public void changePage(){
        if (page == 0){
            onGoing.setTextColor(getResources().getColor(R.color.white));
            tab1.setBackgroundResource(R.color.white);
            finished.setTextColor(getResources().getColor(R.color.grey_500));
            tab2.setBackgroundResource(R.color.black);
            tabAll.setBackgroundResource(R.drawable.background_tab_orange);
            tabAll.setTextColor(getResources().getColor(R.color.white));
            tabPurchase.setBackgroundResource(R.drawable.background_tab);
            tabPurchase.setTextColor(getResources().getColor(R.color.black));
            tabService.setBackgroundResource(R.drawable.background_tab);
            tabService.setTextColor(getResources().getColor(R.color.black));
        }
        if (page == 1){
            finished.setTextColor(getResources().getColor(R.color.white));
            tab2.setBackgroundResource(R.color.white);
            onGoing.setTextColor(getResources().getColor(R.color.grey_500));
            tab1.setBackgroundResource(R.color.black);
            tabAll.setBackgroundResource(R.drawable.background_tab_orange);
            tabAll.setTextColor(getResources().getColor(R.color.white));
            tabPurchase.setBackgroundResource(R.drawable.background_tab);
            tabPurchase.setTextColor(getResources().getColor(R.color.black));
            tabService.setBackgroundResource(R.drawable.background_tab);
            tabService.setTextColor(getResources().getColor(R.color.black));
        }
    }

    public void changeTab(){
        if (tab == 0){
            tabAll.setBackgroundResource(R.drawable.background_tab_orange);
            tabAll.setTextColor(getResources().getColor(R.color.white));
            tabPurchase.setBackgroundResource(R.drawable.background_tab);
            tabPurchase.setTextColor(getResources().getColor(R.color.black));
            tabService.setBackgroundResource(R.drawable.background_tab);
            tabService.setTextColor(getResources().getColor(R.color.black));
        }
        if (tab == 1){
            tabAll.setBackgroundResource(R.drawable.background_tab);
            tabAll.setTextColor(getResources().getColor(R.color.black));
            tabPurchase.setBackgroundResource(R.drawable.background_tab_orange);
            tabPurchase.setTextColor(getResources().getColor(R.color.white));
            tabService.setBackgroundResource(R.drawable.background_tab);
            tabService.setTextColor(getResources().getColor(R.color.black));
        }
        if (tab == 2){
            tabAll.setBackgroundResource(R.drawable.background_tab);
            tabAll.setTextColor(getResources().getColor(R.color.black));
            tabPurchase.setBackgroundResource(R.drawable.background_tab);
            tabPurchase.setTextColor(getResources().getColor(R.color.black));
            tabService.setBackgroundResource(R.drawable.background_tab_orange);
            tabService.setTextColor(getResources().getColor(R.color.white));
        }
    }
}