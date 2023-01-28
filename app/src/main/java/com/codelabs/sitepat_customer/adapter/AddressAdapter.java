package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.viewmodel.CategoryService;
import com.codelabs.sitepat_customer.viewmodel.Maps;
import com.codelabs.sitepat_customer.viewmodel.ServiceSelected;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.viewHolder> {
    private Context mContext;
    public List<Maps> productList;
    public int variant = 0;
    public OnChoose onChoose;

    public AddressAdapter(Context context){
        this.mContext = context;
        this.productList = new ArrayList<>();
    }

    public interface OnChoose{
        void onChoose(String name, String lat, String lon);
    }

    public void OnChooseAddress(OnChoose onChoose){
        this.onChoose = onChoose;
    }

    @NonNull
    @Override
    public AddressAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_address, parent,false);
//        EventBus.getDefault().post(new ServiceSelected(String.valueOf(productList.get(0).getCategoryId())));
        return new AddressAdapter.viewHolder(view);
    }

    @SuppressLint({"RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.viewHolder holder, int position) {
//        holder.textType.setText(productList.get(position).getProductName());
        String nameAddress = productList.get(position).getDisplayName();
        String lat = productList.get(position).getLat();
        String lon = productList.get(position).getLon();
//        String cartId = String.valueOf(productList.get(position).getCategoryId());


//        DataManager.getInstance().setDefaultCartId(String.valueOf(productList.get(0).getCategoryId()));

//        if (type.equals("")){
//            holder.textType.setVisibility(View.GONE);
//        }else{
//            holder.textType.setVisibility(View.VISIBLE);
//            holder.textType.setText(type);
//        }
        holder.address.setText(nameAddress);

        holder.containerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                variant = position;
                onChoose.onChoose(nameAddress, lat, lon);
                Log.e("cek data", String.valueOf(variant));

                notifyDataSetChanged();
            }
        });

//        if (variant == position){
//            holder.viewTab.setBackgroundResource(R.color.red_text);
//            holder.tvCartService.setTextColor(ContextCompat.getColor(mContext, R.color.black));
//        }else {
//            holder.viewTab.setBackgroundResource(R.color.grey_view);
//            holder.tvCartService.setTextColor(ContextCompat.getColor(mContext, R.color.grey_500));
//        }
    }

    @Override
    public int getItemCount() {
        return (productList != null ? productList.size() : 0);
    }

    public void setData(List<Maps> data) {
        this.productList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_address)
        LinearLayout containerAddress;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.address)
        TextView address;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
