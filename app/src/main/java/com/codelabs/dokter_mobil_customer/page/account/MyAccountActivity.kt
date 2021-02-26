package com.codelabs.dokter_mobil_customer.page.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.connection.ApiUtils
import com.codelabs.dokter_mobil_customer.connection.AppConstant
import com.codelabs.dokter_mobil_customer.connection.DataManager
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import com.codelabs.dokter_mobil_customer.viewmodel.PrivacyPolicy
import com.codelabs.dokter_mobil_customer.viewmodel.Profile
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_account.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_setting.iv_back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAccountActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        iv_back.setOnClickListener { finish() }
        getProfile()
    }

    fun getProfile(){
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call : Call<Profile> = ApiUtils.getApiService().getProfile(auth);
        call.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, data: Response<Profile>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        tv_name.text = response?.data?.customerName
                        tv_email.text = response?.data?.customerEmail
                        Picasso.get().load(response?.data?.image).into(iv_photo)
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