package com.codelabs.sitepat_customer.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.viewmodel.Logout
import kotlinx.android.synthetic.main.dialog_logout.*
import org.greenrobot.eventbus.EventBus

class LogoutDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_cancel.setOnClickListener {
            dismiss()
        }

        btn_yes.setOnClickListener {
            EventBus.getDefault().post(Logout())
            dismiss()
        }
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    companion object {
        @JvmStatic
        fun newInstance(): DialogFragment {
            val dialog =  LogoutDialog()
            return dialog
        }
    }
}