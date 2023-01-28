package com.codelabs.sitepat_customer.page.service.booking_service.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.OutletBookingAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.dialog.DialogProgress;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.viewmodel.CloseOutletSelected;
import com.codelabs.sitepat_customer.viewmodel.NextBS1;
import com.codelabs.sitepat_customer.viewmodel.Outlet;
import com.codelabs.sitepat_customer.viewmodel.OutletSelected;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_outlet)
    RecyclerView rvOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_search_outlet)
    AppCompatEditText etSearchOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next)
    AppCompatTextView btnNext;

//    protected Context context;
//
//    protected DialogProgress dialogProgress;
//    BaseActivity baseActivity = new BaseActivity();

    OutletBookingAdapter outletAdapter;
    private String keyword = "";
    private String siteId = "";
    private String close = "";
    private int position = -1;
    private int next = 0;
//    private double valueLatitude, valueLongitude;
    private String valueLatitude = "";
    private String valueLongitude = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadOutlet();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, view);

        initView();
        initSetup();
        fetchData();

        return view;
    }

    private void initView() {
        rvOutlet.setLayoutManager(new LinearLayoutManager(requireContext()));
        outletAdapter = new OutletBookingAdapter(requireContext());
        outletAdapter.setData(new ArrayList<>());
        rvOutlet.setAdapter(outletAdapter);

    }

    private void initSetup() {
        functionSearch();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siteId != null) {
                    if (!siteId.equals("") && !close.equals("1")) {
                        EventBus.getDefault().post(new NextBS1());
                        DataManager.getInstance().setSiteId(siteId);
                        DataManager.getInstance().setPositionLocation(position);
                        DataManager.getInstance().setClose(close);
                    }
//                    if (DataManager.getInstance().getPositionLocation() == -1){
//
//                    }
//                    if (close.equals("0")){
//                        Toast.makeText(v.getContext(), "Please select an outlet that is currently open", Toast.LENGTH_LONG).show();
//                    }
//                    else if (siteId.equals("")){
//                        Toast.makeText(v.getContext(), "No outlet selected", Toast.LENGTH_LONG).show();
//                    }
                    else {
                        Toast.makeText(v.getContext(), "No outlet selected", Toast.LENGTH_LONG).show();
                    }
                }
//                if (DataManager.getInstance().getPositionLocation() == -1){
//                    Toast.makeText(v.getContext(), "No outlet selected", Toast.LENGTH_LONG).show();
//                }else{
//                    EventBus.getDefault().post(new NextBS1());
//                    DataManager.getInstance().setSiteId(siteId);
//                    DataManager.getInstance().setPositionLocation(position);
//                }
            }
        });
    }

    private void fetchData() {
        loadOutlet();
    }

    public void loadOutlet() {
//        baseActivity.showDialogProgress("Getting outlet");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        valueLatitude = DataManager.getInstance().getLatitude();
        valueLongitude = DataManager.getInstance().getLongitude();
        Call<Outlet> call = apiService.getOutlet(auth, keyword, valueLatitude, valueLongitude);
        call.enqueue(new Callback<Outlet>() {
            @Override
            public void onResponse(@NonNull Call<Outlet> call, @NonNull Response<Outlet> response) {
//                baseActivity.hideDialogProgress();
                if (response.isSuccessful()) {
                    Outlet data = response.body();
                    if (response.code() == 200) {
                        outletAdapter.setData(data.getData().getItems());
                        Log.e("cek lat", String.valueOf(valueLatitude));
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_LONG).show();
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

    private void functionSearch() {
        etSearchOutlet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = s.toString();
                loadOutlet();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearchOutlet.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }

//    public void showDialogProgress(String message) {
//        if (message != null) {
//            dialogProgress = new DialogProgress(context, message, true);
//            dialogProgress.setCancelable(false);
//            dialogProgress.show();
//        } else {
//            dialogProgress = new DialogProgress(context, "Loading ...", false);
//            dialogProgress.setCancelable(false);
//            dialogProgress.show();
//        }
//    }
//
//    public void hideDialogProgress() {
//        if (dialogProgress != null) {
//            if (dialogProgress.isShowing()) {
//                dialogProgress.dismiss();
//            }
//        }
//    }

    @Subscribe
    public void onOutletSelect(OutletSelected outletSelected) {
        siteId = outletSelected.outletSelect;
        position = outletSelected.positonLocation;
    }

    @Subscribe
    public void onCloseSelect(CloseOutletSelected closeOutletSelected) {
        close = closeOutletSelected.closeOutlet;
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