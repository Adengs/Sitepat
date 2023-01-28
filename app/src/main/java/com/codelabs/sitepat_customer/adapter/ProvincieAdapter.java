package com.codelabs.sitepat_customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.page.outlet.OutletDetailActivity;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.BikeServiceFragment;
import com.codelabs.sitepat_customer.viewmodel.Outlet;
import com.codelabs.sitepat_customer.viewmodel.Province;
import com.codelabs.sitepat_customer.viewmodel.ProvincieSelected;
import com.codelabs.sitepat_customer.viewmodel.TypeService;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProvincieAdapter extends RecyclerView.Adapter<ProvincieAdapter.viewHolder> {
    private final Context mContext;
    private List<Province.ItemsEntity> provincieList;
    public OnClick onClick;

    public interface OnClick{
        void onClick(Integer id);
    }

    public ProvincieAdapter(Context context) {
        this.mContext = context;
        this.provincieList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProvincieAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_provincie, parent, false);
        return new ProvincieAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvincieAdapter.viewHolder holder, int position) {

        Integer provId = provincieList.get(position).getProvinceid();
        String provName = provincieList.get(position).getProvincename();
        holder.tvProvincie.setText(provName);

        holder.containerProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onClick.onClick(provId);
                EventBus.getDefault().post(new ProvincieSelected(provName, provId));
//                Intent intent = new Intent(mContext, OutletDetailActivity.class);
//                intent.putExtra("outlet_id", provincieList.get(position).getSiteId());
//                mContext.startActivity(intent);
//                DataManager.getInstance().setImageOutlet(provincieList.get(position).getSiteImage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (provincieList != null ? provincieList.size() : 0);
    }

    public void setData(List<Province.ItemsEntity> data) {
        this.provincieList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_provincie)
        ConstraintLayout containerProvince;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.text)
        TextView tvProvincie;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
