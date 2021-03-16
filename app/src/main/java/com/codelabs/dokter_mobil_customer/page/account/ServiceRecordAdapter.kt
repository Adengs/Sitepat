package com.codelabs.dokter_mobil_customer.page.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.viewmodel.ServiceRecordMyCar
import kotlinx.android.synthetic.main.item_service_records.view.*

class ServiceRecordAdapter (c : Context, var items : List<ServiceRecordMyCar>) : RecyclerView.Adapter<ServiceRecordAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_service_records, viewGroup, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_invoice.text = items[position].invoiceCode
        holder.itemView.tv_tgl_invoice.text = items[position].createdAt
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}