package com.codelabs.dokter_mobil_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.page.outlet.OutletDetailActivity;
import com.codelabs.dokter_mobil_customer.viewmodel.Outlet;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutletAdapter extends RecyclerView.Adapter<OutletAdapter.viewHolder> {

    private final Context mContext;
    private List<Outlet.ItemsOutlet> outletList;

    public OutletAdapter(Context context) {
        this.mContext = context;
        this.outletList = new ArrayList<>();
    }

    @NonNull
    @Override
    public OutletAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_outlet_search, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutletAdapter.viewHolder holder, int position) {
        if (position == 0) {
            EventBus.getDefault().post(outletList.get(position));
        }
        if (outletList.get(position).getSiteImage().equals("")) {
            holder.ivOutlet.setImageResource(R.drawable.background_outlet_status);
        } else {
            Picasso.get()
                    .load(outletList.get(position).getSiteImage())
                    .fit().centerCrop()
                    .placeholder(R.drawable.background_outlet_status)
                    .error(R.drawable.background_outlet_status)
                    .into(holder.ivOutlet);
        }

        holder.tvOutletName.setText(outletList.get(position).getSiteName());
        holder.tvAddressOutlet.setText(outletList.get(position).getSiteAddress());
        holder.tvDistanceOutlet.setText(outletList.get(position).getDistance());
        if (outletList.get(position).getIsOpen() == 1) {
            holder.tvStatusOutlet.setVisibility(View.VISIBLE);
            holder.tvStatusOutletClose.setVisibility(View.GONE);
        } else {
            holder.tvStatusOutletClose.setVisibility(View.VISIBLE);
            holder.tvStatusOutlet.setVisibility(View.GONE);
        }

        holder.containerOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OutletDetailActivity.class);
                intent.putExtra("outlet_id", outletList.get(position).getSiteId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (outletList != null ? outletList.size() : 0);
    }

    public void setData(List<Outlet.ItemsOutlet> data) {
        this.outletList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.iv_outlet)
        AppCompatImageView ivOutlet;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_outlet_name)
        AppCompatTextView tvOutletName;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_address_outlet)
        AppCompatTextView tvAddressOutlet;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_distance_outlet)
        AppCompatTextView tvDistanceOutlet;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_status_outlet)
        AppCompatTextView tvStatusOutlet;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_outlet_search)
        RelativeLayout containerOutlet;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_status_outlet_close)
        AppCompatTextView tvStatusOutletClose;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
