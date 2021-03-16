package com.codelabs.dokter_mobil_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.viewmodel.AboutUs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.viewHolder> {

    private final Context mContext;
    private List<AboutUs.ItemsAbout> aboutList;

    public AboutUsAdapter(Context context) {
        this.mContext = context;
        this.aboutList = new ArrayList<>();
    }

    @NonNull
    @Override
    public AboutUsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.item_about_us, parent, false);
       return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutUsAdapter.viewHolder holder, int position) {
        holder.tvAbout.setText(aboutList.get(position).getTitle());
        holder.tvDescAbout.setText(aboutList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return (aboutList != null ? aboutList.size() : 0);
    }

    public void setData(List<AboutUs.ItemsAbout> data) {
        this.aboutList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_title_about)
        AppCompatTextView tvAbout;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_desc_about)
        AppCompatTextView tvDescAbout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
