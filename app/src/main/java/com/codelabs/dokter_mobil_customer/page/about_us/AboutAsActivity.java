package com.codelabs.dokter_mobil_customer.page.about_us;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutAsActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_as);
        ButterKnife.bind(this);
        initSetup();
    }

    private void initSetup() {
        ivBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            onBackPressed();
        }
    }
}