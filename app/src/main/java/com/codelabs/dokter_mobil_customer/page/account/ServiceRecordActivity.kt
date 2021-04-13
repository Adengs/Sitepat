package com.codelabs.dokter_mobil_customer.page.account

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import kotlinx.android.synthetic.main.toolbar_back.*

class ServiceRecordActivity : BaseActivity() {
    private var id: Int = 0
    private lateinit var adapter: ServiceRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_record)
        initView()

    }

    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.service_record)


    }
}