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
import com.codelabs.dokter_mobil_customer.viewmodel.DataDetailCar
import com.codelabs.dokter_mobil_customer.viewmodel.DetailCar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_car.*
import kotlinx.android.synthetic.main.toolbar_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCarActivity : BaseActivity() {
    private var id: Int = 0
    private lateinit var adapter: ServiceRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_car)

        initView()
    }

    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.detail_car)
        rv_data.layoutManager = LinearLayoutManager(this)
        adapter = ServiceRecordAdapter(this, listOf())
        rv_data.adapter = adapter

        id = intent.getIntExtra("DATA", 0)
        getData()
    }

    private fun getData() {
        showDialogProgress("Loading...")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call: Call<DetailCar> = ApiUtils.getApiService().getDetailCar(auth, id)
        call.enqueue(object : Callback<DetailCar> {
            override fun onResponse(call: Call<DetailCar>, data: Response<DetailCar>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        setData(response?.data)
                    }
                } else {
                    val error = ErrorUtils.parseError(data)
                    showToast(error.message())
                }
            }

            override fun onFailure(call: Call<DetailCar>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })
    }

    private fun setData(data: DataDetailCar?) {
        iv_maintenance.visibility =
            if (data?.detail?.isMaintenance == 1) View.VISIBLE else View.INVISIBLE

        Picasso.get().load(data?.detail?.image).into(iv_mobil)
        tv_plat_no.text = data?.detail?.carPlateNumber
        tv_tipe_mobil.text = data?.detail?.carName
        tv_tahun_mobil.text = data?.detail?.carYear
        if (data?.serviceRecords!!.size > 0) {
            adapter.items = data?.serviceRecords!!
            adapter.notifyDataSetChanged()
        } else
            layout_nothing.visibility = View.VISIBLE
    }
}