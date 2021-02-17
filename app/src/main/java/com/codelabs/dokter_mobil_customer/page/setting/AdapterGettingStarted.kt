package com.codelabs.dokter_mobil_customer.page.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.dokter_mobil_customer.R
import kotlinx.android.synthetic.main.item_getting_started.view.*

class AdapterGettingStarted (c : Context, val items : List<String>) : RecyclerView.Adapter<AdapterGettingStarted.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_getting_started, viewGroup, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_title.text = items[position]
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}