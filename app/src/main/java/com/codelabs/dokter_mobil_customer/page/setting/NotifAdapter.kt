package com.codelabs.dokter_mobil_customer.page.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.dokter_mobil_customer.R
import kotlinx.android.synthetic.main.item_notif.view.*
import com.codelabs.dokter_mobil_customer.viewmodel.ItemNotif

class NotifAdapter (c : Context, var items : List<ItemNotif>) : RecyclerView.Adapter<NotifAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_notif, viewGroup, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_date.text = items[position].notificationDate
        holder.itemView.tv_title.text = items[position].notificationTitle
        holder.itemView.tv_desc.text = items[position].notificationShortContent
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}