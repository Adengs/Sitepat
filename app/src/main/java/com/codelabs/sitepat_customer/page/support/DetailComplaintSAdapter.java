package com.codelabs.sitepat_customer.page.support;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.viewmodel.DetailComplaintSelected;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaint;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaintDetail;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaintSelected;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailComplaintSAdapter extends RecyclerView.Adapter<DetailComplaintSAdapter.viewHolder>{
    private final Context mContext;
    private List<TypeComplaintDetail.DataComplaintDetail> detailComplaintList;
    public OnClick onClick;

    public interface OnClick{
        void onClick(Integer id);
    }

    public DetailComplaintSAdapter(Context context) {
        this.mContext = context;
        this.detailComplaintList = new ArrayList<>();
    }

    @NonNull
    @Override
    public DetailComplaintSAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_provincie, parent, false);
        return new DetailComplaintSAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailComplaintSAdapter.viewHolder holder, int position) {

        Integer id = detailComplaintList.get(position).getId();
        String name = detailComplaintList.get(position).getName();
        holder.tvProvincie.setText(name);

        holder.containerProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: " + id );
                EventBus.getDefault().post(new DetailComplaintSelected(name, id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (detailComplaintList != null ? detailComplaintList.size() : 0);
    }

    public void setData(List<TypeComplaintDetail.DataComplaintDetail> data) {
        this.detailComplaintList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_provincie)
        ConstraintLayout containerProvince;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.text)
        TextView tvProvincie;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
