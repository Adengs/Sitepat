package com.codelabs.dokter_mobil_customer.page.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.dokter_mobil_customer.R
import com.codelabs.dokter_mobil_customer.helper.Utils
import com.codelabs.dokter_mobil_customer.viewmodel.ItemPointHistory
import kotlinx.android.synthetic.main.item_point_history.view.*

class HistoryPointAdapter (val c : Context, var items : List<ItemPointHistory>) : RecyclerView.Adapter<HistoryPointAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_point_history, viewGroup, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var point = StringBuilder()
        if (items[position].pointType == 1){
            point = point.append("You get ")
            point = point.append("<font color='#34A853'>${items[position].pointValue} Point</font> ")
        }else{
            point = point.append("You reedem ")
            point = point.append("<font color='#ED1C24'>${items[position].pointValue} Point</font>")
        }

        holder.itemView.tv_poin.text = Utils.fromHtml(point.toString())
        holder.itemView.tv_date.text = Utils.parseDate(items[position].createdAt,"yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy")
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}