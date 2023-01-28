package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.viewmodel.CloseOutletSelected;
import com.codelabs.sitepat_customer.viewmodel.Outlet;
import com.codelabs.sitepat_customer.viewmodel.OutletSelected;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutletBookingAdapter extends RecyclerView.Adapter<OutletBookingAdapter.viewHolder> {
    private final Context mContext;
    private List<Outlet.ItemsEntity> outletList;
    private int click = -1;
    private String close = "";
    private int clickPosition = -1;
    public OutletBookingAdapter(Context context) {
        this.mContext = context;
        this.outletList = new ArrayList<>();
    }

    @NonNull
    @Override
    public OutletBookingAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_outlet_search, parent, false);
        return new OutletBookingAdapter.viewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull OutletBookingAdapter.viewHolder holder, int position) {
        String site = String.valueOf(outletList.get(position).getSiteid());

        if (DataManager.getInstance().getPositionLocation() == -1){
            holder.containerOutlet.setBackgroundResource(R.color.white);
        }else{
            click = DataManager.getInstance().getPositionLocation();
            String siteSph = DataManager.getInstance().getSiteId();
            String closeO = DataManager.getInstance().getClose();
            int positionSph = DataManager.getInstance().getPositionLocation();
            EventBus.getDefault().post(new OutletSelected(siteSph,positionSph));
            EventBus.getDefault().post(new CloseOutletSelected(closeO));
        }

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
                if (outletList.get(position).getIsopen() ==1){
                    close = "";
                }
                else {
                    Toast.makeText(mContext, "Please select an outlet that is currently open", Toast.LENGTH_LONG).show();
                    close = "1";
                }

                click = position;
                EventBus.getDefault().post(new OutletSelected(site, position));
                Log.e("cek klik even", close);
                EventBus.getDefault().post(new CloseOutletSelected(close));
                DataManager.getInstance().setSiteId(site);
                DataManager.getInstance().setPositionLocation(-1);
                DataManager.getInstance().setClose(close);
                DataManager.getInstance().setOutlet(outletList.get(position).getSitename());
                notifyDataSetChanged();
            }
        });

        if (click == position){
            holder.containerOutlet.setBackgroundResource(R.color.orange_background);
            if (outletList.get(position).getIsopen() == 0){
                holder.containerOutlet.setBackgroundResource(R.color.white);
            }

//            if (outletList.get(position).getIsOpen() ==1){
//                holder.backgroundClick.setBackgroundResource(R.color.orange_background);
//                close = "1";
//            }
//            else {
////                DataManager.getInstance().setPositionLocation(-1);
//                Toast.makeText(mContext, "Please select an outlet that is currently open", Toast.LENGTH_LONG).show();
//                holder.backgroundClick.setBackgroundResource(R.color.white);
//                close = "";
//            }
        }else {
            holder.containerOutlet.setBackgroundResource(R.color.white);
        }

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
//        @SuppressLint("NonConstantResourceId")
//        @BindView(R.id.lay_rv)
//        RelativeLayout backgroundClick;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
