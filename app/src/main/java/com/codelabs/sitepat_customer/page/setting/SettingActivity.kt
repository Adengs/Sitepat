package com.codelabs.sitepat_customer.page.setting

import android.content.Intent
import android.os.Bundle
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.connection.DataManager
import com.codelabs.sitepat_customer.dialog.LogoutDialog
import com.codelabs.sitepat_customer.helper.BaseActivity
import com.codelabs.sitepat_customer.page.login.LoginActivity
import com.codelabs.sitepat_customer.viewmodel.Logout
import kotlinx.android.synthetic.main.activity_setting.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initView()
    }

    private fun initView() {
        iv_back.setOnClickListener { finish() }
        ll_getting_started.setOnClickListener {
            val intent = Intent(this, GettingStartedActivity::class.java)
            startActivity(intent)
        }
        ll_term.setOnClickListener {
            val intent = Intent(this, TermConditionActivity::class.java)
            startActivity(intent)
        }
        ll_privacy.setOnClickListener {
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }

        ll_logout.setOnClickListener {
            val dialog = LogoutDialog.newInstance()
            dialog.show(supportFragmentManager, "")
        }
    }

    @Subscribe
    fun onLogout(data: Logout) {
        DataManager.getInstance().doLogout()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}