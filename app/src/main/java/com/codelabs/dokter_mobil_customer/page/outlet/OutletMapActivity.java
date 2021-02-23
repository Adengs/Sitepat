package com.codelabs.dokter_mobil_customer.page.outlet;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutletMapActivity extends BaseActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_skip)
    AppCompatTextView tvSkip;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next)
    AppCompatButton btnNext;



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

    }

    private void initSetup() {

    }

    private void fetchData() {

    }


}