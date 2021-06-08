package com.codelabs.dokter_mobil_customer.page.account

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.connection.DataManager
import com.codelabs.dokter_mobil_customer.viewmodel.ItemMyCar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_mycar.view.*
import org.greenrobot.eventbus.EventBus

class MyCarAdapter(val c: Context, var items: List<ItemMyCar>) :
    RecyclerView.Adapter<MyCarAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_mycar, viewGroup, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (items[position].image.isNotEmpty())
            Picasso.get().load(items[position].image).into(holder.itemView.iv_mobil)
        holder.itemView.tv_plat_no.text = items[position].carPlateNumber
        holder.itemView.tv_tipe_mobil.text = items[position].carName
        holder.itemView.tv_tahun_mobil.text = items[position].carYear
        holder.itemView.iv_maintenance.visibility =
            if (items[position].isMaintenance == 1) View.VISIBLE else View.GONE
        holder.itemView.ll_background.setOnClickListener {
            val intent = Intent(c, DetailCarActivity::class.java)
            intent.putExtra("DATA", items[position].carId)
            DataManager.getInstance().customerCarId = items[position].carId
            c.startActivity(intent)
        }

        holder.itemView.ll_background.setOnLongClickListener {
            EventBus.getDefault().post(items[position])
            return@setOnLongClickListener true
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}