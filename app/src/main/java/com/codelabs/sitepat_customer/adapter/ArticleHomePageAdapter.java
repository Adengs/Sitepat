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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.page.article.ArticleDetailActivity;
import com.codelabs.sitepat_customer.viewmodel.Articles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleHomePageAdapter extends RecyclerView.Adapter<ArticleHomePageAdapter.viewHolder> {

    private Context mContext;
    private List<Articles.ItemsArticles> articlesList;

    public ArticleHomePageAdapter(Context context) {
        this.mContext = context;
        this.articlesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ArticleHomePageAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_article_homepage, parent,false);
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
    public void onBindViewHolder(@NonNull ArticleHomePageAdapter.viewHolder holder, int position) {
        Glide.with(mContext)
                .load(articlesList.get(position).getImageUrl())
                .thumbnail(0.25f)
                .diskCacheStrategy( DiskCacheStrategy.ALL )
//                .dontTransform()
                .into(holder.ivArticle);

        holder.containerArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                intent.putExtra("article_id", articlesList.get(position).getArticleId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        holder.tvTitleArticle.setText(articlesList.get(position).getTitle());
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
