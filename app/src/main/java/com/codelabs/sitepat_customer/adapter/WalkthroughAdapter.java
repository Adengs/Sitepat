package com.codelabs.sitepat_customer.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.PagerAdapter;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.viewmodel.GetWalkThrough;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WalkthroughAdapter extends PagerAdapter {

    private final Context mContext;
    private List<GetWalkThrough.ItemsWalkthrough> mData = new ArrayList<>();

    public WalkthroughAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<GetWalkThrough.ItemsWalkthrough> getItems() {
        return mData;
    }


    @Override
    public int getCount() {
        return (mData != null ? mData.size() : 0);
    }

    public void setData(List<GetWalkThrough.ItemsWalkthrough> items) {
        mData = items;
        notifyDataSetChanged();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View walkthrough = inflater.inflate(R.layout.item_walkthrough, container, false);
        assert walkthrough != null;

        AppCompatTextView tvTitle = walkthrough.findViewById(R.id.tv_title_walkthrough);
        AppCompatTextView tvDescription = walkthrough.findViewById(R.id.tv_desc_walkthrough);
        AppCompatImageView imgWalkthrough = walkthrough.findViewById(R.id.iv_walkthrough);
        Picasso.get()
                .load(mData.get(position).getImage())
                .fit().centerCrop()
                .into(imgWalkthrough, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        tvTitle.setText(mData.get(position).getTitle());
        tvDescription.setText(Html.fromHtml(mData.get(position).getDescription()));
        container.addView(walkthrough, 0);
        return walkthrough;

    }
}
