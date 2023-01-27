package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.CityAdapter;
import com.codelabs.sitepat_customer.adapter.ProvincieAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.viewmodel.Cities;
import com.codelabs.sitepat_customer.viewmodel.Province;
import com.codelabs.sitepat_customer.viewmodel.TypeService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetCities extends BottomSheetDialogFragment {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_cities)
    RecyclerView rvCities;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progress_view)
    ProgressBar progressView;

    CityAdapter cityAdapter;
    Integer limit = 100;
    String provinceId = "";

    //sampe sini
//    public void setData(Integer id) {
////        Log.e("TAG", "setData: " + complain );
//        this.provinceId = id;
//    }

    public BottomSheetCities() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_cities, container, false);
        ButterKnife.bind(this, view);

        initSetup();
        fethData();

        return view;
    }

    private void initSetup() {
        rvCities.setLayoutManager(new LinearLayoutManager(requireContext()));
        cityAdapter = new CityAdapter(requireContext());
        rvCities.setAdapter(cityAdapter);

    }

    private void fethData(){
//        if (DataManager.getInstance().getProvId().equals("")){
//
//        }else{
            loadCity();
//        }
    }

    public void loadCity(){
//        showDialogProgress("Getting List Virtual Account");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        provinceId = DataManager.getInstance().getProvId();
        Call<Cities> call = apiService.getListCities(auth, limit, provinceId);
        call.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(@NonNull Call<Cities> call, @NonNull Response<Cities> response) {
//                hideDialogProgress();
                if (response.isSuccessful()) {
                    Cities data = response.body();
                    if (response.code() == 200) {
                        cityAdapter.setData(data.getData().getItems());
                        progressView.setVisibility(View.GONE);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_LONG).show();
                    progressView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cities> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (!call.isCanceled()) {
//                    hideDialogProgress();
                    Toast.makeText(requireContext(), getString(R.string.toast_onfailure), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//    private void getID(){
//        if (cityAdapter.cityList.size() == 0){
//
//        }else{
//            cityAdapter.cityList
//        }
//    }
}