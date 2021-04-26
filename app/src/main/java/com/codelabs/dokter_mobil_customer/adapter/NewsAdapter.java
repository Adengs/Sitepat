package com.codelabs.dokter_mobil_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.page.news.NewsDetailActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.News;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewHolder> {

    private Context mContext;
    private List<News.ItemsNews> newsList;

    public NewsAdapter(Context context) {
        this.mContext = context;
        this.newsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false);
        return new viewHolder (view);
    }

    @Override
    public int getItemCount() {
        return (newsList != null ? newsList.size() : 0);
    }

    public void setData(List<News.ItemsNews> data) {
        this.newsList = data;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.viewHolder holder, int position) {
       holder.containerVideo.setVisibility(View.GONE);

        Picasso.get()
                .load(newsList.get(position).getImage())
                .into(holder.ivArticle, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });

        holder.tvTitleArticle.setText(newsList.get(position).getTitle());
        holder.tvDateArticle.setText(RecentUtils.formatDateToDateDMY(newsList.get(position).getCreatedAt()));
        holder.containerArticleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("news_id", newsList.get(position).getId());
                mContext.startActivity(intent);
            }
        });
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
        LinearLayout containerArticle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_video)
        LinearLayout containerVideo;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_date_article)
        AppCompatTextView tvDateArticle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_article_list)
        RelativeLayout containerArticleList;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
