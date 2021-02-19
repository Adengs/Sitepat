package com.codelabs.dokter_mobil_customer.page.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codelabs.dokter_mobil_customer.R
import kotlinx.android.synthetic.main.activity_setting.*

class MyAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        iv_back.setOnClickListener { finish() }
    }
}