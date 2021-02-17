package com.codelabs.dokter_mobil_customer.page.setting

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.helper.BaseActivity
import kotlinx.android.synthetic.main.activity_term_condition.*
import kotlinx.android.synthetic.main.toolbar_back.*

class TermConditionActivity : BaseActivity() {
    lateinit var adapter : AdapterTerm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_condition)
        initview()
    }

    private fun initview() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.term_condition)
        val data = listOf("A. Lorem Ipsum","B. Sit dolor ","C. Amet sit","D. Lorem")
        adapter = AdapterTerm(this,data)

        rv_data.layoutManager = LinearLayoutManager(this)
        rv_data.adapter = adapter
    }
}