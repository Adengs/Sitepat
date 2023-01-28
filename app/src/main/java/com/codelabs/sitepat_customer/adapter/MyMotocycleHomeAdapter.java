package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.viewmodel.MyMotocycle;
import com.codelabs.sitepat_customer.viewmodel.MyMotocycleHomeSelected;
import com.codelabs.sitepat_customer.viewmodel.MyMotocycleSelected;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMotocycleHomeAdapter extends RecyclerView.Adapter<MyMotocycleHomeAdapter.viewHolder>{
    private Context mContext;
    private List<MyMotocycle.ItemsEntity> motocycleList;
    private int onClick = -1;

    public MyMotocycleHomeAdapter(Context context){
        this.mContext = context;
        this.motocycleList = new ArrayList<>();
    }


    @NonNull
    @Override
    public MyMotocycleHomeAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mymotocycle , parent,false);
        return new MyMotocycleHomeAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMotocycleHomeAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {
        String image = motocycleList.get(position).getImage();
        String pet_id = String.valueOf(motocycleList.get(position).getCarid());

        if (DataManager.getInstance().getPositionMotocycle() == -1){
            holder.containerMotocycle.setBackgroundResource(R.drawable.ic_mybike_outline);
        }else{
            onClick = DataManager.getInstance().getPositionMotocycle();
            String petId = DataManager.getInstance().getPetId();
            int positionM = DataManager.getInstance().getPositionMotocycle();
            EventBus.getDefault().post(new MyMotocycleHomeSelected(petId, positionM));
        }

        if (image.isEmpty()){

        }else {
            Picasso.get()
                    .load(image)
                    .into(holder.ivMotocycle);
        }

        holder.containerMotocycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick = position;
                EventBus.getDefault().post(new MyMotocycleHomeSelected(pet_id, position));
                DataManager.getInstance().setPetId(pet_id);
                DataManager.getInstance().setPositionMotocycle(-1);
                notifyDataSetChanged();
            }
        });
        holder.tvPlatNo.setText(motocycleList.get(position).getCarplatenumber());
        holder.tvTypeMotocycle.setText(motocycleList.get(position).getBrand().getBrandname());
        holder.tvYearMotocycle.setText(motocycleList.get(position).getCaryear());

        if (onClick == position){
            holder.containerMotocycle.setBackgroundResource(R.drawable.ic_mybike_orange);
        }else {
            holder.containerMotocycle.setBackgroundResource(R.drawable.ic_mybike_outline);
        }
    }

    @Override
    public int getItemCount() {
        return (motocycleList != null ? motocycleList.size() : 0);
    }

    public void setData(List<MyMotocycle.ItemsEntity> data) {
        this.motocycleList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.iv_motocycle)
        AppCompatImageView ivMotocycle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_plat_no)
        AppCompatTextView tvPlatNo;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_type_motocycle)
        AppCompatTextView tvTypeMotocycle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_year_motocycle)
        AppCompatTextView tvYearMotocycle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_motocycle)
        RelativeLayout containerMotocycle;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
