package com.codelabs.dokter_mobil_customer.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.page.account.AddNewCarActivity
import com.codelabs.dokter_mobil_customer.viewmodel.ItemMyCar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_select_update.*

class UpdateCarDialog : BottomSheetDialogFragment() {

    private lateinit var data: ItemMyCar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_select_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_update.setOnClickListener {

            val intent = Intent(requireContext(), AddNewCarActivity::class.java)
            intent.putExtra("IS_EDIT",true)
            intent.putExtra("DATA",data)
            startActivity(intent)
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
        fun newInstance(data: ItemMyCar): BottomSheetDialogFragment {
            val dialog = UpdateCarDialog()
            dialog.data = data
            return dialog
        }
    }
}