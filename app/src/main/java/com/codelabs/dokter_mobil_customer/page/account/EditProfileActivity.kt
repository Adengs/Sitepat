package com.codelabs.dokter_mobil_customer.page.account

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.connection.ApiUtils
import com.codelabs.dokter_mobil_customer.connection.AppConstant
import com.codelabs.dokter_mobil_customer.connection.DataManager
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import com.codelabs.dokter_mobil_customer.viewmodel.DoPost
import com.codelabs.dokter_mobil_customer.viewmodel.Profile
import com.codelabs.dokter_mobil_customer.viewmodel.param.UpdateAddress
import com.codelabs.dokter_mobil_customer.viewmodel.param.UpdateProfil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.toolbar_back.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : BaseActivity() {
    private val RESULT_ALAMAT: Int = 100
    private lateinit var dataProfile: Profile.DataProfile
    private lateinit var adapter: AddressesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initView()
    }

    private fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.edit_profile)

        rv_addresses.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = AddressesAdapter(this, listOf())
        rv_addresses.adapter = adapter

        getProfile()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
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
                        dataProfile = response?.dataProfile!!
                        setData()
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

    private fun setData() {
        if (dataProfile?.image!!.length > 0)
            Picasso.get().load(dataProfile?.image).into(iv_profil)

        txt_name.setText(dataProfile?.customerName)
        txt_email.setText(dataProfile?.customerEmail)
        txt_phone.setText(dataProfile?.customerPhone)
        txt_gender.setText(dataProfile?.customerGender)

        val add = Profile.Addresses();
        add.input = true
        dataProfile.addresses?.add(add)
        adapter.items = dataProfile.addresses
        adapter.notifyDataSetChanged()

        btn_save.setOnClickListener {
            saveProfile()
        }

    }

    @Subscribe
    fun onClickItem(data : Profile.Addresses){
        val intent = Intent(this, TambahAlamatActivity::class.java)
        intent.putExtra("DATA",data)
        startActivityForResult(intent, RESULT_ALAMAT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_ALAMAT && resultCode == RESULT_OK){
            val data = data?.getSerializableExtra("DATA") as Profile.Addresses
            when(data.action){
                "ADD"->{
                    dataProfile.addresses.set(data.position,data)

                    val add = Profile.Addresses();
                    add.input = true
                    dataProfile.addresses?.add(add)
                }
                "EDIT"->{
                    dataProfile.addresses.set(data.position,data)
                }
                "DELETE"->{
                    dataProfile.addresses.removeAt(data.position)
                }
            }
            adapter.notifyDataSetChanged()

        }
    }

    private fun saveProfile() {

        if (txt_name.text!!.isEmpty()) {
            showToast("Input name")
            return
        }
        if (txt_email.text!!.isEmpty()) {
            showToast("Input email")
            return
        }
        if (txt_phone.text!!.isEmpty()) {
            showToast("Input phone")
            return
        }
        if (txt_gender.text!!.isEmpty()) {
            showToast("Input gender")
            return
        }

        val address = ArrayList<UpdateAddress>()
        dataProfile.addresses.forEach {
            if (!it.isInput)
                address.add(UpdateAddress(name = it.name, content = it.address))
        }
        if (address.size == 0){
            showToast("Input address")
            return
        }

        val param = UpdateProfil(
            customerName = txt_name.text.toString(),
            customerEmail = txt_email.text.toString(),
            customerPhone = txt_phone.text.toString(),
            customerGender = txt_gender.text.toString(),
            address = address
        )

        showDialogProgress("Saving Profile")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call: Call<DoPost> = ApiUtils.getApiService().updateProfile(auth, param);
        call.enqueue(object : Callback<DoPost> {
            override fun onResponse(call: Call<DoPost>, data: Response<DoPost>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        showToast(response?.message)
                        finish()
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

}