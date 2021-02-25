package com.codelabs.dokter_mobil_customer.page.support;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.adapter.DetailComplaintAdapter;
import com.codelabs.dokter_mobil_customer.adapter.TypeComplaintAdapter;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.viewmodel.DoPost;
import com.codelabs.dokter_mobil_customer.viewmodel.TypeComplaint;
import com.codelabs.dokter_mobil_customer.viewmodel.TypeComplaintDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_complaint)
    AppCompatEditText edtComplaint;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_send_hold)
    FrameLayout btnSendHold;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_action_send)
    FrameLayout btnActionSend;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spinner_type_complaint)
    AppCompatSpinner spinnerTypeComplaint;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spinner_detail_complaint)
    AppCompatSpinner spinnerDetailComplaint;

    TypeComplaintAdapter adapterType;
    DetailComplaintAdapter adapterDetail;

    private List<TypeComplaint.ItemsTypeComplaint> responseComplaint = new ArrayList<>();
    private List<TypeComplaintDetail.DataComplaintDetail> responseDetail = new ArrayList<>();

    String keyword = "";
    Integer typeComplaintID = 1;
    Integer typeComplaintDetailID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();

    }


    private void initView() {
        adapterType = new TypeComplaintAdapter(this, responseComplaint);
        spinnerTypeComplaint.setAdapter(adapterType);

        adapterDetail = new DetailComplaintAdapter(this, responseDetail);
        spinnerDetailComplaint.setAdapter(adapterDetail);
    }

    private void initSetup() {
        ivBack.setOnClickListener(this);
        edtComplaint.setOnClickListener(this);
        btnActionSend.setOnClickListener(this);

        handleSpinnerTypeComplaint();
        handleSpinnerDetailComplaint();
    }

    private void fetchData() {
        loadTypeComplaint();

    }

    private boolean valid() {
        if (TextUtils.isEmpty(Objects.requireNonNull(edtComplaint.getText()).toString().trim())) {
            showToast("please enter your complaint");
            return false;
        }
        return true;
    }

    private void handleSpinnerTypeComplaint() {
        spinnerTypeComplaint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypeComplaint.ItemsTypeComplaint item = adapterType.getItem(position);
                keyword = item.getName();
                typeComplaintID = item.getId();
                loadDetailComplaint();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void handleSpinnerDetailComplaint() {
        spinnerDetailComplaint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               TypeComplaintDetail.DataComplaintDetail item = adapterDetail.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadTypeComplaint() {
        showDialogProgress("Getting data support");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<TypeComplaint> call = apiService.getTypeComplaint(auth);
        call.enqueue(new Callback<TypeComplaint>() {
            @Override
            public void onResponse(@NonNull Call<TypeComplaint> call,@NonNull Response<TypeComplaint> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    TypeComplaint data = response.body();
                    if (response.code() == 200) {
                        responseComplaint = data.getData().getItemsTypeComplaint();
                        adapterType.clear();
                        adapterType.addAll(response.body().getData().getItemsTypeComplaint());
                        adapterType.notifyDataSetChanged();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TypeComplaint> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    private void loadDetailComplaint() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<TypeComplaintDetail> call = apiService.getDetailComplaint(auth, typeComplaintID);
        call.enqueue(new Callback<TypeComplaintDetail>() {
            @Override
            public void onResponse(@NonNull Call<TypeComplaintDetail> call,@NonNull Response<TypeComplaintDetail> response) {
                if (response.isSuccessful()) {
                    TypeComplaintDetail data = response.body();
                    if (response.code() == 200) {
                        responseDetail = data.getDataComplainDetail();
                        typeComplaintDetailID = responseDetail.get(0).getId();
                        adapterDetail.clear();
                        adapterDetail.addAll(response.body().getDataComplainDetail());
                        adapterDetail.notifyDataSetChanged();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TypeComplaintDetail> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void createComplaint() {
        if (!valid())
            return;

        showDialogProgress("Send data complaint");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<DoPost> call = apiService.createComplaint(auth, typeComplaintDetailID, edtComplaint.getText().toString().trim());
        call.enqueue(new Callback<DoPost>() {
            @Override
            public void onResponse(@NonNull Call<DoPost> call, @NonNull Response<DoPost> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        showToast("SUCCESS");
                        Intent intent = new Intent(SupportActivity.this, SupportDoneActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoPost> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }

        if (btnActionSend == view) {
            createComplaint();
        }

        if (edtComplaint == view) {
            assert edtComplaint != null;
            edtComplaint.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    btnSendHold.setVisibility(View.GONE);
                    btnActionSend.setVisibility(View.VISIBLE);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}