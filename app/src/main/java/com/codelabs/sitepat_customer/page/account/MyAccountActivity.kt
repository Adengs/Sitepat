package com.codelabs.sitepat_customer.page.account

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.connection.ApiUtils
import com.codelabs.sitepat_customer.connection.AppConstant
import com.codelabs.sitepat_customer.connection.DataManager
import com.codelabs.sitepat_customer.connection.ErrorUtils
import com.codelabs.sitepat_customer.helper.BaseActivity
import com.codelabs.sitepat_customer.page.about.AboutUsActivity
import com.codelabs.sitepat_customer.page.support.SupportActivity
import com.codelabs.sitepat_customer.viewmodel.Profile
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


    override fun onRestart() {
        super.onRestart()
        getProfile()
    }

    private fun initView() {

        ll_change_password.setOnClickListener {
            val intent = Intent(this, AccountChangePasswordActivity::class.java)
            startActivity(intent)
        }
        btn_open_now.setOnClickListener {
            val intent = Intent(this, DetailCardActivity::class.java)
            startActivity(intent)
        }
        ll_my_cars.setOnClickListener {
            val intent = Intent(this, MyCarActivity::class.java)
            startActivity(intent)
        }
        ll_my_poin.setOnClickListener {
            val intent = Intent(this, MyPointActivity::class.java)
            startActivity(intent)
        }
        btn_edit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
//        ll_customer.setOnClickListener {
//            val intent = Intent(this, EditProfileActivity::class.java)
//            startActivity(intent)
//        }
        ll_support.setOnClickListener {
            val intent = Intent(this, SupportActivity::class.java)
            startActivity(intent)
        }
        ll_about.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
        iv_back.setOnClickListener { finish() }
        getProfile()
    }

    fun getProfile() {
        showDialogProgress("Getting My Account")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call: Call<Profile> = ApiUtils.getApiService().getProfile(auth);
        call.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, data: Response<Profile>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        tv_name.text = response?.dataProfile?.customerName
                        tv_email.text = response?.dataProfile?.customerEmail
                        if (response?.dataProfile?.image!!.length > 0)
                            Glide.with(this@MyAccountActivity)
                                .load(response?.dataProfile?.image)
                                .thumbnail(0.25f)
                                .diskCacheStrategy( DiskCacheStrategy.ALL )
//                                .dontTransform()
                                .into(iv_photo)
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