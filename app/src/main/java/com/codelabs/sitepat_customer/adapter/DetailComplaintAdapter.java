package com.codelabs.sitepat_customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaintDetail;

import java.util.ArrayList;
import java.util.List;

public class DetailComplaintAdapter extends ArrayAdapter<TypeComplaintDetail.DataComplaintDetail> {

    private Context mContext;
    private List<TypeComplaintDetail.DataComplaintDetail> mItems;

    public DetailComplaintAdapter(Context context, List<TypeComplaintDetail.DataComplaintDetail> mItems){
        super(context, 0, mItems);
        this.mItems = mItems;
        this.mContext = context;
        this.mItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView (position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView (position, convertView, parent);
    }

    private View initView(int position, View converView, ViewGroup parent) {
//        AppCompatTextView tvDetailComplaint = (AppCompatTextView) converView.findViewById(R.id.tv_detail_complaint);
//        TypeComplaintDetail.DataComplaintDetail currentItem = getItem(position);

        if (converView == null) {
//            tvDetailComplaint.setText(R.string.choose_detail_complaint);
            converView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_detail_complaint, parent, false
            );
        }

        AppCompatTextView tvDetailComplaint = converView.findViewById(R.id.tv_detail_complaint);
        TypeComplaintDetail.DataComplaintDetail currentItem = getItem(position);

        if (currentItem != null) {
            tvDetailComplaint.setText(currentItem.getName());
//            tvDetailComplaint.setText(R.string.choose_type_complaint);
//            if (DataManager.getInstance().getSelectD().equals("")){
//                tvDetailComplaint.setText(R.string.choose_type_complaint);
//                tvDetailComplaint.setTextColor(mContext.getResources().getColor(R.color.grayFont));
//                tvDetailComplaint.setTypeface(ResourcesCompat.getFont(mContext, R.font.rajdhani_regular));
//            }else{
//                tvDetailComplaint.setText(currentItem.getName());
//                tvDetailComplaint.setTextColor(mContext.getResources().getColor(R.color.black));
//                tvDetailComplaint.setTypeface(ResourcesCompat.getFont(mContext, R.font.rajdhani_bold));
//            }
        }

        return converView;
    }

    @Override
    public TypeComplaintDetail.DataComplaintDetail getItem(int position) {
        return super.getItem(position);
    }
}
