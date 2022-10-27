package com.codelabs.sitepat_customer.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.viewmodel.eventbus.PickImage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_pick_image.*
import org.greenrobot.eventbus.EventBus

class SelectImageDialog : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_pick_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_take_photo.setOnClickListener {
            EventBus.getDefault().post(PickImage(1))
            dismiss()
        }
        tv_gallery.setOnClickListener {
            EventBus.getDefault().post(PickImage(2))
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
        fun newInstance(): BottomSheetDialogFragment {
            val dialog = SelectImageDialog()
            return dialog
        }
    }

}