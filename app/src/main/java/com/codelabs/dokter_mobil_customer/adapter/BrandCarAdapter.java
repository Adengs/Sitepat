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
import com.codelabs.dokter_mobil_customer.viewmodel.BrandCar;
import com.codelabs.dokter_mobil_customer.viewmodel.TypeComplaint;

import java.util.ArrayList;
import java.util.List;

public class BrandCarAdapter extends ArrayAdapter<BrandCar.ItemsBrandCar> {

    private Context mContext;
    private List<BrandCar.ItemsBrandCar> mItems;

    public BrandCarAdapter(Context context, List<BrandCar.ItemsBrandCar> mItems) {
        super(context, 0, mItems);
        this.mItems = mItems;
        this.mContext = context;
        this.mItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_type_complaint, parent, false
            );
        }

        AppCompatTextView tvTypeComplaint = convertView.findViewById(R.id.tv_type_complaint);
        BrandCar.ItemsBrandCar currentItem = getItem(position);

        if (currentItem != null) {
            tvTypeComplaint.setText(currentItem.getBrandName());
        }

        return convertView;
    }

    @Nullable
    @Override
    public BrandCar.ItemsBrandCar getItem(int position) {
        return super.getItem(position);
    }
}
