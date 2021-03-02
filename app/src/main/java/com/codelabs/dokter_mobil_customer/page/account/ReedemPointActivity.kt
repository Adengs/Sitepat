package com.codelabs.dokter_mobil_customer.page.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codelabs.dokter_mobil_customer.R
import kotlinx.android.synthetic.main.toolbar_back.*

class ReedemPointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reedem_point)

        initView()
    }

    private fun initView() {
        tv_title.text = "Reedem Point"
    }
}