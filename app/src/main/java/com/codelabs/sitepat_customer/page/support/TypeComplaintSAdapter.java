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
import com.codelabs.sitepat_customer.adapter.ProvincieAdapter;
import com.codelabs.sitepat_customer.viewmodel.Province;
import com.codelabs.sitepat_customer.viewmodel.ProvincieSelected;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaint;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaintSelected;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypeComplaintSAdapter extends RecyclerView.Adapter<TypeComplaintSAdapter.viewHolder> {
    private final Context mContext;
    private List<TypeComplaint.ItemsTypeComplaint> typeComplaintList;
    public OnClick onClick;

    public interface OnClick{
        void onClick(Integer id);
    }

    public TypeComplaintSAdapter(Context context) {
        this.mContext = context;
        this.typeComplaintList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TypeComplaintSAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_provincie, parent, false);
        return new TypeComplaintSAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeComplaintSAdapter.viewHolder holder, int position) {

        Integer id = typeComplaintList.get(position).getId();
        String name = typeComplaintList.get(position).getName();
        holder.tvProvincie.setText(name);

        holder.containerProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: " + id );
                EventBus.getDefault().post(new TypeComplaintSelected(name, id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (typeComplaintList != null ? typeComplaintList.size() : 0);
    }

    public void setData(List<TypeComplaint.ItemsTypeComplaint> data) {
        this.typeComplaintList = data;
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
