package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.viewmodel.CategoryService;
import com.codelabs.sitepat_customer.viewmodel.ServiceSelected;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryServiceAdapter extends RecyclerView.Adapter<CategoryServiceAdapter.viewHolder>  {
    private Context mContext;
    private List<CategoryService.ItemsEntity> productList;
    public int variant = 0;

    public CategoryServiceAdapter(Context context){
        this.mContext = context;
        this.productList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CategoryServiceAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_service, parent,false);
        EventBus.getDefault().post(new ServiceSelected(String.valueOf(productList.get(0).getCategoryId())));
        return new CategoryServiceAdapter.viewHolder(view);
    }

    @SuppressLint({"RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull CategoryServiceAdapter.viewHolder holder, int position) {
//        holder.textType.setText(productList.get(position).getProductName());
        String type = productList.get(position).getCategoryName();
        String cartId = String.valueOf(productList.get(position).getCategoryId());
//        DataManager.getInstance().setDefaultCartId(String.valueOf(productList.get(0).getCategoryId()));

//        if (type.equals("")){
//            holder.textType.setVisibility(View.GONE);
//        }else{
//            holder.textType.setVisibility(View.VISIBLE);
//            holder.textType.setText(type);
//        }
        holder.tvCartService.setText(type);

        holder.tvCartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variant = position;
                EventBus.getDefault().post(new ServiceSelected(cartId));
                Log.e("cek data", String.valueOf(variant));

//                Log.e("cek_adapter", type);
                notifyDataSetChanged();
            }
        });

        if (variant == position){
            holder.viewTab.setBackgroundResource(R.color.red_text);
            holder.tvCartService.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }else {
            holder.viewTab.setBackgroundResource(R.color.grey_view);
            holder.tvCartService.setTextColor(ContextCompat.getColor(mContext, R.color.grey_500));
        }
    }

    @Override
    public int getItemCount() {
        return (productList != null ? productList.size() : 0);
    }

    public void setData(List<CategoryService.ItemsEntity> data) {
        this.productList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_cart_service)
        AppCompatTextView tvCartService;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.view_tab)
        View viewTab;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
