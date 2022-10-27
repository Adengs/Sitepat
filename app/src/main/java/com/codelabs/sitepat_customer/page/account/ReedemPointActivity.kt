package com.codelabs.sitepat_customer.page.account

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.codelabs.sitepat_customer.R
import kotlinx.android.synthetic.main.toolbar_back.*

class ReedemPointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reedem_point)

        initView()
    }

    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }



        iv_back.setOnClickListener { finish() }
        tv_title.text = "Reedem Point"
    }
}