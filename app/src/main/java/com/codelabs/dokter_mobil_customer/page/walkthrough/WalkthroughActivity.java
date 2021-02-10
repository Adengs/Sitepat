package com.codelabs.dokter_mobil_customer.page.walkthrough;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.adapter.WalkthroughAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalkthroughActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_skip)
    AppCompatTextView tvSkip;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next)
    AppCompatButton btnNext;

    private WalkthroughAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        ButterKnife.bind(this);
    }
}