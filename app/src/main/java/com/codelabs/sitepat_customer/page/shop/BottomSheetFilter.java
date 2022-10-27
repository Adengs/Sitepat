package com.codelabs.sitepat_customer.page.shop;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.ItemBrandAdapter;
import com.codelabs.sitepat_customer.adapter.ItemTypeFilterAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.viewmodel.Brand;
import com.codelabs.sitepat_customer.viewmodel.BrandSelected;
import com.codelabs.sitepat_customer.viewmodel.TypeFilter;
import com.codelabs.sitepat_customer.viewmodel.TypeFilterSelected;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetFilter extends BottomSheetDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_close)
    AppCompatImageView btnClose;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_brand)
    RecyclerView rvBrand;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_type)
    RecyclerView rvType;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.reset)
    AppCompatTextView reset;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_min)
    AppCompatEditText etMin;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_max)
    AppCompatEditText etMax;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_apply)
    AppCompatTextView btnApply;

    ItemBrandAdapter itemBrandAdapter;
    ItemTypeFilterAdapter itemTypeFilterAdapter;
    private String filterBrand = "";
    private int filterBrandPositon = -1;
    private int filterTypePositon = -1;
    private String filterType = "";
    private String minPrice = "";
    private String maxPrice = "";
    private int count = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_filter, container, false);
        ButterKnife.bind(this, view);

        initView();
        fetchData();

        if (DataManager.getInstance().getMinPrice().equals("")){

        }else{
            etMin.setText(DataManager.getInstance().getMinPrice());
        }

        if (DataManager.getInstance().getMaxPrice().equals("")){

        }else{
            etMax.setText(DataManager.getInstance().getMaxPrice());
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                DataManager.getInstance().setPositionBrand(-1);
                DataManager.getInstance().setPositionType(-1);
                Objects.requireNonNull(rvBrand.getAdapter()).notifyDataSetChanged();
                Objects.requireNonNull(rvType.getAdapter()).notifyDataSetChanged();
                etMin.setText("");
                etMax.setText("");
                DataManager.getInstance().setMinPrice("");
                DataManager.getInstance().setMaxPrice("");
                EventBus.getDefault().post(new BrandSelected("", -1));
                EventBus.getDefault().post(new TypeFilterSelected("", -1));
                dismiss();
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(requireContext(), "On Develop :(", Toast.LENGTH_LONG).show();
                String minPrice = String.valueOf(etMin.getText());
                String maxPrice = String.valueOf(etMax.getText());
                EventBus.getDefault().post(new BrandSelected(filterBrand, filterBrandPositon));
                EventBus.getDefault().post(new TypeFilterSelected(filterType, filterTypePositon));
                DataManager.getInstance().setPositionBrand(filterBrandPositon);
                DataManager.getInstance().setPositionType(filterTypePositon);
                DataManager.getInstance().setMinPrice(minPrice);
                DataManager.getInstance().setMaxPrice(maxPrice);
                dismiss();
            }
        });
    }

    private void initView() {
        itemBrandAdapter = new ItemBrandAdapter(getApplicationContext());
        rvBrand.setAdapter(itemBrandAdapter);
        rvBrand.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

//        if(rvBrand.getAdapter() != null){
//            count = itemBrandAdapter.getItemCount();
//            Log.e("cek item count", String.valueOf(count));
//        }

        itemTypeFilterAdapter = new ItemTypeFilterAdapter(getApplicationContext());
        rvType.setAdapter(itemTypeFilterAdapter);
        rvType.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    private void fetchData(){
        loadBrand();
        loadType();
    }

    public void loadBrand() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Brand> call = apiService.getBrand(auth);
        call.enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(@NonNull Call<Brand> call, @NonNull Response<Brand> response) {
                if (response.isSuccessful()) {
                    Brand data = response.body();
                    if (response.code() == 200) {
                        itemBrandAdapter.setData(data.getData());
//                        DataManager.getInstance().setCustomerId(data.getData().getItems().get(0).getProductId());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(getApplicationContext(), error.message(), Toast.LENGTH_SHORT).show();
//                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Brand> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }

    public void loadType() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<TypeFilter> call = apiService.getTypeFilter(auth);
        call.enqueue(new Callback<TypeFilter>() {
            @Override
            public void onResponse(@NonNull Call<TypeFilter> call, @NonNull Response<TypeFilter> response) {
                if (response.isSuccessful()) {
                    TypeFilter data = response.body();
                    if (response.code() == 200) {
                        itemTypeFilterAdapter.setData(data.getData());
//                        DataManager.getInstance().setCustomerId(data.getData().getItems().get(0).getProductId());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(getApplicationContext(), error.message(), Toast.LENGTH_SHORT).show();
//                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TypeFilter> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }

    @Subscribe
    public void onFilterBrand(BrandSelected brandSelected){
        filterBrand = brandSelected.brandSelect;
        filterBrandPositon = brandSelected.brandPosition;
//        loadProduct();
    }

    @Subscribe
    public void onFilterType(TypeFilterSelected typeFilterSelected){
        filterType = typeFilterSelected.typeFilterSelect;
        filterTypePositon = typeFilterSelected.typePosition;
//        loadProduct();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}