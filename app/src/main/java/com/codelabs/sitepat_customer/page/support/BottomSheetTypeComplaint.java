package com.codelabs.sitepat_customer.page.support;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.ProvincieAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.viewmodel.Province;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaint;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetTypeComplaint extends BottomSheetDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_type_complaint)
    RecyclerView rvTypeComplaint;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progress_view)
    ProgressBar progressView;

    TypeComplaintSAdapter typeComplaintSAdapter;

    public BottomSheetTypeComplaint() {
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
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_type_complaint, container, false);
        ButterKnife.bind(this, view);

        initSetup();
        fethData();

        return view;
    }

    private void initSetup() {
        rvTypeComplaint.setLayoutManager(new LinearLayoutManager(requireContext()));
        typeComplaintSAdapter = new TypeComplaintSAdapter(requireContext());
        rvTypeComplaint.setAdapter(typeComplaintSAdapter);
    }

    private void fethData(){
        loadProvincie();
    }

    public void loadProvincie(){
//        showDialogProgress("Getting List Virtual Account");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();

        Call<TypeComplaint> call = apiService.getTypeComplaint(auth);
        call.enqueue(new Callback<TypeComplaint>() {
            @Override
            public void onResponse(@NonNull Call<TypeComplaint> call, @NonNull Response<TypeComplaint> response) {
                if (response.isSuccessful()) {
                    TypeComplaint data = response.body();
                    if (response.code() == 200) {
                        typeComplaintSAdapter.setData(data.getData().getItemsTypeComplaint());
                        progressView.setVisibility(View.GONE);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_SHORT).show();
                    progressView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TypeComplaint> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    Toast.makeText(requireContext(), getString(R.string.toast_onfailure), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}