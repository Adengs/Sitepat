package com.codelabs.dokter_mobil_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.viewmodel.ServiceRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceRecordHorizontalAdapter extends RecyclerView.Adapter<ServiceRecordHorizontalAdapter.viewHolder> {

    private final Context mContext;
    private List<String> detailService;
    ServiceRecordItemAdapter serviceRecordItemAdapter;

    public ServiceRecordHorizontalAdapter(Context mContext, ServiceRecordItemAdapter serviceRecordItemAdapter) {
        this.mContext = mContext;
        this.detailService = new ArrayList<>();
        this.serviceRecordItemAdapter = serviceRecordItemAdapter;
    }


    @NonNull
    @Override
    public ServiceRecordHorizontalAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_service_record_horizontal, parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ServiceRecordHorizontalAdapter.viewHolder holder, int position) {
        holder.tvServiceItems.setText((CharSequence) detailService.get(position));
    }

    @Override
    public int getItemCount() {
        return (detailService != null ? detailService.size() : 0);
    }

    public void setData(List<String> data) {
        this.detailService = data;
        notifyDataSetChanged();
    }



    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_service_items)
        AppCompatTextView tvServiceItems;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
