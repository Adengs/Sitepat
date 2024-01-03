package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.viewmodel.Maps;
import com.codelabs.sitepat_customer.viewmodel.PaymentMethod;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VirtualAccountAdapter extends RecyclerView.Adapter<VirtualAccountAdapter.viewHolder> {
    private Context mContext;
    public List<PaymentMethod.DataEntity> listVA;
    public int variant = 0;
    public int itemVA = -1;
    public OnItemSelected onItemSelected;

    public VirtualAccountAdapter(Context context) {
        this.mContext = context;
        this.listVA = new ArrayList<>();
    }

    public interface OnItemSelected {
        void onItemClick(PaymentMethod.DataEntity item);
    }

    public void OnClickSelected(OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public VirtualAccountAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_virtual_account, parent, false);
//        EventBus.getDefault().post(new ServiceSelected(String.valueOf(productList.get(0).getCategoryId())));
        return new VirtualAccountAdapter.viewHolder(view);
    }

    @SuppressLint({"RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull VirtualAccountAdapter.viewHolder holder, int position) {
        String imageBank = listVA.get(position).getPicture();
        String bankName = listVA.get(position).getName();

        if (imageBank.isEmpty()){

        }else {
            Glide.with(mContext)
                    .load(imageBank)
                    .thumbnail(0.25f)
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
//                    .dontTransform()
                    .into(holder.imgBank);
        }
        holder.nameBank.setText(bankName);
//        holder.rb.setChecked(listVA.get(position).isSelected());

        holder.containerVirtualAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                itemVA = position;
//                onChoose.onChoose(nameAddress, lat, lon);
                Log.e("cek data", String.valueOf(itemVA));

                notifyDataSetChanged();
            }
        });

//        if (itemVA == position) {
//            holder.rb.setChecked(true);
//        } else {
//            holder.rb.setChecked(false);
//        }

    }

    @Override
    public int getItemCount() {
        return (listVA != null ? listVA.size() : 0);
    }

    public void setData(List<PaymentMethod.DataEntity> data) {
        this.listVA = data;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_virtual_account)
        LinearLayout containerVirtualAccount;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.img_bank)
        AppCompatImageView imgBank;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.bank_name)
        AppCompatTextView nameBank;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.rb)
        AppCompatRadioButton rb;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            containerVirtualAccount.setOnClickListener(v -> {
//                listVA.get(getAdapterPosition()).setSelected(!listVA.get(getAdapterPosition()).isSelected());
//                notifyItemChanged(getAdapterPosition());
//                onItemSelected.onItemClick(listVA.get(getAdapterPosition()));
//            });
        }
    }
}