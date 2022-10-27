package com.codelabs.sitepat_customer.page.Notif

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.connection.ApiUtils
import com.codelabs.sitepat_customer.connection.AppConstant
import com.codelabs.sitepat_customer.connection.DataManager
import com.codelabs.sitepat_customer.connection.ErrorUtils
import com.codelabs.sitepat_customer.helper.BaseActivity
import com.codelabs.sitepat_customer.viewmodel.Notification
import kotlinx.android.synthetic.main.activity_notification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity : BaseActivity() {

    lateinit var adapter : NotifAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        iv_back.setOnClickListener { finish() }


        initView()
    }

    private fun initView() {
        adapter = NotifAdapter(this, listOf())

        rv_data.layoutManager = LinearLayoutManager(this)
        rv_data.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        showDialogProgress("Getting Notification")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call : Call<Notification> = ApiUtils.getApiService().getNotif(auth);
        call.enqueue(object : Callback<Notification> {
            override fun onResponse(call: Call<Notification>, data: Response<Notification>) {
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

            override fun onFailure(call: Call<Notification>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })
    }

}