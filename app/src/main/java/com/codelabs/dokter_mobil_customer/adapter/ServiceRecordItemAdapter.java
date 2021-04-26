package com.codelabs.dokter_mobil_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.page.service_record.ServiceRecordActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.ServiceRecord;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceRecordItemAdapter extends RecyclerView.Adapter<ServiceRecordItemAdapter.viewHolder> {

    private final Context mContext;
    private List<ServiceRecord.ItemsService> itemsServices;

    public ServiceRecordItemAdapter(Context context) {
        this.mContext = context;
        this.itemsServices = new ArrayList<>();
    }

    @NonNull
    @Override
    public ServiceRecordItemAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_detail_service_record, parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ServiceRecordItemAdapter.viewHolder holder, int position) {

        holder.tvTotalItem.setText(String.valueOf(itemsServices.get(position).getItemQty()));
        holder.tvServicePackage.setText(itemsServices.get(position).getItemName());
        holder.tvPricePackage.setText("Rp. " + " " + RecentUtils.toCurrency(itemsServices.get(position).getItemPrice().replace(".00","")));

    }


    @Override
    public int getItemCount() {
        return (itemsServices != null ? itemsServices.size() : 0);
    }

    public void setData(List<ServiceRecord.ItemsService> data) {
        this.itemsServices = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_total_item)
        AppCompatTextView tvTotalItem;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_service_package)
        AppCompatTextView tvServicePackage;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_price_package)
        AppCompatTextView tvPricePackage;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
