package com.codelabs.sitepat_customer.page.service.home_service.fragment;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.AddressAdapter;
import com.codelabs.sitepat_customer.adapter.TypeServiceAdapater;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.MapsApi;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.viewmodel.Maps;
import com.codelabs.sitepat_customer.viewmodel.NextHS1;
import com.codelabs.sitepat_customer.viewmodel.Outlet;
import com.codelabs.sitepat_customer.viewmodel.TypeService;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationFragmentHome extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.input_address)
    AppCompatEditText etAddress;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.note)
    AppCompatEditText etNote;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next)
    AppCompatTextView btnNext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_note)
    LinearLayout layNote;

    private List<Maps> mapsModel = new ArrayList<>();
    Call<List<Maps>> call;

    AddressAdapter addressAdapter;
    String keyword = "";
    String geojson = "json";
    String nameAddress = "";
    String lati = "";
    String loni = "";

    public LocationFragmentHome() {
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
        View view = inflater.inflate(R.layout.fragment_location_home, container, false);
        ButterKnife.bind(this, view);

        initView();
        initSetup();
        rvAddress.setVisibility(View.GONE);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initSetup() {

        functionSearch();

        if (DataManager.getInstance().getAddress() != ""){
            etAddress.setText(DataManager.getInstance().getAddress());
            etNote.setText(DataManager.getInstance().getNote());
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAddress.getText().toString().isEmpty()){
                    Toast.makeText(requireContext(), "Address can't be empty", Toast.LENGTH_SHORT).show();
                }
//                if (etTime.getText().toString().isEmpty()){
//                    Toast.makeText(requireContext(), "Address can't be empty", Toast.LENGTH_SHORT).show();
//                }
                else{
                    EventBus.getDefault().post(new NextHS1());
                    DataManager.getInstance().setAddress(etAddress.getText().toString());
                    DataManager.getInstance().setNote(etNote.getText().toString());
                    Log.e("TAG", "onClick: " + etAddress.getText().toString() );
//                    hideKeyboard();
                }
            }
        });

        addressAdapter.onChoose = new AddressAdapter.OnChoose() {
            @Override
            public void onChoose(String name, String lat, String lon) {
                nameAddress = name;
                lati = lat;
                loni = lon;

                DataManager.getInstance().setLat(lati);
                DataManager.getInstance().setLong(loni);
                etAddress.setText(nameAddress);
                rvAddress.setVisibility(View.GONE);
                layNote.setVisibility(View.VISIBLE);
                etAddress.clearFocus();
//                etNote.requestFocus();
//                hideKeyboard();
//                keyword = "";
            }
        };
    }

    private void initView(){
        addressAdapter = new AddressAdapter(getApplicationContext());
        rvAddress.setAdapter(addressAdapter);
        rvAddress.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void functionSearch() {
        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = s.toString();
                if (nameAddress != "") {
                    rvAddress.setVisibility(View.GONE);
//                    hideKeyboard();
                } else {
                    getAddress();
                }
//                if (!Objects.equals(DataManager.getInstance().getAddress(), s.toString())) {
//                    getAddress();
//                }
//
//                if (Objects.equals(nameAddress, s.toString())) {
//                    nameAddress = "";
//                    hideKeyboard();
//                }
                Log.e("TAG", "onTextChanged: '" + s.toString() );
                Log.e("TAG", "onTextChanged: '" + DataManager.getInstance().getAddress() );

            }

            @Override
            public void afterTextChanged(Editable s) {
//                rvAddress.setVisibility(View.GONE);
            }
        });

        etAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    etNote.setVisibility(View.VISIBLE);
                    getAddress();
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

    public void getAddress(){
        RetrofitInterface apiService = MapsApi.getApiService();
        if (call != null){
            call.cancel();
        }
        call = apiService.getMaps(keyword, geojson);
        call.enqueue(new Callback<List<Maps>>() {
            @Override
            public void onResponse(@NonNull Call<List<Maps>> call, @NonNull Response<List<Maps>> response) {
//                baseActivity.hideDialogProgress();
                if (response.isSuccessful()) {
                    List<Maps> data = response.body();
                    if (response.code() == 200) {
                        addressAdapter.setData(data);
                        rvAddress.setVisibility(View.VISIBLE);
                        layNote.setVisibility(View.GONE);

                        if (data.size() == 0){
                            rvAddress.setVisibility(View.GONE);
                            layNote.setVisibility(View.VISIBLE);
                        }

//                        outletAdapter.setData(data.getData().getItemsOutlet());
//                        Log.e("cek lat", String.valueOf(valueLatitude));
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_LONG).show();
                    rvAddress.setVisibility(View.GONE);
                    layNote.setVisibility(View.VISIBLE);
//                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Maps>> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                    Toast.makeText(requireContext(), getString(R.string.toast_onfailure), Toast.LENGTH_LONG).show();
                    rvAddress.setVisibility(View.GONE);
                    layNote.setVisibility(View.VISIBLE);
//                    baseActivity.hideDialogProgress();
//                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }
}