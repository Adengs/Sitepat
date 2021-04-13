package com.codelabs.dokter_mobil_customer.page.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codelabs.dokter_mobil_customer.R
import kotlinx.android.synthetic.main.activity_detail_card.*

class DetailCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_card)
        iv_close_card.setOnClickListener { finish() }
    }
}