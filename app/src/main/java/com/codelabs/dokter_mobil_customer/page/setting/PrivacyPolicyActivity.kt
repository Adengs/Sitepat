package com.codelabs.dokter_mobil_customer.page.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.dokter_mobil_customer.R
import kotlinx.android.synthetic.main.activity_term_condition.*
import kotlinx.android.synthetic.main.toolbar_back.*

class PrivacyPolicyActivity : AppCompatActivity() {
    lateinit var adapter : AdapterTerm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        initview()
    }

    private fun initview() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.privacy_policy)
        val data = listOf("1. Lorem Ipsum","2. Sit dolor ","3. Amet sit","4. Lorem")
        adapter = AdapterTerm(this,data)

        rv_data.layoutManager = LinearLayoutManager(this)
        rv_data.adapter = adapter
    }
}