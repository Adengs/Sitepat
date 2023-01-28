package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
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
import com.codelabs.sitepat_customer.adapter.VirtualAccountAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.viewmodel.Province;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetProvincie extends BottomSheetDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_provincies)
    RecyclerView rvProvincie;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progress_view)
    ProgressBar progressView;

    ProvincieAdapter provincieAdapter;
    Integer limit = 50;

    public BottomSheetProvincie() {
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
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_provincie, container, false);
        ButterKnife.bind(this, view);

        initSetup();
        fethData();

        return view;
    }

    private void initSetup() {
        rvProvincie.setLayoutManager(new LinearLayoutManager(requireContext()));
        provincieAdapter = new ProvincieAdapter(requireContext());
        rvProvincie.setAdapter(provincieAdapter);
    }

    private void fethData(){
        loadProvincie();
    }

    public void loadProvincie(){
//        showDialogProgress("Getting List Virtual Account");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Province> call = apiService.getListProvince(auth, limit);
        call.enqueue(new Callback<Province>() {
            @Override
            public void onResponse(@NonNull Call<Province> call, @NonNull Response<Province> response) {
//                hideDialogProgress();
                if (response.isSuccessful()) {
                    Province data = response.body();
                    if (response.code() == 200) {
                        provincieAdapter.setData(data.getData().getItems());
                        progressView.setVisibility(View.GONE);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_LONG).show();
                    progressView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Province> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (!call.isCanceled()) {
//                    hideDialogProgress();
                    Toast.makeText(requireContext(), getString(R.string.toast_onfailure), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}