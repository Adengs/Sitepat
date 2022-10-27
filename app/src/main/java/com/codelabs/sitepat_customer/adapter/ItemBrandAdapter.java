package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.viewmodel.BrandSelected;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemBrandAdapter extends RecyclerView.Adapter<ItemBrandAdapter.viewHolder> {
    private Context mContext;
    private List<String> productList;
    private int brandClick = -1;

    public ItemBrandAdapter(Context context){
        this.mContext = context;
        this.productList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemBrandAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_brand, parent,false);
        return new ItemBrandAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemBrandAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {
        String brandName = productList.get(position);
        int positionBranded = DataManager.getInstance().getPositionBrand();

        if (DataManager.getInstance().getPositionBrand() == -1){
            holder.cbBrand.setChecked(false);
        }else {
            brandClick = DataManager.getInstance().getPositionBrand();
            holder.cbBrand.setChecked(true);
        }

        if (brandName.equals("")){
            holder.layType.setVisibility(View.GONE);
//            holder.view.setVisibility(View.GONE);
        }else {
            holder.layType.setVisibility(View.VISIBLE);
//            holder.view.setVisibility(View.VISIBLE);
            holder.brand.setText(brandName);
        }


        holder.brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brandClick = position;
                EventBus.getDefault().post(new BrandSelected(brandName, position));
                DataManager.getInstance().setPositionBrand(-1);
                Log.e("cek position", String.valueOf(position));
                notifyDataSetChanged();
            }
        });

        if (brandClick == position){
            holder.cbBrand.setChecked(true);
//            holder.textType.setBackgroundResource(R.drawable.bg_variant_orange);
//            holder.textType.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }else {
            holder.cbBrand.setChecked(false);
//            holder.textType.setBackgroundResource(R.drawable.bg_variant);
//            holder.textType.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return (productList != null ? productList.size() : 0);
    }

    public void setData(List<String> data) {
        this.productList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.brand)
        AppCompatTextView brand;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_brand)
        ConstraintLayout containerBrand;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.cb_brand)
        CheckBox cbBrand;
//        @SuppressLint("NonConstantResourceId")
//        @BindView(R.id.view)
//        View view;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.lay_brand)
        LinearLayout layType;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
