package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.viewmodel.Cities;
import com.codelabs.sitepat_customer.viewmodel.CitySelected;
import com.codelabs.sitepat_customer.viewmodel.Province;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.viewHolder> {
    private final Context mContext;
    public List<Cities.ItemsEntity> cityList;

    public CityAdapter(Context context) {
        this.mContext = context;
        this.cityList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CityAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_provincie, parent, false);
        return new CityAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.viewHolder holder, int position) {

        String cityName = cityList.get(position).getCityname();
        Integer cityId = cityList.get(position).getCityid();
        holder.tvCity.setText(cityName);

        holder.containerProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CitySelected(cityName, cityId));
//                Intent intent = new Intent(mContext, OutletDetailActivity.class);
//                intent.putExtra("outlet_id", provincieList.get(position).getSiteId());
//                mContext.startActivity(intent);
//                DataManager.getInstance().setImageOutlet(provincieList.get(position).getSiteImage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (cityList != null ? cityList.size() : 0);
    }

    public void setData(List<Cities.ItemsEntity> data) {
        this.cityList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_provincie)
        ConstraintLayout containerProvince;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.text)
        TextView tvCity;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
