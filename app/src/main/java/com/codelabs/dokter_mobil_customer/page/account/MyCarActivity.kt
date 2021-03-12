package com.codelabs.dokter_mobil_customer.page.account

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.connection.ApiUtils
import com.codelabs.dokter_mobil_customer.connection.AppConstant
import com.codelabs.dokter_mobil_customer.connection.DataManager
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import com.codelabs.dokter_mobil_customer.viewmodel.MyCar
import kotlinx.android.synthetic.main.activity_my_car.*
import kotlinx.android.synthetic.main.toolbar_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCarActivity : BaseActivity() {
    private lateinit var adapter: MyCarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_car)
        initview()
    }

    private fun initview() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        tv_title.text = getString(R.string.my_car)
        iv_back.setOnClickListener { finish() }
        rv_data.layoutManager = LinearLayoutManager(this)
        adapter = MyCarAdapter(this, listOf())
        rv_data.adapter = adapter
        getData()
    }

    private fun getData() {
        showDialogProgress("Getting My Car")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call : Call<MyCar> = ApiUtils.getApiService().getCustomerCar(auth)
        call.enqueue(object : Callback<MyCar> {
            override fun onResponse(call: Call<MyCar>, data: Response<MyCar>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        adapter.items = response?.data?.items!!
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    val error = ErrorUtils.parseError(data)
                    showToast(error.message())
                }
            }

            override fun onFailure(call: Call<MyCar>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })
    }

}