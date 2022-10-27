package com.codelabs.sitepat_customer.page.setting

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.connection.ApiUtils
import com.codelabs.sitepat_customer.connection.AppConstant
import com.codelabs.sitepat_customer.connection.DataManager
import com.codelabs.sitepat_customer.connection.ErrorUtils
import com.codelabs.sitepat_customer.helper.BaseActivity
import com.codelabs.sitepat_customer.viewmodel.TermsCondition
import kotlinx.android.synthetic.main.activity_term_condition.*
import kotlinx.android.synthetic.main.toolbar_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermConditionActivity : BaseActivity() {
    lateinit var adapter : AdapterTerm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_condition)
        initview()
    }

    private fun initview() {
        getData()


        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.term_condition)
        adapter = AdapterTerm(this, listOf())

        rv_data.layoutManager = LinearLayoutManager(this)
        rv_data.adapter = adapter
    }

    private fun getData() {
        showDialogProgress("Getting Term Condition")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call : Call<TermsCondition> = ApiUtils.getApiService().getTC(auth, 1, 1, 1000);
        call.enqueue(object : Callback<TermsCondition> {
            override fun onResponse(call: Call<TermsCondition>, data: Response<TermsCondition>) {
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

            override fun onFailure(call: Call<TermsCondition>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })

    }
}