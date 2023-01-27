package com.codelabs.sitepat_customer.page.service.body_paint.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.YourInformationFragment;
import com.codelabs.sitepat_customer.viewmodel.Previous4;
import com.codelabs.sitepat_customer.viewmodel.PreviousBP4;
import com.codelabs.sitepat_customer.viewmodel.Profile;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourInformationFragmentBody extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_book_now)
    AppCompatTextView btnBookNow;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_previous)
    AppCompatTextView btnPrevious;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fullname)
    AppCompatEditText etFullname;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.phone_number)
    AppCompatEditText etPhone;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.email)
    AppCompatEditText etEmail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.promo_code)
    AppCompatEditText etPromoCode;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_apply_grey)
    AppCompatTextView tvGrey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_apply_orange)
    AppCompatTextView tvOrange;

    private String keyword = "";

    public BookNow bookNow;

    public interface BookNow{
        void bookNow();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_your_information_body, container, false);
        ButterKnife.bind(this, view);

        initSetup();
        loadInformation();

        return view;
    }

    public void initSetup(){

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                bookNow.bookNow();

//                if (etFullname.getText().toString().isEmpty()){
//                    etFullname.setError("Column cannot be empty");
//                    etFullname.requestFocus();
//                    return;
//                }
//                if (etPhone.getText().toString().isEmpty()){
//                    etPhone.setError("Column cannot be empty");
//                    etPhone.requestFocus();
//                    return;
//                }
//                if (etEmail.getText().toString().isEmpty()){
//                    etEmail.setError("Column cannot be empty");
//                    etEmail.requestFocus();
//                    return;
//                }
//                if (!etPhone.getText().toString().equals("") && etPhone.getText().toString().length() < 11){
//                    etPhone.setError("Phone number at least 11 characters");
//                    etPhone.requestFocus();
//                    return;
//                }
//                else{
//                    EventBus.getDefault().post(new BookNow());

//                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new PreviousBP4());
            }
        });

        etPromoCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                keyword = s.toString();
                if (s.length() > 0){
                    tvGrey.setVisibility(View.GONE);
                    tvOrange.setVisibility(View.VISIBLE);
                }
                if (s.length() < 1){
                    tvGrey.setVisibility(View.VISIBLE);
                    tvOrange.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        if (tvOrange.getVisibility() == View.VISIBLE){
        tvOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "Promo not available now", Toast.LENGTH_SHORT).show();
                hideKeyboard();
            }
        });
//        }

    }

    public void loadInformation(){
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Profile> call = apiService.getProfile(auth);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                if (response.isSuccessful()) {
                    Profile data = response.body();
                    if (response.code() == 200) {
                        etFullname.setText(data.getDataProfile().getCustomerName());
                        etPhone.setText(data.getDataProfile().getCustomerPhone());
                        etEmail.setText(data.getDataProfile().getCustomerEmail());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_LONG).show();
//                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                t.printStackTrace();
//                if (!call.isCanceled()) {
//                    showToast(getString(R.string.toast_onfailure));
//                }
            }
        });
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }

}