package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.viewmodel.TypeService;
import com.codelabs.sitepat_customer.viewmodel.TypeServiceSelected;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypeServiceAdapater extends RecyclerView.Adapter<TypeServiceAdapater.viewHolder> {
    private Context mContext;
    public List<TypeService.ItemsEntity> typeServiceList;
    public int typeService = -1;
    public int categoryService = -1;
    private OnItemCLickTypeService onItemCLickTypeService;


    public interface OnItemCLickTypeService{
        void onItemClick(TypeService.ItemsEntity item);
    }

    public void OnClickSelectedItem(OnItemCLickTypeService onItemCLickTypeService){
        this.onItemCLickTypeService = onItemCLickTypeService;
    }

    public TypeServiceAdapater(Context context){
        this.mContext = context;
        this.typeServiceList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TypeServiceAdapater.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_choose_service, parent,false);
        return new TypeServiceAdapater.viewHolder(view);
    }

    @SuppressLint({"RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull TypeServiceAdapater.viewHolder holder, int position) {
//        holder.textType.setText(productList.get(position).getProductName());
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String price = rupiah.format(new BigDecimal(typeServiceList.get(position).getRetailPrice()));
        String nameService = typeServiceList.get(position).getMedicalName();
        String desc = typeServiceList.get(position).getMedicalDesc();
//        int price = typeServiceList.get(position).getRetailPrice();

//        if (type.equals("")){
//            holder.textType.setVisibility(View.GONE);
//        }else{
//            holder.textType.setVisibility(View.VISIBLE);
//            holder.textType.setText(type);
//        }
        holder.tvServiceName.setText(nameService);
        holder.tvDescService.setText(desc);
        holder.tvPrice.setText(price.replace(",00","").replace("Rp",""));
        int priceNumber = typeServiceList.get(position).getRetailPrice();
        holder.cbTypeService.setChecked(typeServiceList.get(position).isSelected());

//        holder.containerChooseService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                typeService = position;
//                if (holder.cbTypeService.isChecked()){
//                    holder.cbTypeService.setChecked(false);
//                }else {
//                    holder.cbTypeService.setChecked(true);
//                    EventBus.getDefault().post(new TypeServiceSelected(nameService, desc, priceNumber));
//                }
//                EventBus.getDefault().post(new TypeServiceSelected(nameService, desc, priceNumber));
////                EventBus.getDefault().post(new CartSelected(type));
//
////                Log.e("cek_adapter", type);
//                notifyDataSetChanged();
//            }
//        });

        //single check
//        if (typeService == position && typeServiceList.get(position).getMedicalCategoryId() == categoryService){
//            holder.cbTypeService.setChecked(true);
//        }else {
//            holder.cbTypeService.setChecked(false);
//        }
    }

    @Override
    public int getItemCount() {
        return (typeServiceList != null ? typeServiceList.size() : 0);
    }

    public void setData(List<TypeService.ItemsEntity> data) {
        this.typeServiceList = data;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_choose_service)
        RelativeLayout containerChooseService;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_service_name)
        AppCompatTextView tvServiceName;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_desc_service)
        AppCompatTextView tvDescService;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_price)
        AppCompatTextView tvPrice;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.cb_type_service)
        CheckBox cbTypeService;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            containerChooseService.setOnClickListener(v -> {
                typeServiceList.get(getAdapterPosition()).setSelected(!typeServiceList.get(getAdapterPosition()).isSelected());
                notifyItemChanged(getAdapterPosition());
                onItemCLickTypeService.onItemClick(typeServiceList.get(getAdapterPosition()));

            });
        }
    }
}
