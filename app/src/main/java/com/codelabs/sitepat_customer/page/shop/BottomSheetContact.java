package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.Utils;
import com.codelabs.sitepat_customer.page.service.body_paint.BodyPaintActivity;
import com.codelabs.sitepat_customer.viewmodel.Cart;
import com.codelabs.sitepat_customer.viewmodel.ContactInformation;
import com.codelabs.sitepat_customer.viewmodel.ContactOutletInformation;
import com.codelabs.sitepat_customer.viewmodel.Profile;
import com.codelabs.sitepat_customer.viewmodel.SaveContactInformation;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BottomSheetContact extends BottomSheetDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_close)
    AppCompatImageView btnClose;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_name)
    AppCompatEditText etName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_address)
    AppCompatEditText etAddress;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_contact)
    AppCompatEditText etContact;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_email)
    AppCompatEditText etEmail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_save)
    AppCompatTextView btnSave;

    BottomSheetDialog dialog;
    BottomSheetBehavior<View> bottomSheetBehavior;
    private HashMap<String, RequestBody> list = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_contact, container, false);
        ButterKnife.bind(this, view);

        if (!DataManager.getInstance().getNameContact().equals("")){
            etName.setText(DataManager.getInstance().getNameContact());
            etAddress.setText(DataManager.getInstance().getAddressContact());
            etContact.setText(DataManager.getInstance().getContactContact());
            etEmail.setText(DataManager.getInstance().getEmailContact());
        }else{
            loadContactInformation();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int dm = Resources.getSystem().getDisplayMetrics().heightPixels;
        view.setMinimumHeight(dm);

        View bottomSheetView = LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_bottom_sheet_contact, null);
        dialog.setContentView(view);

        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setDraggable(false);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Name cannot be empty", Toast.LENGTH_LONG).show();
                }
                if (etAddress.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Address cannot be empty", Toast.LENGTH_LONG).show();
                }
                if (etContact.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Contact cannot be empty", Toast.LENGTH_LONG).show();
                }
                if (etEmail.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Email cannot be empty", Toast.LENGTH_LONG).show();
                }
                else{
                    DataManager.getInstance().setNameContact(etName.getText().toString());
                    Log.e("TAG", "onClick: " + etName.getText().toString() );
                    DataManager.getInstance().setAddressContact(etAddress.getText().toString());
                    DataManager.getInstance().setContactContact(etContact.getText().toString());
                    DataManager.getInstance().setemailContact(etEmail.getText().toString());
                    EventBus.getDefault().post(new SaveContactInformation());
                    Toast.makeText(v.getContext(), "Success", Toast.LENGTH_SHORT).show();
                    dismiss();
//                    saveContactInformation();
                }
            }
        });
    }

    public void loadContactInformation(){
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<ContactInformation> call = apiService.getContactInformation(auth);
        call.enqueue(new Callback<ContactInformation>() {
            @Override
            public void onResponse(@NonNull Call<ContactInformation> call, @NonNull Response<ContactInformation> response) {
                if (response.isSuccessful()) {
                    ContactInformation data = response.body();
                    if (response.code() == 200) {
                        etName.setText(data.getData().getName());
                        etAddress.setText(data.getData().getAddress());
                        etContact.setText(data.getData().getContact());
                        etEmail.setText(data.getData().getEmail());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ContactInformation> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (!call.isCanceled()) {
                    Toast.makeText(requireContext(), getString(R.string.toast_onfailure), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//    private void saveContactInformation(){
//        RetrofitInterface apiService = ApiUtils.getApiService();
//        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
//        String latitude = DataManager.getInstance().getLatitude();
//        String longitude = DataManager.getInstance().getLongitude();
//        String cartProductId = String.valueOf(DataManager.getInstance().getCartProduct());
//        String name = etName.getText().toString();
//        String address = etAddress.getText().toString();
//        String contact = etContact.getText().toString();
//        String email = etEmail.getText().toString();
//
//        list.put("cart_product_id", Utils.INSTANCE.createRequestBody(cartProductId));
//        list.put("name", Utils.INSTANCE.createRequestBody(name));
//        list.put("address", Utils.INSTANCE.createRequestBody(String.valueOf(address)));
//        list.put("contact", Utils.INSTANCE.createRequestBody(String.valueOf(contact)));
//        list.put("email", Utils.INSTANCE.createRequestBody(email));
//
//        Call<ContactOutletInformation> call = apiService.createContactOutlet(auth, latitude, longitude, list);
//        call.enqueue(new Callback<ContactOutletInformation>() {
//            @Override
//            public void onResponse(@NonNull Call<ContactOutletInformation> call, @NonNull Response<ContactOutletInformation> response) {
//                if (response.isSuccessful()) {
//                    ContactOutletInformation data = response.body();
//                    if (response.code() == 200) {
//                        Toast.makeText(requireContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
//                        dismiss();
//                    }
//                } else {
//                    ApiError error = ErrorUtils.parseError(response);
//                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ContactOutletInformation> call, @NonNull Throwable t) {
//                if (!call.isCanceled()) {
//                    t.printStackTrace();
//                }
//            }
//        });
//    }
}