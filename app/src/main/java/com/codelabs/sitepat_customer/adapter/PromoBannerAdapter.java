package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.page.promo.PromoDetailActivity;
import com.codelabs.sitepat_customer.viewmodel.Promo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromoBannerAdapter extends RecyclerView.Adapter<PromoBannerAdapter.viewHolder> {

    private Context mContext;
    private List<Promo.ItemsPromo> promoList;

    public PromoBannerAdapter(Context mContext) {
        this.mContext = mContext;
        this.promoList = new ArrayList<>();
    }

    public void setData(List<Promo.ItemsPromo> items) {
        promoList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PromoBannerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_promo, parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PromoBannerAdapter.viewHolder holder, int position) {
        Glide.with(mContext)
                .load(promoList.get(position).getImage())
                .diskCacheStrategy( DiskCacheStrategy.ALL )
//                .dontTransform()
                .into(holder.ivPromo);

        holder.ivPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PromoDetailActivity.class);
                intent.putExtra("promo_id", promoList.get(position).getPromo_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (promoList != null ? promoList.size() : 0);
    }

    public static class viewHolder extends  RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.iv_promo)
        AppCompatImageView ivPromo;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
