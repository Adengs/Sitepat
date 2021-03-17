package com.codelabs.dokter_mobil_customer.page.account

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import kotlinx.android.synthetic.main.activity_add_new_car.*
import kotlinx.android.synthetic.main.toolbar_back.*

class AddNewCarActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_car)
        initView()
    }

    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        tv_title.text = getString(R.string.new_car)
        iv_add_photo.setOnClickListener { showToast("This feature on develop ):") }
        iv_back.setOnClickListener { finish() }

    }

}