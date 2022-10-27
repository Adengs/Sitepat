package com.codelabs.sitepat_customer.page.service.body_paint;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.page.service.body_paint.fragment.BikeServiceFragmentBody;
import com.codelabs.sitepat_customer.page.service.body_paint.fragment.OutletTimeFragment;
import com.codelabs.sitepat_customer.page.service.body_paint.fragment.YourInformationFragmentBody;
import com.codelabs.sitepat_customer.page.shop.ShopActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BodyPaintActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_body_paint);
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
                Intent intent = new Intent(BodyPaintActivity.this, ShopActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initSetup() {
        ivBack.setOnClickListener(this);

//        btnNext.setOnClickListener(this);
//        btnPrevious.setOnClickListener(this);
//        btnBookNow.setOnClickListener(this);
//        OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
//            @Override
//            public void onPage(Integer page) {
//
//            }
//        };
//        onPageChangeListener.onPage();
    }

    @Override
    public void onClick(View view){
        if (ivBack == view) {
            finish();
        }
//        if (btnNext == view) {
//        }
//        if (btnPrevious == view) {
//            finish();
//        }
//        if (btnBookNow == view) {
//            finish();
//        }
    }

//    interface OnPageChangeListener{
//        public void onPage(Integer page);
//    }

    public void changePage(){
//        FragmentManager fragment = getSupportFragmentManager();
        if (page == 0){
            title.setText(R.string.body);
            headerMenu.setText(R.string.bikeservice);

            indicator1.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new BikeServiceFragmentBody()).commit();
            btnNext.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.GONE);
            btnBookNow.setVisibility(View.GONE);


        }
        if (page == 1){
            title.setText(R.string.body);
            headerMenu.setText(R.string.outlet_time);

            indicator2.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new OutletTimeFragment()).commit();
            btnNext.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.VISIBLE);
            btnBookNow.setVisibility(View.GONE);


        }
        if (page == 2){
            title.setText(R.string.body);
            headerMenu.setText(R.string.your_information);

            indicator3.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new YourInformationFragmentBody()).commit();
            btnNext.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.VISIBLE);
            btnBookNow.setVisibility(View.VISIBLE);

        }
    }
}