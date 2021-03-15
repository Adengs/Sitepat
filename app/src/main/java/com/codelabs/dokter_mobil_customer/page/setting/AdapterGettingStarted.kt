package com.codelabs.dokter_mobil_customer.page.setting

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.page.setting.getting_started.GettingStartedLogin
import com.codelabs.dokter_mobil_customer.viewmodel.Faq
import com.codelabs.dokter_mobil_customer.viewmodel.ItemFaq
import kotlinx.android.synthetic.main.item_getting_started.view.*
import org.greenrobot.eventbus.EventBus

class AdapterGettingStarted (val c : Context, var items : List<ItemFaq>) : RecyclerView.Adapter<AdapterGettingStarted.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_getting_started, viewGroup, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_title.text = items[position].title
        holder.itemView.setOnClickListener {
            EventBus.getDefault().post(items[position])
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}