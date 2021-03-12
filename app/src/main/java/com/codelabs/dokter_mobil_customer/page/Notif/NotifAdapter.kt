package com.codelabs.dokter_mobil_customer.page.Notif

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.utils.RecentUtils
import kotlinx.android.synthetic.main.item_notif.view.*
import com.codelabs.dokter_mobil_customer.viewmodel.ItemNotif

class NotifAdapter (val c : Context, var items : List<ItemNotif>) : RecyclerView.Adapter<NotifAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_notif, viewGroup, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_date.text = RecentUtils.formatDateToDateDMY(items[position].notificationDate)
        holder.itemView.tv_title.text = items[position].notificationTitle
        holder.itemView.tv_desc.text = items[position].notificationShortContent
        if (items[position].isRead == 0){
            holder.itemView.ll_background.setBackgroundColor(c.resources.getColor(R.color.gray_bg_notif))
        }else{
            holder.itemView.ll_background.setBackgroundColor(c.resources.getColor(R.color.white))

        }

        holder.itemView.setOnClickListener {
            val intent  = Intent(c,NotifDetailActivity::class.java)
            intent.putExtra("DATA", items[position].notificationId)
            c.startActivity(intent)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}