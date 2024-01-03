package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.akiniyalocts.pagingrecycler.PagingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.page.shop.DetailProduct;
import com.codelabs.sitepat_customer.viewmodel.NewProduct;
import com.codelabs.sitepat_customer.viewmodel.Product;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder> {
    private Context mContext;
    public List<Product.ItemsEntity> productList;
    private Activity activity;
//    private static final int LOADING = 0;
//    private static final int ITEM = 1;
//    private boolean isLoadingAdded = false;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public ProductAdapter(Context context){
        this.mContext = context;
        this.productList = new ArrayList<>();
    }

//    public ProductAdapter(Activity activity, List<Product.ItemsEntity> productList){
//        this.activity = activity;
//        this.productList = productList;
//    }

//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent,false);
//        return new ProductAdapter.viewHolder(view);
//        RecyclerView.ViewHolder viewHolder = null;
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//
//        switch (viewType) {
//            case ITEM:
//                View viewItem = inflater.inflate(R.layout.item_product, parent, false);
//                viewHolder = new MovieViewHolder(viewItem);
//                break;
//            case LOADING:
//                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
//                viewHolder = new LoadingViewHolder(viewLoading);
//                break;
//        }
//        return viewHolder;


//        if (viewType == VIEW_TYPE_ITEM) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
//            return new ItemViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
//            return new LoadingViewHolder(view);
//        }
//    }


    @NonNull
    @Override
    public ProductAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent, false);
        return new ProductAdapter.viewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
//        if (viewHolder instanceof ItemViewHolder) {
//
//            populateItemRows((ItemViewHolder) viewHolder, position);
//        } else if (viewHolder instanceof LoadingViewHolder) {
//            showLoadingView((LoadingViewHolder) viewHolder, position);
//        }
//    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String formatRupiah = rupiah.format(new BigDecimal(productList.get(position).getProductPrice()));
        String image = productList.get(position).getProductImage();

        if (image.isEmpty()){

        }else {
            Glide.with(mContext)
                    .load(image)
                    .thumbnail(0.25f)
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
//                    .dontTransform()
                    .into(holder.ivProduct);
        }

        holder.containerProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailProduct.class);
                intent.putExtra("product_id", productList.get(position).getProductId());
                intent.putExtra("product_name", productList.get(position).getProductName());
                intent.putExtra("product_sku", productList.get(position).getSku());
                intent.putExtra("tax_rate", productList.get(position).getTaxRate());
                intent.putExtra("category_id", productList.get(position).getCategoryId());
                intent.putExtra("category_name", productList.get(position).getCategory().getCategoryName());
                intent.putExtra("product_price", productList.get(position).getProductPrice());
                intent.putExtra("product_qty", productList.get(position).getProductQty());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        holder.tvTitleProduct.setText(productList.get(position).getProductName());
        holder.tvSubProduct.setText(productList.get(position).getCategory().getCategoryName());
        holder.tvPriceProduct.setText(formatRupiah.replace(",00","").replace("Rp",""));

    }

    @Override
    public int getItemCount() {
//        return (productList == null ? 0 : productList.size());
        return (productList != null ? productList.size() : 0);
    }

    public void setData(List<Product.ItemsEntity> data) {
        this.productList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.iv_product)
        AppCompatImageView ivProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_title_product)
        AppCompatTextView tvTitleProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_sub_product)
        AppCompatTextView tvSubProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_price_product)
        AppCompatTextView tvPriceProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_product)
        CardView containerProduct;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


//    @Override
//    public int getItemViewType(int position) {
//        return productList.get(position) == null? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
//    }
//
//    private class ItemViewHolder extends RecyclerView.ViewHolder {
//
//        AppCompatTextView tvTitleProduct;
//        AppCompatTextView tvSubProduct;
//        AppCompatTextView tvPriceProduct;
//        AppCompatImageView ivProduct;
//        CardView containerProduct;
//
//        public ItemViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            tvTitleProduct = itemView.findViewById(R.id.tv_title_product);
//            tvSubProduct = itemView.findViewById(R.id.tv_sub_product);
//            tvPriceProduct = itemView.findViewById(R.id.tv_price_product);
//            ivProduct = itemView.findViewById(R.id.iv_product);
//            containerProduct = itemView.findViewById(R.id.container_product);
//        }
//    }
//
//    private class LoadingViewHolder extends RecyclerView.ViewHolder {
//
//        ProgressBar progressBar;
//
//        public LoadingViewHolder(@NonNull View itemView) {
//            super(itemView);
//            progressBar = itemView.findViewById(R.id.progressBar);
//        }
//    }
//
//    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
//        //ProgressBar would be displayed
//
//    }
//
//    private void populateItemRows(ItemViewHolder viewHolder, int position) {
//
////        String item = String.valueOf(productList.get(position));
////        viewHolder.tvItem.setText(item);
//
//        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
//        String formatRupiah = rupiah.format(new BigDecimal(productList.get(position).getProductPrice()));
//        String image = productList.get(position).getProductImage();
//
//        if (image.isEmpty()){
//
//        }else {
//            Picasso.get()
//                    .load(image)
//                    .into(viewHolder.ivProduct);
//        }
//
//        viewHolder.containerProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, DetailProduct.class);
//                intent.putExtra("product_id", productList.get(position).getProductId());
//                intent.putExtra("product_name", productList.get(position).getProductName());
//                intent.putExtra("product_sku", productList.get(position).getSku());
//                intent.putExtra("tax_rate", productList.get(position).getTaxRate());
//                intent.putExtra("category_id", productList.get(position).getCategoryId());
//                intent.putExtra("category_name", productList.get(position).getCategory().getCategoryName());
//                intent.putExtra("product_price", productList.get(position).getProductPrice());
//                intent.putExtra("product_qty", productList.get(position).getProductQty());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
//            }
//        });
//        viewHolder.tvTitleProduct.setText(productList.get(position).getProductName());
//        viewHolder.tvSubProduct.setText(productList.get(position).getCategory().getCategoryName());
//        viewHolder.tvPriceProduct.setText(formatRupiah.replace(",00","").replace("Rp",""));
//
//    }
}
