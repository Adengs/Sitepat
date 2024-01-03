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
import com.codelabs.sitepat_customer.page.shop.DetailProduct;
import com.codelabs.sitepat_customer.viewmodel.NewProduct;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.viewHolder> {

    private Context mContext;
    private List<NewProduct.ItemsEntity> newProductList;

    public NewProductAdapter(Context context){
        this.mContext = context;
        this.newProductList = new ArrayList<>();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_new_product, parent,false);
        return new NewProductAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String formatRupiah = rupiah.format(new BigDecimal(newProductList.get(position).getProductPrice()));
        String image = newProductList.get(position).getProductImage();

        if (image.isEmpty()){

        }else {
            Glide.with(mContext)
                    .load(image)
                    .thumbnail(0.25f)
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
//                    .dontTransform()
                    .into(holder.ivNewProduct);
        }

        holder.containerNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailProduct.class);
                intent.putExtra("product_id", newProductList.get(position).getProductId());
                intent.putExtra("product_name", newProductList.get(position).getProductName());
                intent.putExtra("product_sku", newProductList.get(position).getSku());
                intent.putExtra("tax_rate", newProductList.get(position).getTaxRate());
                intent.putExtra("category_id", newProductList.get(position).getCategoryId());
                intent.putExtra("category_name", newProductList.get(position).getCategory().getCategoryName());
                intent.putExtra("product_price", newProductList.get(position).getProductPrice());
                intent.putExtra("product_qty", newProductList.get(position).getProductQty());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        holder.tvTitleProduct.setText(newProductList.get(position).getProductName());
        holder.tvSubProduct.setText(newProductList.get(position).getCategory().getCategoryName());
        holder.tvPriceProduct.setText(formatRupiah.replace(",00","").replace("Rp",""));
//        holder.tvPriceProduct.setText(newProductList.get(position).getProductPrice());
    }

    @Override
    public int getItemCount() {
        return (newProductList != null ? newProductList.size() : 0);
    }

    public void setData(List<NewProduct.ItemsEntity> data) {
        this.newProductList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.iv_new_product)
        AppCompatImageView ivNewProduct;
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
        @BindView(R.id.container_new_product)
        CardView containerNewProduct;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
