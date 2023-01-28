package com.codelabs.sitepat_customer.page.account

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.connection.ApiUtils
import com.codelabs.sitepat_customer.connection.AppConstant
import com.codelabs.sitepat_customer.connection.DataManager
import com.codelabs.sitepat_customer.connection.ErrorUtils
import com.codelabs.sitepat_customer.helper.BaseActivity
import com.codelabs.sitepat_customer.page.login.LoginActivity
import com.codelabs.sitepat_customer.viewmodel.DoPost
import kotlinx.android.synthetic.main.activity_account_change_password.*
import kotlinx.android.synthetic.main.toolbar_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        img_eye_current_password.setOnClickListener(
            onClickEye(
                img_eye_current_password,
                txt_current_password
            )
        )
        img_eye_new_password.setOnClickListener(onClickEye(img_eye_new_password, txt_new_password))
        img_eye_confirm_password.setOnClickListener(
            onClickEye(
                img_eye_confirm_password,
                txt_confirm_password
            )
        )

        btn_update_password.setOnClickListener {
            changePassword()
        }
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

    private fun changePassword() {
        if (!valid()) {
            return
        }
        showDialogProgress("Checking New Password ")
        val auth = AppConstant.AuthValue + " " + DataManager.getInstance().token
        val param = HashMap<String, String>()
        param["old_password"] = txt_current_password.text.toString().trim()
        param["new_password"] = txt_new_password.text.toString().trim()
        param["new_password_confirmation"] = txt_confirm_password.text.toString().trim()
        val call: Call<DoPost> = ApiUtils.getApiService().changePassword(auth, param);
        call.enqueue(object : Callback<DoPost> {
            override fun onResponse(call: Call<DoPost>, data: Response<DoPost>) {
                hideDialogProgress()
                if (data.isSuccessful) {
                    val response = data.body()
                    if (data.code() == 200) {
                        showToast(response?.message)
                        DataManager.getInstance().doLogout()
                        val intent = Intent(this@AccountChangePasswordActivity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
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

    private fun valid(): Boolean {
        if (txt_current_password.text.toString().trim().isEmpty()) {
            showToast("Input current password")
            return false
        }
        if (txt_new_password.text.toString().trim().isEmpty()) {
            showToast("Input new password")
            return false
        }
        if (txt_confirm_password.text.toString().trim().isEmpty()) {
            showToast("Input confirm password")
            return false
        }
        if (txt_new_password.text.toString().trim().length <8) {
            showToast("New password must be more than 8 characters")
            return false
        }
        if (txt_confirm_password.text.toString().trim().length <8) {
            showToast("Confirmation password must be more than 8 characters")
            return false
        }
        if (!txt_confirm_password.text.toString().trim().equals(txt_new_password.text.toString().trim())) {
            showToast("Confirmation password must be the same with the new password")
            return false
        }
        return true
    }

}