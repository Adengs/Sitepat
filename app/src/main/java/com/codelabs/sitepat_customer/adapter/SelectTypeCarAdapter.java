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
import com.codelabs.sitepat_customer.viewmodel.BrandTypesCar;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectTypeCarAdapter extends RecyclerView.Adapter<SelectTypeCarAdapter.viewHolder> {

    private Context mContext;
    private List<BrandTypesCar.ItemsBrandType> brandTypeList;

    public SelectTypeCarAdapter(Context context) {
        this.mContext = context;
        this.brandTypeList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SelectTypeCarAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_brand_car,parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SelectTypeCarAdapter.viewHolder holder, int position) {
        holder.tvType.setText(brandTypeList.get(position).getTypeName());
        holder.containerSelectType.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddNewCarActivity.class);
            intent.putExtra("type_name", brandTypeList.get(position).getTypeName());
            intent.putExtra("type_id", brandTypeList.get(position).getTypeId());
            ((Activity)mContext).setResult(Activity.RESULT_OK,intent);
            ((Activity)mContext).finish();
        });
    }

    @Override
    public int getItemCount() {
        return (brandTypeList != null ? brandTypeList.size() : 0);
    }

    public void setData(List<BrandTypesCar.ItemsBrandType> data) {
        this.brandTypeList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_brand)
        AppCompatTextView tvType;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_select_brand)
        RelativeLayout containerSelectType;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
