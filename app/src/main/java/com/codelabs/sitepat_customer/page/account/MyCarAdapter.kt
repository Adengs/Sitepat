package com.codelabs.sitepat_customer.page.account

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codelabs.sitepat_customer.R
import com.codelabs.sitepat_customer.connection.DataManager
import com.codelabs.sitepat_customer.viewmodel.ItemMyCar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_mycar.view.*

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
            Glide.with(holder.itemView.context)
                .load(items[position].image)
                .thumbnail(0.25f)
                .diskCacheStrategy( DiskCacheStrategy.ALL )
//                .dontTransform()
                .into(holder.itemView.iv_mobil)
        holder.itemView.tv_plat_no.text = items[position].carPlateNumber
        holder.itemView.tv_tipe_mobil.text = items[position].carName
        holder.itemView.tv_tahun_mobil.text = items[position].carYear
        holder.itemView.iv_maintenance.visibility =
            if (items[position].isMaintenance == 1) View.VISIBLE else View.GONE
        holder.itemView.ll_background.setOnClickListener {
            val intent = Intent(c, DetailCarActivity::class.java)
            intent.putExtra("carId", items[position].carId)
            intent.putExtra("DATA", items[position])
            Log.e("CEK Adapter", "onBindViewHolder: " + items[position] )
            DataManager.getInstance().customerCarId = items[position].carId
            c.startActivity(intent)
        }

       /* holder.itemView.ll_background.setOnLongClickListener {
            EventBus.getDefault().post(items[position])
            return@setOnLongClickListener true
        }*/
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}