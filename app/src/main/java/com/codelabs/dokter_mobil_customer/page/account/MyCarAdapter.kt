package com.codelabs.dokter_mobil_customer.page.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.viewmodel.ItemMyCar
import kotlinx.android.synthetic.main.item_mycar.view.*

class MyCarAdapter (c : Context, var items : List<ItemMyCar>) : RecyclerView.Adapter<MyCarAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_mycar, viewGroup, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_plat_no.text = items[position].carPlateNumber
        holder.itemView.tv_tipe_mobil.text = items[position].carName
        holder.itemView.tv_tahun_mobil.text = items[position].carYear
        holder.itemView.iv_maintenance.visibility = if (items[position].isMaintenance == 1) View.VISIBLE else View.GONE
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}