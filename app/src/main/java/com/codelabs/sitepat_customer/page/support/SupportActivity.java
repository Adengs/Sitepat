package com.codelabs.sitepat_customer.page.support;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.DetailComplaintAdapter;
import com.codelabs.sitepat_customer.adapter.TypeComplaintAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.page.shop.BottomSheetProvincie;
import com.codelabs.sitepat_customer.viewmodel.CitySelected;
import com.codelabs.sitepat_customer.viewmodel.DetailComplaintSelected;
import com.codelabs.sitepat_customer.viewmodel.DoPost;
import com.codelabs.sitepat_customer.viewmodel.OutletListSelected;
import com.codelabs.sitepat_customer.viewmodel.ProvincieSelected;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaint;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaintDetail;
import com.codelabs.sitepat_customer.viewmodel.TypeComplaintSelected;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.angmarch.views.SpinnerTextFormatter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    //    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.spinner_type_complaint)
//    NiceSpinner spinnerTypeComplaint;
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.spinner_type_complaint)
//    AppCompatSpinner spinnerTypeComplaint;
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.spinner_detail_complaint)
//    AppCompatSpinner spinnerDetailComplaint;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_action_continue)
    AppCompatTextView tvActionContinue;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_type_complaint)
    AppCompatEditText etTypeComplaint;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_detail_complaint)
    AppCompatEditText etDetailComplaint;

    TypeComplaintAdapter adapterType;
    DetailComplaintAdapter adapterDetail;

    private List<TypeComplaint.ItemsTypeComplaint> responseComplaint = new ArrayList<>();
    private List<TypeComplaintDetail.DataComplaintDetail> responseDetail = new ArrayList<>();
    private BottomSheetTypeComplaint bottomSheetTypeComplaint = new BottomSheetTypeComplaint();
    private BottomSheetDetailComplaint bottomSheetDetailComplaint = new BottomSheetDetailComplaint();

    String keyword = "";
    Integer typeComplaintID;
    Integer typeComplaintDetailID;


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
        edtComplaint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    btnActionSend.setVisibility(View.VISIBLE);
                    btnSendHold.setVisibility(View.GONE);
                } else {
                    btnActionSend.setVisibility(View.GONE);
                    btnSendHold.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        String[] array={"Select Type Complaint"};
//        AutoCompleteTextView textView;
//
//        ArrayAdapter<String> adapter;
//
//
//        adapter = new ArrayAdapter<String>(SupportActivity.this,
//                android.R.layout.simple_list_item_1, array);

//        responseComplaint.add(0, "Select a device");


//        adapterType = new TypeComplaintAdapter(this, responseComplaint);
//        spinnerTypeComplaint.setAdapter(adapterType);


        //        spinnerTypeComplaint.setSelection(-1);

//        if (adapterType.mItems.size() == 0){
//            spinnerTypeComplaint.setSelection(responseComplaint.indexOf("value"));
//            spinnerTypeComplaint.setAdapter(adapter);
////            handleSpinnerTypeComplaint();
////            handleSpinnerDetailComplaint();
//        }else{
//            handleSpinnerTypeComplaint();
//            handleSpinnerDetailComplaint();
//        spinnerTypeComplaint.setAdapter(adapterType);
//        }

//        SpinnerTextFormatter<TypeComplaint.ItemsTypeComplaint> textFormatter = new SpinnerTextFormatter<TypeComplaint.ItemsTypeComplaint>() {
//            @Override
//            public Spannable format(TypeComplaint.ItemsTypeComplaint person) {
//                return new SpannableString(person.getName());
//            }
//        };
//
//        spinnerTypeComplaint.setSpinnerTextFormatter(textFormatter);
//        spinnerTypeComplaint.setSelectedTextFormatter(textFormatter);
//
//        spinnerTypeComplaint.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
//            @Override
//            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
//                TypeComplaint.ItemsTypeComplaint person = (TypeComplaint.ItemsTypeComplaint) spinnerTypeComplaint.getSelectedItem();
//                Toast.makeText(SupportActivity.this, "Selected: " + person.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        spinnerTypeComplaint.attachDataSource(responseComplaint);


//        adapterDetail = new DetailComplaintAdapter(this, responseDetail);
//        spinnerDetailComplaint.setAdapter(adapterDetail);

//        DataManager.getInstance().setSelect("");
//        DataManager.getInstance().setSelectD("");
    }

    private void initSetup() {
        ivBack.setOnClickListener(this);
        btnActionSend.setOnClickListener(this);

        etTypeComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetTypeComplaint.show(getSupportFragmentManager(), bottomSheetTypeComplaint.getTag());
            }
        });
        etDetailComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataManager.getInstance().getTypeId().equals("")) {
                    showToast("Type Complaint is empty");
                } else {
                    bottomSheetDetailComplaint.show(getSupportFragmentManager(), bottomSheetDetailComplaint.getTag());
                }
            }
        });

