package com.codelabs.sitepat_customer.adapter;

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

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.page.outlet.OutletDetailActivity;
import com.codelabs.sitepat_customer.viewmodel.Outlet;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutletAdapter extends RecyclerView.Adapter<OutletAdapter.viewHolder> {

    private final Context mContext;
    private List<Outlet.ItemsEntity> outletList;

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

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull OutletAdapter.viewHolder holder, int position) {
        if (position == 0) {
            EventBus.getDefault().post(outletList.get(position));
        }
        if (outletList.get(position).getSiteimage().equals("")) {
            holder.ivOutlet.setImageResource(R.drawable.background_outlet_status);
        } else {
            Picasso.get()
                    .load(outletList.get(position).getSiteimage())
                    .fit().centerCrop()
                    .placeholder(R.drawable.background_outlet_status)
                    .error(R.drawable.background_outlet_status)
                    .into(holder.ivOutlet);
        }

        holder.tvOutletName.setText(outletList.get(position).getSitename());
        holder.tvAddressOutlet.setText(outletList.get(position).getSiteaddress());
        holder.tvDistanceOutlet.setText(outletList.get(position).getDistance());
        if (outletList.get(position).getIsopen() == 1) {
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
                intent.putExtra("outlet_id", outletList.get(position).getSiteid());
                mContext.startActivity(intent);
                DataManager.getInstance().setImageOutlet(outletList.get(position).getSiteimage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (outletList != null ? outletList.size() : 0);
    }

    public void setData(List<Outlet.ItemsEntity> data) {
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
