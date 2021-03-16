package com.codelabs.dokter_mobil_customer.page.account

import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import kotlinx.android.synthetic.main.activity_account_change_password.*
import kotlinx.android.synthetic.main.toolbar_back.*

class AccountChangePasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_change_password)

        initView()
    }

    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        tv_title.text = getString(R.string.change_password)
        iv_back.setOnClickListener { finish() }

        img_eye_current_password.setOnClickListener(onClickEye(img_eye_current_password, txt_current_password))
        img_eye_new_password.setOnClickListener(onClickEye(img_eye_new_password, txt_new_password))
        img_eye_confirm_password.setOnClickListener(onClickEye(img_eye_confirm_password, txt_confirm_password))

    }

    private fun onClickEye(
        img: AppCompatImageView,
        txt: AppCompatEditText
    ): View.OnClickListener? {
        return View.OnClickListener {
            if (txt.transformationMethod != PasswordTransformationMethod.getInstance()) {
                img.setImageResource(R.drawable.ic_eye_gone)
                txt.setTransformationMethod(PasswordTransformationMethod.getInstance())
            } else {
                img.setImageResource(R.drawable.ic_eye_view)
                txt.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
            }
        }
    }
}