//        spinnerTypeComplaint.setSelection(0);
//        handleSpinnerTypeComplaint();
//        handleSpinnerDetailComplaint();
    }

    private void fetchData() {
//        loadTypeComplaint();

    }

    private boolean valid() {
        if (TextUtils.isEmpty(Objects.requireNonNull(edtComplaint.getText()).toString().trim())) {
            showToast("please enter your complaint");
            return false;
        }
        return true;
    }

//    private void handleSpinnerTypeComplaint() {
////        spinnerTypeComplaint.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                handleSpinnerTypeComplaint();
////                handleSpinnerDetailComplaint();
////                spinnerTypeComplaint.setAdapter(adapterType);
////                loadTypeComplaint();
////                initView();
////            }
////        });
//
//        spinnerTypeComplaint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                TypeComplaint.ItemsTypeComplaint item = adapterType.getItem(position);
//                keyword = item.getName();
//                typeComplaintID = item.getId();
////                if ()
//                loadDetailComplaint();
//
//                DataManager.getInstance().setSelect("1");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

//    private void handleSpinnerDetailComplaint() {
//        spinnerDetailComplaint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                TypeComplaintDetail.DataComplaintDetail item = adapterDetail.getItem(position);
//                typeComplaintDetailID = item.getId();
//                DataManager.getInstance().setSelectD("1");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    private void loadTypeComplaint() {
        showDialogProgress("Getting data support");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<TypeComplaint> call = apiService.getTypeComplaint(auth);
        call.enqueue(new Callback<TypeComplaint>() {
            @Override
            public void onResponse(@NonNull Call<TypeComplaint> call, @NonNull Response<TypeComplaint> response) {
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
            public void onResponse(@NonNull Call<TypeComplaintDetail> call, @NonNull Response<TypeComplaintDetail> response) {
                if (response.isSuccessful()) {
                    TypeComplaintDetail data = response.body();
                    if (response.code() == 200) {
                        responseDetail = data.getDataComplainDetail();
//                        typeComplaintDetailID = responseDetail.get(0).getId();
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

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("complaint", edtComplaint.getText().toString().trim());
        requestBody.put("detailComplaintId", String.valueOf(typeComplaintDetailID));
        showDialogProgress("Send data complaint");

        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<DoPost> call = apiService.doSendComplaint(auth, requestBody);
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
            DataManager.getInstance().setTypeId("");
            finish();
        }

        if (btnActionSend == view) {
            DataManager.getInstance().setTypeId("");
            createComplaint();
        }

    }

    @Subscribe
    public void onSelectType(TypeComplaintSelected typeComplaintSelected){
        etTypeComplaint.setText(typeComplaintSelected.name);
        DataManager.getInstance().setTypeId(String.valueOf(typeComplaintSelected.id));
        bottomSheetTypeComplaint.dismiss();
    }

    @Subscribe
    public void onSelectDetail(DetailComplaintSelected detailComplaintSelected){
        etDetailComplaint.setText(detailComplaintSelected.name);
        typeComplaintDetailID = detailComplaintSelected.id;
        bottomSheetDetailComplaint.dismiss();
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