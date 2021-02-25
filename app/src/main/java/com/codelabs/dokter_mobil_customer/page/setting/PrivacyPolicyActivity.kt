package com.codelabs.dokter_mobil_customer.page.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.connection.ApiUtils
import com.codelabs.dokter_mobil_customer.connection.AppConstant
import com.codelabs.dokter_mobil_customer.connection.DataManager
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import com.codelabs.dokter_mobil_customer.viewmodel.PrivacyPolicy
import com.codelabs.dokter_mobil_customer.viewmodel.TermsCondition
import kotlinx.android.synthetic.main.activity_term_condition.*
import kotlinx.android.synthetic.main.toolbar_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrivacyPolicyActivity : BaseActivity() {
    lateinit var adapter : AdapterPrivacyPolicy
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        initview()
    }

    private fun initview() {
        getData()
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.privacy_policy)
        adapter = AdapterPrivacyPolicy(this, listOf())

        rv_data.layoutManager = LinearLayoutManager(this)
        rv_data.adapter = adapter
    }

    private fun getData() {
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call : Call<PrivacyPolicy> = ApiUtils.getApiService().getPrivacyPolicy(auth, 1, 1, 1000);
        call.enqueue(object : Callback<PrivacyPolicy> {
            override fun onResponse(call: Call<PrivacyPolicy>, data: Response<PrivacyPolicy>) {
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

            override fun onFailure(call: Call<PrivacyPolicy>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })

    }

}