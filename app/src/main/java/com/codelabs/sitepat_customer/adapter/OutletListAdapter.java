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
import com.codelabs.sitepat_customer.viewmodel.Outlet;
import com.codelabs.sitepat_customer.viewmodel.OutletListSelected;
import com.codelabs.sitepat_customer.viewmodel.Province;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutletListAdapter extends RecyclerView.Adapter<OutletListAdapter.viewHolder> {
    private final Context mContext;
    private List<Outlet.ItemsEntity> outletList;

    public OutletListAdapter(Context context) {
        this.mContext = context;
        this.outletList = new ArrayList<>();
    }

    @NonNull
    @Override
    public OutletListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_provincie, parent, false);
        return new OutletListAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutletListAdapter.viewHolder holder, int position) {

        String outletName = outletList.get(position).getSitename();
        Integer outletId = outletList.get(position).getSiteid();
        holder.tvOutlet.setText(outletName);

        holder.containerProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OutletListSelected(outletName, outletId));
//                Intent intent = new Intent(mContext, OutletDetailActivity.class);
//                intent.putExtra("outlet_id", provincieList.get(position).getSiteId());
//                mContext.startActivity(intent);
//                DataManager.getInstance().setImageOutlet(provincieList.get(position).getSiteImage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (outletList != null ? outletList.size() : 0);
    }

    public void setData(List<Outlet.ItemsEntity> data) {
        this.outletList = data;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.container_provincie)
        ConstraintLayout containerProvince;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.text)
        TextView tvOutlet;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
