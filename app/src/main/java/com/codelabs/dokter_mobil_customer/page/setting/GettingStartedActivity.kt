package com.codelabs.dokter_mobil_customer.page.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import kotlinx.android.synthetic.main.activity_getting_started.*
import kotlinx.android.synthetic.main.toolbar_back.*

class GettingStartedActivity : BaseActivity() {
    lateinit var adapter : AdapterGettingStarted
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getting_started)
        initview()
    }

    private fun initview() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.getting_started)
        val data = listOf("Login","Register","Forgot Password","Reserve Outlet", "Lorem Ipsum")
        adapter = AdapterGettingStarted(this,data)

        rv_data.layoutManager = LinearLayoutManager(this)
        rv_data.adapter = adapter
    }
}