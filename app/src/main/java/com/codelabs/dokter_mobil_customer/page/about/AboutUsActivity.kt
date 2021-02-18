package com.codelabs.dokter_mobil_customer.page.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.codelabs.dokter_mobil_customer.R
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        initView()
        fetchData()
    }

    private fun initView() {
        iv_back.setOnClickListener(this)
    }

    private fun fetchData() {

    }


    override fun onClick(view: View?) {
        if (iv_back == view) {
            onBackPressed()
        }
    }
}