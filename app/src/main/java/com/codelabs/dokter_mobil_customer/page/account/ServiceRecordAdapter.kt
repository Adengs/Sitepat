package com.codelabs.dokter_mobil_customer.page.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.page.service_record.ServiceRecordActivity
import com.codelabs.dokter_mobil_customer.utils.RecentUtils
import com.codelabs.dokter_mobil_customer.viewmodel.ServiceRecord
import kotlinx.android.synthetic.main.item_service_records.view.*


class ServiceRecordAdapter (val c : Context, var items : List<ServiceRecord.serviceRecords>) : RecyclerView.Adapter<ServiceRecordAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_service_records, viewGroup, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_invoice.text = (items[position].invoiceCode)
        holder.itemView.tv_tgl_invoice.text = RecentUtils.formatDateToDateDMY(items[position].createdAt)
//        holder.itemView.tv_detail.setOnClickListener {
//            val intent = Intent(c, ServiceRecordActivity::class.java)
//            intent.putExtra("invoice_code", items[position].invoiceCode)
//            intent.putExtra("date_invoice", items[position].createdAt)
//            intent.putExtra("total_amount", items[position].totalAmount)
////            intent.putExtra("item_service", Gson().toJson(items[position].orders[position]))
////            val extra = Bundle()
////            extra.putSerializable("objects", items[position].orders[position])
////            intent.putExtra("item_service", extra)'
////            intent.putParcelableArrayListExtra ("nvp", items[position]);
//
//
//            c.startActivity(intent)
//        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}