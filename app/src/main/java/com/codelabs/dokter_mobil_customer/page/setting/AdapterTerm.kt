package com.codelabs.dokter_mobil_customer.page.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.dokter_mobil_customer.R
import kotlinx.android.synthetic.main.item_term.view.*

class AdapterTerm (c : Context, val items : List<String>) : RecyclerView.Adapter<AdapterTerm.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_term, viewGroup, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_title.text = items[position]
        holder.itemView.layout_main.setOnClickListener {
            if (holder.itemView.btn_readmore.text == "+"){
                holder.itemView.txt_detail.visibility = View.VISIBLE
                holder.itemView.btn_readmore.text = "-"
            }else{
                holder.itemView.txt_detail.visibility = View.GONE
                holder.itemView.btn_readmore.text = "+"
            }
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}