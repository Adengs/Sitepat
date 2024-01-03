package com.codelabs.sitepat_customer.page.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.viewmodel.DetailMyOrder;
import com.codelabs.sitepat_customer.viewmodel.MyOrder;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.viewHolder>{
    private final Context mContext;
    public List<DetailMyOrder.ItemsEntity> detailOrderList;

    public DetailOrderAdapter(Context context) {
        this.mContext = context;
        this.detailOrderList = new ArrayList<>();
    }

    @NonNull
    @Override
    public DetailOrderAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_detail_myorder, parent, false);
        return new DetailOrderAdapter.viewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull DetailOrderAdapter.viewHolder holder, int position) {
        String image = detailOrderList.get(position).getProductImage();
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String formatRupiah = rupiah.format(new BigDecimal(detailOrderList.get(position).getItemPrice()).longValue());

        if (image != null) {
            if (image.equals("")) {
                holder.imgProduct.setImageResource(R.drawable.img_dummy_myorder);
            } else {
                Glide.with(mContext)
                        .load(image)
                        .centerCrop()
                        .placeholder(R.drawable.background_outlet_status)
                        .error(R.drawable.background_outlet_status)
                        .thumbnail(0.25f)
                        .diskCacheStrategy( DiskCacheStrategy.ALL )
//                        .dontTransform()
                        .into(holder.imgProduct);
            }
        }

        holder.nameProduct.setText(detailOrderList.get(position).getItemName());
        holder.qtyProduct.setText(String.valueOf(detailOrderList.get(position).getItemQty()));
        holder.priceProduct.setText(formatRupiah.replace("Rp","").replace(",00",""));
    }

    @Override
    public int getItemCount() {
        return (detailOrderList != null ? detailOrderList.size() : 0);
    }

    public void setData(List<DetailMyOrder.ItemsEntity> data) {
        this.detailOrderList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.img_product)
        ShapeableImageView imgProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.name_product)
        TextView nameProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.quantity)
        TextView qtyProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.price)
        TextView priceProduct;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
