package com.codelabs.dokter_mobil_customer.page.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import com.codelabs.dokter_mobil_customer.viewmodel.Profile
import kotlinx.android.synthetic.main.activity_tambah_alamat.*

class TambahAlamatActivity : BaseActivity() {
    private lateinit var data: Profile.Addresses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_alamat)

        initView()
    }

    private fun initView() {
        data = intent.getSerializableExtra("DATA") as Profile.Addresses

        if (data.isInput){
            tv_title.text = "Add Address"
            tv_delete.visibility = View.GONE
        }else{

            tv_title.text = "Edit Address"
            tv_delete.visibility = View.VISIBLE

            txt_name.setText(data.name)
            txt_address.setText(data.address)
        }

        btn_save.setOnClickListener {
            if (txt_name.text!!.isEmpty()){
                showToast("Input Address Name")
                return@setOnClickListener
            }
            else if (txt_name.text!!.isEmpty()){
                showToast("Input Address")
                return@setOnClickListener
            }
            else {
                data.name = txt_name.text.toString()
                data.address = txt_address.text.toString()
                data.isInput = false

                val intent = Intent()
                intent.putExtra("DATA", data)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        tv_delete.setOnClickListener {
            if (txt_name.text!!.isEmpty()){
                showToast("Input Address Name")
                return@setOnClickListener
            }
            else if (txt_name.text!!.isEmpty()){
                showToast("Input Address")
                return@setOnClickListener
            }
            else {
                data.action = "DELETE"

                val intent = Intent()
                intent.putExtra("DATA", data)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}