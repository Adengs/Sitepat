package com.codelabs.dokter_mobil_customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.viewmodel.Promo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PromoAdapter extends PagerAdapter {

    private Context mContext;
    private List<Promo.ItemsPromo> promoList = new ArrayList<>();

    public PromoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<Promo.ItemsPromo> getPromoList() {
        return promoList;
    }

    public void setData(List<Promo.ItemsPromo> items) {
        promoList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return promoList.size();
    }



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View promo = inflater.inflate(R.layout.item_viewpager_promo, container, false);
        assert promo != null;

        AppCompatImageView imgPromo = promo.findViewById(R.id.iv_promo);
//        ProgressBar progressBar = promo.findViewById(R.id.progress_bar);
        Picasso.get()
                .load(promoList.get(position).getImage())
                .fit().centerCrop()
                .into(imgPromo, new Callback() {
                    @Override
                    public void onSuccess() {
//                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
//                        progressBar.setVisibility(View.GONE);
                    }
                });

        container.addView(promo,0);
        return promo;
    }

}
