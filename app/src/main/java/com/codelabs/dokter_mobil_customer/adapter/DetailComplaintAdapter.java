package com.codelabs.dokter_mobil_customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.viewmodel.TypeComplaintDetail;

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
        if (converView == null) {
            converView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_detail_complaint, parent, false
            );
        }

        AppCompatTextView tvDetailComplaint = converView.findViewById(R.id.tv_detail_complaint);
        TypeComplaintDetail.DataComplaintDetail currentItem = getItem(position);

        if (currentItem != null) {
            tvDetailComplaint.setText(currentItem.getName());
        }

        return converView;
    }

    @Override
    public TypeComplaintDetail.DataComplaintDetail getItem(int position) {
        return super.getItem(position);
    }
}
