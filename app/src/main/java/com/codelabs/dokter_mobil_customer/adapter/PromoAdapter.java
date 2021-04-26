package com.codelabs.dokter_mobil_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.page.promo.PromoDetailActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.Promo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.viewHolder> {

    private final Context mContext;
    private List<Promo.ItemsPromo> promoList;

    public PromoAdapter(Context context) {
        this.mContext = context;
        this.promoList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PromoAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_promo, parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PromoAdapter.viewHolder holder, int position) {
        Picasso.get()
                .load(promoList.get(position).getImage())
                .into(holder.imgPromo, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });
        holder.imgPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PromoDetailActivity.class);
                intent.putExtra("promo_id", promoList.get(position).getPromo_id());
                mContext.startActivity(intent);
            }
        });
        holder.tvPromoValid.setText(RecentUtils.formatDateToDateDM(promoList.get(position).getPeriod_start()) + " - " + RecentUtils.formatDateToDateDMY(promoList.get(position).getPeriod_end()));
    }


    @Override
    public int getItemCount() {
        return (promoList != null ? promoList.size() : 0);
    }

    public void setData(List<Promo.ItemsPromo> data) {
        this.promoList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.img_promo)
        AppCompatImageView imgPromo;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_promo_valid_date)
        AppCompatTextView tvPromoValid;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
