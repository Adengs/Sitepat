package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.page.shop.DetailProduct;
import com.codelabs.sitepat_customer.viewmodel.CartProduct;
import com.codelabs.sitepat_customer.viewmodel.Product;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.viewHolder> {
    private Context mContext;
    public List<CartProduct.ItemsEntity> productList;
    public OnPlus onPlus;
    public OnMin onMin;
    public OnTrash onTrash;

    public CartProductAdapter(Context context){
        this.mContext = context;
        this.productList = new ArrayList<>();
    }

    public interface OnPlus{
        void onPlus(int idProduct, String productName, String productSku, String taxtRate, int categoryId, String categoryName, int productPrice, int produckQty, int siteId);
    }

    public interface OnMin{
        void onMin(int idProduct, String productName, String productSku, String taxtRate, int categoryId, String categoryName, int productPrice, int produckQty, int siteId);
    }

    public interface OnTrash{
        void onTrash(int idProduct, int siteId);
    }

    @NonNull
    @Override
    public CartProductAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_product_cart, parent,false);
        return new CartProductAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductAdapter.viewHolder holder, int position) {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String formatRupiah = rupiah.format(new BigDecimal(productList.get(position).getGrandTotal()));
        String image = productList.get(position).getProductImage();

        int idProduct = productList.get(position).getProductId();
        String productName = productList.get(position).getProductName();
        String productSku = productList.get(position).getProductSku();
        String taxtRate = productList.get(position).getTaxRate();
        int categoryId = productList.get(position).getCategoryId();
        String categoryName = productList.get(position).getCategoryName();
        int productPrice = Integer.parseInt(productList.get(position).getProductPrice().replace(".00",""));
        int produckQty = productList.get(position).getProductQty();
        int siteId = productList.get(position).getSiteId();

        if (image.isEmpty()){

        }else {
            Picasso.get()
                    .load(image)
                    .into(holder.imgProduct);
        }

        holder.containerProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlus.onPlus(idProduct, productName, productSku, taxtRate, categoryId, categoryName, productPrice, produckQty, siteId);
                notifyDataSetChanged();
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMin.onMin(idProduct, productName, productSku, taxtRate, categoryId, categoryName, productPrice, produckQty, siteId);
                notifyDataSetChanged();
            }
        });

        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTrash.onTrash(idProduct, siteId);
            }
        });

        holder.nameProduct.setText(productList.get(position).getProductName());
//        holder.unitProduct.setText(productList.get(position).);
        holder.price.setText(formatRupiah.replace(",00","").replace("Rp",""));
        holder.quantity.setText(String.valueOf(productList.get(position).getProductQty()));
//        holder.tvPriceProduct.setText(newProductList.get(position).getProductPrice());
    }

    @Override
    public int getItemCount() {
        return (productList != null ? productList.size() : 0);
    }

    public void setData(List<CartProduct.ItemsEntity> data) {
        this.productList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.img_product)
        AppCompatImageView imgProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.name_product)
        AppCompatTextView nameProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.unit_product)
        AppCompatTextView unitProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.price)
        AppCompatTextView price;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.quantity)
        AppCompatTextView quantity;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.btn_minus)
        AppCompatImageView btnMinus;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.btn_plus)
        AppCompatImageView btnPlus;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.trash)
        AppCompatImageView trash;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_product)
        ConstraintLayout containerProduct;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
