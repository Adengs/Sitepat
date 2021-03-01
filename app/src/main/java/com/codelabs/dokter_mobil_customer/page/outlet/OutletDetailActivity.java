package com.codelabs.dokter_mobil_customer.page.outlet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.codelabs.dokter_mobil_customer.R;

import butterknife.ButterKnife;

public class OutletDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_detail);
        ButterKnife.bind(this);
    }
}