package com.codelabs.sitepat_customer.page.account

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.page.service_record.ServiceRecordActivity
import com.codelabs.sitepat_customer.utils.RecentUtils
import com.codelabs.sitepat_customer.viewmodel.ServiceRecord
import kotlinx.android.synthetic.main.item_service_records.view.*
import java.io.Serializable


class ServiceRecordAdapter (val c : Context, var items : List<ServiceRecord.serviceRecords>) : RecyclerView.Adapter<ServiceRecordAdapter.MyViewHolder>() {

    private lateinit var data: ServiceRecord.serviceRecords

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
        holder.itemView.tv_detail.setOnClickListener {
            val intent = Intent(c, ServiceRecordActivity::class.java)
            intent.putExtra("invoice_code", items[position].invoiceCode)
            intent.putExtra("date_invoice", items[position].createdAt)
            intent.putExtra("total_amount", items[position].paymentsService.total)
            intent.putExtra("subtotal", items[position].paymentsService.subtotal)
            intent.putExtra("ppn", items[position].paymentsService.ppn)

            intent.putExtra("servicelist", items[position].orders as Serializable)




           /* val bundle = Bundle()
            bundle.putParcelable("data", sharedBookingObject)
            intent.putExtras(bundle)*/


            c.startActivity(intent)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}