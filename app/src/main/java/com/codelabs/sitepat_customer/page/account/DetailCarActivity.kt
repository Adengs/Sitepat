package com.codelabs.sitepat_customer.page.account

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.connection.ApiUtils
import com.codelabs.sitepat_customer.connection.AppConstant
import com.codelabs.sitepat_customer.connection.DataManager
import com.codelabs.sitepat_customer.connection.ErrorUtils
import com.codelabs.sitepat_customer.helper.BaseActivity
import com.codelabs.sitepat_customer.viewmodel.DoPost
import com.codelabs.sitepat_customer.viewmodel.ItemMyCar
import com.codelabs.sitepat_customer.viewmodel.ServiceRecord
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_car.*
import kotlinx.android.synthetic.main.toolbar_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailCarActivity : BaseActivity() {
    private var id: Int = 0
    private lateinit var adapter: ServiceRecordAdapter
    private lateinit var data: ItemMyCar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_car)

        initView()
    }


    fun showPopup(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.submenu_detail_car)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.edit_car -> {
                    val intent = Intent(this, AddNewCarActivity::class.java)
                    intent.putExtra("IS_EDIT",true)
                    intent.putExtra("DATA", data)
                    startActivity(intent)
                }
                R.id.delete_car -> {
                   showDialogDelete()
                }
            }

            true
        })
        popup.show()
    }

    private fun showDialogDelete() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_delete_car)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val yesBtn = dialog.findViewById(R.id.btn_yes) as RelativeLayout
        val noBtn = dialog.findViewById(R.id.btn_cancel) as RelativeLayout
        yesBtn.setOnClickListener {
            deleteCar()
        }
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }


    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.detail_bike)
        rv_data.layoutManager = LinearLayoutManager(this)
        adapter = ServiceRecordAdapter(this, listOf())
        rv_data.adapter = adapter

        id = intent.getIntExtra("carId", 0)
        data = intent.getSerializableExtra("DATA") as ItemMyCar
        getData()
    }

    private fun getData() {
        showDialogProgress("Loading...")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call: Call<ServiceRecord> = ApiUtils.getApiService().getDetailCar(auth, id)
        call.enqueue(object : Callback<ServiceRecord> {
            override fun onResponse(call: Call<ServiceRecord>, data: Response<ServiceRecord>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        setData(response?.dataServiceRecord)
                    }
                } else {
                    val error = ErrorUtils.parseError(data)
                    showToast(error.message())
                }
            }

            override fun onFailure(call: Call<ServiceRecord>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })
    }

    private fun deleteCar() {
        showDialogProgress("Loading...")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call: Call<DoPost> = ApiUtils.getApiService().deleteCar(auth, id)
        call.enqueue(object : Callback<DoPost> {
            override fun onResponse(call: Call<DoPost>, data: Response<DoPost>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        showToast(response?.message)
                        onBackPressed()
                    }
                } else {
                    val error = ErrorUtils.parseError(data)
                    showToast(error.message())
                }
            }

            override fun onFailure(call: Call<DoPost>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })
    }

    private fun setData(data: ServiceRecord.DataServiceRecord?) {
        tv_maintenance.visibility =
            if (data?.detail?.isMaintenance == 1) View.VISIBLE else View.INVISIBLE

        if (data?.detail?.image!!.isNotEmpty())
            Picasso.get().load(data?.detail?.image).into(iv_mobil)
        tv_plat_no.text = data?.detail?.carPlateNumber
        tv_tipe_mobil.text = data?.detail?.carName
        tv_tahun_mobil.text = data?.detail?.carYear
        DataManager.getInstance().subtotalPayments = data?.payments?.subtotal
        DataManager.getInstance().ppn = data?.payments?.ppn
        DataManager.getInstance().totalPayments = data?.payments?.total

        if (data?.serviceRecords!!.size > 0) {
            adapter.items = data?.serviceRecords!!
            adapter.notifyDataSetChanged()
        } else
            layout_nothing.visibility = View.VISIBLE
    }


}