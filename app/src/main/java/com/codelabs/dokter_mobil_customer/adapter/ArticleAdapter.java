package com.codelabs.dokter_mobil_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.page.article.ArticleDetailActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.Articles;
import com.codelabs.dokter_mobil_customer.viewmodel.ArticlesDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.viewHolder> {

    private Context mContext;
    private List<Articles.ItemsArticles> articlesList;

    public ArticleAdapter(Context context) {
        this.mContext = context;
        this.articlesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ArticleAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false);
        return new viewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (articlesList != null ? articlesList.size() : 0);
    }

    public void setData(List<Articles.ItemsArticles> data) {
        this.articlesList = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.viewHolder holder, int position) {
        if (articlesList.get(position).getIsVideo() == 1) {
            holder.containerArticle.setVisibility(View.GONE);
            holder.containerVideo.setVisibility(View.VISIBLE);
        } else {
            holder.containerArticle.setVisibility(View.VISIBLE);
            holder.containerVideo.setVisibility(View.GONE);
        }
        Picasso.get()
                .load(articlesList.get(position).getImageUrl())
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

        holder.tvTitleArticle.setText(articlesList.get(position).getTitle());
        holder.tvDateArticle.setText(RecentUtils.formatDateToDateDMY(articlesList.get(position).getCreatedAt()));
        holder.containerArticleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                intent.putExtra("article_id", articlesList.get(position).getArticleId());
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
