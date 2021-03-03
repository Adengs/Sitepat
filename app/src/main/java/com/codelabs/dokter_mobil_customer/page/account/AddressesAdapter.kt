package com.codelabs.dokter_mobil_customer.page.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.viewmodel.Profile
import kotlinx.android.synthetic.main.item_addresses.view.*

class AddressesAdapter(val c: Context, var items: List<Profile.Addresses>) :
    RecyclerView.Adapter<AddressesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_addresses, viewGroup, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (items[position].isInput) {
            holder.itemView.layout_main.visibility = View.GONE
            holder.itemView.layout_add_address.visibility = View.VISIBLE
            holder.itemView.ll_background.background = c.resources.getDrawable(R.drawable.bg_border_btn_2)

        } else {
            holder.itemView.layout_main.visibility = View.VISIBLE
            holder.itemView.layout_add_address.visibility = View.GONE
            holder.itemView.ll_background.background = c.resources.getDrawable(R.drawable.background_edittext_grey)
            holder.itemView.tv_nomor.text = "${position + 1}"
            holder.itemView.tv_alamat.text = items[position].address
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}