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
import com.codelabs.sitepat_customer.adapter.OutletListAdapter;
import com.codelabs.sitepat_customer.adapter.ProvincieAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.viewmodel.Outlet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetListOutlet extends BottomSheetDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_list_outlet)
    RecyclerView rvListOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progress_view)
    ProgressBar progressView;

    OutletListAdapter outletListAdapter;
    private String valueLatitude = "";
    private String valueLongitude = "";

    public BottomSheetListOutlet() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_list_outlet, container, false);
        ButterKnife.bind(this, view);

        initSetup();
        fethData();

        return view;
    }

    private void initSetup() {
        rvListOutlet.setLayoutManager(new LinearLayoutManager(requireContext()));
        outletListAdapter = new OutletListAdapter(requireContext());
        rvListOutlet.setAdapter(outletListAdapter);
    }

    private void fethData() {
        loadOutlet();
    }

    public void loadOutlet() {
//        baseActivity.showDialogProgress("Getting outlet");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        valueLatitude = DataManager.getInstance().getLatitude();
        valueLongitude = DataManager.getInstance().getLongitude();
        Call<Outlet> call = apiService.getListOutlet(auth, valueLatitude, valueLongitude);
        call.enqueue(new Callback<Outlet>() {
            @Override
            public void onResponse(@NonNull Call<Outlet> call, @NonNull Response<Outlet> response) {
//                baseActivity.hideDialogProgress();
                if (response.isSuccessful()) {
                    Outlet data = response.body();
                    if (response.code() == 200) {
                        outletListAdapter.setData(data.getData().getItems());
                        progressView.setVisibility(View.GONE);
                        Log.e("cek lat", String.valueOf(valueLatitude));
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_LONG).show();
                    progressView.setVisibility(View.VISIBLE);
//                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Outlet> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    Toast.makeText(requireContext(), getString(R.string.toast_onfailure), Toast.LENGTH_LONG).show();
//                    baseActivity.hideDialogProgress();
//                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }
}