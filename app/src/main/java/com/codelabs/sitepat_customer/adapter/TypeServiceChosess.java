package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.viewmodel.TypeServiceSelected;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypeServiceChosess extends RecyclerView.Adapter<TypeServiceChosess.viewHolder>{
    private Context mContext;
    private List<TypeServiceSelected> typeServiceSelectedList;
    private int onClick = -1;

    public TypeServiceChosess(Context context){
        this.mContext = context;
        this.typeServiceSelectedList = new ArrayList<>();
    }


    @NonNull
    @Override
    public TypeServiceChosess.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_service_chosess , parent,false);
        return new TypeServiceChosess.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeServiceChosess.viewHolder holder, @SuppressLint("RecyclerView") int position) {
        String serviceName = typeServiceSelectedList.get(position).getServiceName();
        String descService = typeServiceSelectedList.get(position).getDescService();
//        String price = String.valueOf(typeServiceSelectedList.get(position).getPrice());
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String price = rupiah.format(new BigDecimal(typeServiceSelectedList.get(position).getPrice()));

//        holder.containerServiceChosess.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClick = position;
//                EventBus.getDefault().post(new MyMotocycleSelected(pet_id));
//                notifyDataSetChanged();
//            }
//        });

//        if (holder.tvServiceName.getText().toString() == typeServiceSelectedList.get(position).getServiceName()){
////            Toast.makeText(holder.itemView.getContext(), "Same data", Toast.LENGTH_LONG).show();
//        }
//        else {
            holder.tvServiceName.setText(serviceName);
            holder.tvDescService.setText(descService);
            holder.tvPrice.setText(price.replace(",00", "").replace("Rp", ""));
//        }

    }

    @Override
    public int getItemCount() {
        return (typeServiceSelectedList != null ? typeServiceSelectedList.size() : 0);
    }

    public void setData(List<TypeServiceSelected> data) {
        this.typeServiceSelectedList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_service_name)
        AppCompatTextView tvServiceName;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_desc_service)
        AppCompatTextView tvDescService;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_price)
        AppCompatTextView tvPrice;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_service_chosess)
        RelativeLayout containerServiceChosess;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
