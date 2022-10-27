package com.codelabs.sitepat_customer.page.setting

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.connection.ApiUtils
import com.codelabs.sitepat_customer.connection.AppConstant
import com.codelabs.sitepat_customer.connection.DataManager
import com.codelabs.sitepat_customer.connection.ErrorUtils
import com.codelabs.sitepat_customer.helper.BaseActivity
import com.codelabs.sitepat_customer.page.main.MainActivity
import com.codelabs.sitepat_customer.page.setting.getting_started.GettingStartedLogin
import com.codelabs.sitepat_customer.viewmodel.Faq
import com.codelabs.sitepat_customer.viewmodel.ItemFaq
import kotlinx.android.synthetic.main.activity_getting_started.*
import kotlinx.android.synthetic.main.toolbar_back.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GettingStartedActivity : BaseActivity() {
    lateinit var adapter: AdapterGettingStarted
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getting_started)
        initview()
    }

    private fun initview() {
        getData()
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.getting_started)
        adapter = AdapterGettingStarted(this, listOf())

        rv_data.layoutManager = LinearLayoutManager(this)
        rv_data.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onClickItem(data: ItemFaq) {
        when(data.id){
            1->{
                val intent = Intent(this, GettingStartedLogin::class.java)
                intent.putExtra("IS_HIGHLIGHT_LOGIN",true)
                startActivity(intent)
            }
            3->{
                val intent = Intent(this, GettingStartedLogin::class.java)
                intent.putExtra("IS_HIGHLIGHT_REGIST",true)
                startActivity(intent)
            }
            4->{
                val intent = Intent(this, GettingStartedLogin::class.java)
                intent.putExtra("IS_HIGHLIGHT_FORGOT_PASSWORD",true)
                startActivity(intent)
            }
            5->{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("IS_HIGHLIGHT_OUTLET",true)
                startActivity(intent)

            }
        }
    }

    private fun getData() {
        showDialogProgress("Getting Data")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val call: Call<Faq> = ApiUtils.getApiService().getFaq(auth, 1, 1, 1000);
        call.enqueue(object : Callback<Faq> {
            override fun onResponse(call: Call<Faq>, data: Response<Faq>) {
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

            override fun onFailure(call: Call<Faq>, t: Throwable) {
                if (!call.isCanceled) {
                    hideDialogProgress()
                    showToast(getString(R.string.toast_onfailure))
                }
            }
        })
    }

}