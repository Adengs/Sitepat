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
import com.codelabs.sitepat_customer.viewmodel.Data;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaint;

import java.util.ArrayList;
import java.util.List;

public class TypeComplaintAdapter extends ArrayAdapter<TypeComplaint.ItemsTypeComplaint> {

    private Context mContext;
    public List<TypeComplaint.ItemsTypeComplaint> mItems;

    public TypeComplaintAdapter(Context context, List<TypeComplaint.ItemsTypeComplaint> mItems){
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

    private View initView(int position, View convertView, ViewGroup parent) {
//        AppCompatTextView tvTypeComplaint = (AppCompatTextView) convertView.findViewById(R.id.tv_type_complaint);
//        TypeComplaint.ItemsTypeComplaint currentItem = getItem(position);

        if (convertView == null) {
//            AppCompatTextView tvTypeComplaint = (AppCompatTextView) convertView.findViewById(R.id.tv_type_complaint);
//            tvTypeComplaint.setText(R.string.choose_type_complaint);
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_type_complaint, parent, false
            );
        }

        AppCompatTextView tvTypeComplaint = convertView.findViewById(R.id.tv_type_complaint);
        TypeComplaint.ItemsTypeComplaint currentItem = getItem(position);

        if (currentItem != null) {
            tvTypeComplaint.setText(currentItem.getName());
//            tvTypeComplaint.setText(getItem(0).getName());
//            tvTypeComplaint.setText(R.string.choose_type_complaint);
//            if (DataManager.getInstance().getSelectD().equals("")){
//                tvTypeComplaint.setText(getItem(-1).getName());
//                tvTypeComplaint.setText(R.string.choose_type_complaint);
//                tvTypeComplaint.setTextColor(mContext.getResources().getColor(R.color.grayFont));
//                tvTypeComplaint.setTypeface(ResourcesCompat.getFont(mContext, R.font.rajdhani_regular));
//            }else{
//                tvTypeComplaint.setText(currentItem.getName());
//                tvTypeComplaint.setTextColor(mContext.getResources().getColor(R.color.black));
//                tvTypeComplaint.setTypeface(ResourcesCompat.getFont(mContext, R.font.rajdhani_bold));
//            }
        }

        return convertView;
    }

   @Override
    public TypeComplaint.ItemsTypeComplaint getItem(int position) {
        return super.getItem(position);
   }
}
