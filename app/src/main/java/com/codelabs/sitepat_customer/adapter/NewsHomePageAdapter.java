package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.page.news.NewsDetailActivity;
import com.codelabs.sitepat_customer.utils.RecentUtils;
import com.codelabs.sitepat_customer.viewmodel.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsHomePageAdapter extends RecyclerView.Adapter<NewsHomePageAdapter.viewHolder> {

    private Context mContext;
    private List<News.ItemsNews> newsList;

    public NewsHomePageAdapter(Context context) {
        this.mContext = context;
        this.newsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsHomePageAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_article_homepage, parent,false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NewsHomePageAdapter.viewHolder holder, int position) {
        Picasso.get()
                .load(newsList.get(position).getImage())
                .into(holder.ivArticle);

        holder.containerArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("news_id", newsList.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        holder.tvTitleArticle.setText(RecentUtils.fromHtml(newsList.get(position).getContent()));
    }


    @Override
    public int getItemCount() {
        return (newsList != null ? newsList.size() : 0);
    }

    public void setData(List<News.ItemsNews> data) {
        this.newsList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.iv_article)
        AppCompatImageView ivArticle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_title_article)
        AppCompatTextView tvTitleArticle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_article)
        CardView containerArticle;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
