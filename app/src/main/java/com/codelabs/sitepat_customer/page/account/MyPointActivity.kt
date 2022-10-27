package com.codelabs.sitepat_customer.page.account

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.connection.ApiUtils
import com.codelabs.sitepat_customer.connection.AppConstant
import com.codelabs.sitepat_customer.connection.DataManager
import com.codelabs.sitepat_customer.connection.ErrorUtils
import com.codelabs.sitepat_customer.helper.BaseActivity
import com.codelabs.sitepat_customer.viewmodel.PointHistory
import com.codelabs.sitepat_customer.viewmodel.Profile
import kotlinx.android.synthetic.main.activity_my_point.*
import kotlinx.android.synthetic.main.toolbar_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPointActivity : BaseActivity() {
    private lateinit var adapter: HistoryPointAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_point)

        initView()
    }

    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        iv_back.setOnClickListener { finish() }
        rv_data.layoutManager = LinearLayoutManager(this)
        adapter = HistoryPointAdapter(this, listOf())
        rv_data.adapter = adapter

        tv_title.text = "My Point"

        btn_reedem.setOnClickListener {
            val intent = Intent(this, ReedemPointActivity::class.java)
            startActivity(intent)
        }
        getProfile()
        getData()
    }

    fun getProfile() {
//        showDialogProgress("Getting My Account")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call: Call<Profile> = ApiUtils.getApiService().getProfile(auth);
        call.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, data: Response<Profile>) {
//                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        tv_total_point.text = response?.dataProfile?.totalPoint.toString()
                    }
                } else {
                    val error = ErrorUtils.parseError(data)
                    showToast(error.message())
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                if (!call.isCanceled) {
//                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })

    }

    private fun getData() {
        showDialogProgress("Getting Point History")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call : Call<PointHistory> = ApiUtils.getApiService().getHistoryPoint(auth);
        call.enqueue(object : Callback<PointHistory> {
            override fun onResponse(call: Call<PointHistory>, data: Response<PointHistory>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        adapter.items = response?.data?.items.orEmpty()
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    val error = ErrorUtils.parseError(data)
                    showToast(error.message())
                }
            }

            override fun onFailure(call: Call<PointHistory>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })
    }
}