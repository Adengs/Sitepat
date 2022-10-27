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
    private List<Product.ItemsEntity> productList;

    public ProductAdapter(Context context){
        this.mContext = context;
        this.productList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent,false);
        return new ProductAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.viewHolder holder, int position) {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String formatRupiah = rupiah.format(new BigDecimal(productList.get(position).getProductPrice()));
        String image = productList.get(position).getProductImage();

        if (image.isEmpty()){

        }else {
            Picasso.get()
                    .load(image)
                    .into(holder.ivProduct);
        }

        holder.containerProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailProduct.class);
                intent.putExtra("product_id", productList.get(position).getProductId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        holder.tvTitleProduct.setText(productList.get(position).getProductName());
        holder.tvSubProduct.setText(productList.get(position).getCategory().getCategoryName());
        holder.tvPriceProduct.setText(formatRupiah.replace(",00","").replace("Rp",""));
//        holder.tvPriceProduct.setText(newProductList.get(position).getProductPrice());
    }

    @Override
    public int getItemCount() {
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
}
