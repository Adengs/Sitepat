package com.codelabs.sitepat_customer.page.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.OutletAdapter;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.page.outlet.OutletDetailActivity;
import com.codelabs.sitepat_customer.utils.RecentUtils;
import com.codelabs.sitepat_customer.viewmodel.MyOrder;
import com.codelabs.sitepat_customer.viewmodel.Outlet;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

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

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.viewHolder> {
    private final Context mContext;
    public List<MyOrder.Data> orderList;

    public MyOrderAdapter(Context context) {
        this.mContext = context;
        this.orderList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyOrderAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_myorders, parent, false);
        return new MyOrderAdapter.viewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.viewHolder holder, int position) {
        String image = orderList.get(position).getOrders().get(0).getItems().get(0).getItemImage();
        String nameProduct = orderList.get(position).getOrders().get(0).getItems().get(0).getItemName();
        String qty = String.valueOf(orderList.get(position).getOrders().get(0).getItems().get(0).getItemQty());

//        String priceProduct = String.valueOf(orderList.get(position).getOrders().get(position).getItems().get(position).getItemPrice());

        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String formatRupiah = rupiah.format(new BigDecimal(orderList.get(position).getTotalAmount()));

        if (orderList.get(position).getTransactionType().equals("Service")){
            holder.imgType.setImageResource(R.drawable.ic_setting);
        }else{
            holder.imgType.setImageResource(R.drawable.ic_shop);
        }

//        RecentUtils.formatDateToDateDMY
        holder.tvType.setText(orderList.get(position).getTransactionType());
        holder.date_type.setText(formatDateToDateDMY(orderList.get(position).getCreatedAt())); //change type data
        holder.tvStatus.setText(orderList.get(position).getTransactionStatus());
        if (orderList.get(position).getTransactionStatus().equals("On Going")){
            holder.cardStatus.setCardBackgroundColor(mContext.getResources().getColor(R.color.green_text));
        }else{
            holder.cardStatus.setCardBackgroundColor(mContext.getResources().getColor(R.color.grey_500));
            holder.tvStatus.setText("Finish");
        }

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
//                    .dontTransform()
                    .into(holder.imgProduct);
        }

        holder.tvProduct.setText(nameProduct);
        holder.qtyProduct.setText(qty);
        holder.priceProduct.setText(formatRupiah.replace("Rp","").replaceAll("\\,\\d+$", ""));


        holder.containerMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "Detail On Develop :(", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, DetailOrderActivity.class);
                intent.putExtra("transaction_id", orderList.get(position).getTransactionId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
//                DataManager.getInstance().setImageOutlet(orderList.get(position).getSiteimage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (orderList != null ? orderList.size() : 0);
    }

    public void setData(List<MyOrder.Data> data) {
        this.orderList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.img_type)
        ShapeableImageView imgType;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.text_type)
        TextView tvType;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.date_type)
        TextView date_type;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.card_status)
        CardView cardStatus;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.text_status)
        TextView tvStatus;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.img_product)
        ShapeableImageView imgProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.text_product)
        TextView tvProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.qty_product)
        TextView qtyProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.price_product)
        TextView priceProduct;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_myorder)
        RelativeLayout containerMyOrder;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public static String formatDateToDateDMY(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat toFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;
    }

}
