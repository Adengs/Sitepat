package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.page.account.AddNewCarActivity;
import com.codelabs.sitepat_customer.viewmodel.BrandCar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectBrandCarAdapter extends RecyclerView.Adapter<SelectBrandCarAdapter.viewHolder> {

    private final Context mContext;
    private List<BrandCar.ItemsBrandCar> brandCarList;

    public SelectBrandCarAdapter(Context context) {
        this.mContext = context;
        this.brandCarList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SelectBrandCarAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_brand_car, parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SelectBrandCarAdapter.viewHolder holder, int position) {
        holder.tvBrand.setText(brandCarList.get(position).getBrandName());
        holder.containerSelectBrand.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddNewCarActivity.class);
            intent.putExtra("brand_name", brandCarList.get(position).getBrandName());
            intent.putExtra("brand_id", brandCarList.get(position).getBrandId());
            ((Activity)mContext).setResult(Activity.RESULT_OK,intent);
            ((Activity)mContext).finish();
        });
    }


    @Override
    public int getItemCount() {
        return (brandCarList != null ? brandCarList.size() : 0);
    }

    public void setData(List<BrandCar.ItemsBrandCar> data) {
        this.brandCarList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_brand)
        AppCompatTextView tvBrand;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_select_brand)
        RelativeLayout containerSelectBrand;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
