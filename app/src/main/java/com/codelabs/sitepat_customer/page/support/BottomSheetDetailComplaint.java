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
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaint;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaintDetail;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetDetailComplaint extends BottomSheetDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_detail_complaint)
    RecyclerView rvDetailComplaint;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progress_view)
    ProgressBar progressView;

    DetailComplaintSAdapter detailComplaintSAdapter;
    Integer typeComplaintID = 0;

    public BottomSheetDetailComplaint() {
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
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_detail_complaint, container, false);
        ButterKnife.bind(this, view);

        initSetup();
        fethData();

        return view;
    }

    private void initSetup() {
        rvDetailComplaint.setLayoutManager(new LinearLayoutManager(requireContext()));
        detailComplaintSAdapter = new DetailComplaintSAdapter(requireContext());
        rvDetailComplaint.setAdapter(detailComplaintSAdapter);
    }

    private void fethData(){
        loadProvincie();
    }

    public void loadProvincie(){
//        showDialogProgress("Getting List Virtual Account");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        typeComplaintID = Integer.valueOf(DataManager.getInstance().getTypeId());

        Call<TypeComplaintDetail> call = apiService.getDetailComplaint(auth, typeComplaintID);
        call.enqueue(new Callback<TypeComplaintDetail>() {
            @Override
            public void onResponse(@NonNull Call<TypeComplaintDetail> call, @NonNull Response<TypeComplaintDetail> response) {
                if (response.isSuccessful()) {
                    TypeComplaintDetail data = response.body();
                    if (response.code() == 200) {
                        detailComplaintSAdapter.setData(data.getDataComplainDetail());
                        progressView.setVisibility(View.GONE);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_SHORT).show();
                    progressView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TypeComplaintDetail> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    Toast.makeText(requireContext(), getString(R.string.toast_onfailure), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}