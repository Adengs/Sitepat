package com.codelabs.sitepat_customer.page.Notif

import android.os.Build
import android.os.Bundle
import android.view.View
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.connection.ApiUtils
import com.codelabs.sitepat_customer.connection.AppConstant
import com.codelabs.sitepat_customer.connection.DataManager
import com.codelabs.sitepat_customer.connection.ErrorUtils
import com.codelabs.sitepat_customer.helper.BaseActivity
import com.codelabs.sitepat_customer.utils.RecentUtils
import com.codelabs.sitepat_customer.viewmodel.DetailNotif
import com.codelabs.sitepat_customer.viewmodel.ItemNotif
import kotlinx.android.synthetic.main.activity_notif_detail.*
import kotlinx.android.synthetic.main.toolbar_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifDetailActivity : BaseActivity() {
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif_detail)
        initView()
    }

    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        id = intent.getIntExtra("DATA",0)
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.notification)

        getData()
    }

    private fun getData() {
        showDialogProgress("Getting Notification Detail")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call : Call<DetailNotif> = ApiUtils.getApiService().getNotifDetail(auth,id);
        call.enqueue(object : Callback<DetailNotif> {
            override fun onResponse(call: Call<DetailNotif>, data: Response<DetailNotif>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        setDataNotif(response?.data)
                    }
                } else {
                    val error = ErrorUtils.parseError(data)
                    showToast(error.message())
                }
            }

            override fun onFailure(call: Call<DetailNotif>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })
    }

    private fun setDataNotif(data: ItemNotif?) {
        tv_title_notif.text = data?.notificationTitle
        tv_desc.text = RecentUtils.fromHtml(data?.notificationContent)
        btn_check_your_point.visibility = if (data?.isPoint!! == 1) View.VISIBLE else View.GONE
    }

}