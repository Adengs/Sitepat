package com.codelabs.dokter_mobil_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.page.article.ArticleDetailActivity;
import com.codelabs.dokter_mobil_customer.viewmodel.Articles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleHomeVerticalAdapter extends RecyclerView.Adapter<ArticleHomeVerticalAdapter.viewHolder> {

    private Context mContext;
    private List<Articles.ItemsArticles> articlesList;

    public ArticleHomeVerticalAdapter(Context context) {
        this.mContext = context;
        this.articlesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ArticleHomeVerticalAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_article_vertical_homepage,parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHomeVerticalAdapter.viewHolder holder, int position) {
        Picasso.get()
                .load(articlesList.get(position).getImageUrl())
                .into(holder.ivArticle);

        holder.ivArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                intent.putExtra("article_id", articlesList.get(position).getArticleId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        holder.tvTitleArticle.setText(articlesList.get(position).getTitle());
        holder.tvDescArticle.setText(articlesList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return (articlesList != null ? articlesList.size() : 0);
    }

    public void setData(List<Articles.ItemsArticles> data) {
        this.articlesList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.img_article_list)
        AppCompatImageView ivArticle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_title_article)
        AppCompatTextView tvTitleArticle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_desc_article)
        AppCompatTextView tvDescArticle;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
