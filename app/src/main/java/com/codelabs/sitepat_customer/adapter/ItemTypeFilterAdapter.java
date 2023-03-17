package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.codelabs.sitepat_customer.viewmodel.TypeFilter;
import com.codelabs.sitepat_customer.viewmodel.TypeFilterSelected;
import com.codelabs.sitepat_customer.viewmodel.TypeFilterShop;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemTypeFilterAdapter  extends RecyclerView.Adapter<ItemTypeFilterAdapter.viewHolder>{
    private Context mContext;
    private List<TypeFilterShop.ItemsEntity> productList;
    private int brandClick = -1;

    public ItemTypeFilterAdapter(Context context){
        this.mContext = context;
        this.productList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemTypeFilterAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type_filter, parent,false);
        return new ItemTypeFilterAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTypeFilterAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {
        String brandName = productList.get(position).getCategoryName();

        if (DataManager.getInstance().getPositionType() == -1){
            holder.cbType.setChecked(false);
        }else {
            brandClick = DataManager.getInstance().getPositionType();
            holder.cbType.setChecked(true);
        }

        if (brandName.equals("")){
            holder.layType.setVisibility(View.GONE);
//            holder.view.setVisibility(View.GONE);
        }else {
            holder.layType.setVisibility(View.VISIBLE);
//            holder.view.setVisibility(View.VISIBLE);
            holder.type.setText(brandName);
        }

        holder.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brandClick = position;
                EventBus.getDefault().post(new TypeFilterSelected(brandName, position));
                DataManager.getInstance().setPositionType(-1);
//                DataManager.getInstance().setType(type);
//                Log.e("cek",DataManager.getInstance().getType());
                notifyDataSetChanged();
            }
        });

        if (brandClick == position){
            holder.cbType.setChecked(true);
//            holder.textType.setBackgroundResource(R.drawable.bg_variant_orange);
//            holder.textType.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }else {
            holder.cbType.setChecked(false);
//            holder.textType.setBackgroundResource(R.drawable.bg_variant);
//            holder.textType.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return (productList != null ? productList.size() : 0);
    }

    public void setData(List<TypeFilterShop.ItemsEntity> data) {
        this.productList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.type)
        AppCompatTextView type;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_type)
        ConstraintLayout containerType;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.cb_type)
        CheckBox cbType;
//        @SuppressLint("NonConstantResourceId")
//        @BindView(R.id.view)
//        View view;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.lay_type)
        LinearLayout layType;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
