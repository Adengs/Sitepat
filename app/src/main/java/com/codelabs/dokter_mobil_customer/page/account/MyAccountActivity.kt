package com.codelabs.dokter_mobil_customer.page.account

import android.content.Intent
import android.os.Bundle
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.connection.ApiUtils
import com.codelabs.dokter_mobil_customer.connection.AppConstant
import com.codelabs.dokter_mobil_customer.connection.DataManager
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import com.codelabs.dokter_mobil_customer.viewmodel.Profile
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAccountActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        initView()

    }

    private fun initView() {
        iv_card.setOnClickListener {
            val intent = Intent(this,DetailCardActivity::class.java)
            startActivity(intent)
        }
        ll_my_cars.setOnClickListener {
            val intent = Intent(this,MyCarActivity::class.java)
            startActivity(intent)
        }
        iv_back.setOnClickListener { finish() }
        getProfile()    }

    fun getProfile(){
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call : Call<Profile> = ApiUtils.getApiService().getProfile(auth);
        call.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, data: Response<Profile>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        tv_name.text = response?.dataProfile?.customerName
                        tv_email.text = response?.dataProfile?.customerEmail
                        if(response?.dataProfile?.image!!.length > 0)
                            Picasso.get().load(response?.dataProfile?.image).into(iv_photo)
                    }
                } else {
                    val error = ErrorUtils.parseError(data)
                    showToast(error.message())
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })

    }
}