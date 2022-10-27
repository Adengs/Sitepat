package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.page.shop.ShopActivity;
import com.codelabs.sitepat_customer.viewmodel.CartSelected;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypeProductAdapter extends RecyclerView.Adapter<TypeProductAdapter.viewHolder>  {
    private Context mContext;
    private List<String> productList;
    private ShopActivity activity = (ShopActivity)mContext;
    private int variant = -1;

    public TypeProductAdapter(Context context){
        this.mContext = context;
        this.productList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TypeProductAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type, parent,false);
        return new TypeProductAdapter.viewHolder(view);
    }

    @SuppressLint({"RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull TypeProductAdapter.viewHolder holder, int position) {
//        holder.textType.setText(productList.get(position).getProductName());
        String type = productList.get(position);
//
        if (type.equals("")){
//            holder.containerType.setVisibility(View.GONE);
            holder.textType.setVisibility(View.GONE);
        }else{
            holder.textType.setVisibility(View.VISIBLE);
            holder.textType.setText(type);
//            holder.containerType.setVisibility(View.VISIBLE);
//            variant = Integer.parseInt(type);
        }

        holder.containerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variant = position;
                EventBus.getDefault().post(new CartSelected(type));

                Log.e("cek_adapter", type);
                notifyDataSetChanged();
            }
        });

        if (variant == position){
            holder.textType.setBackgroundResource(R.drawable.bg_variant_orange);
            holder.textType.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }else {
            holder.textType.setBackgroundResource(R.drawable.bg_variant);
            holder.textType.setTextColor(ContextCompat.getColor(mContext, R.color.black));
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
        @BindView(R.id.text_type)
        AppCompatTextView textType;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_type)
        RelativeLayout containerType;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
