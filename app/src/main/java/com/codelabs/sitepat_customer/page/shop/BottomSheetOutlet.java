package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
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
import com.codelabs.sitepat_customer.viewmodel.CitySelected;
import com.codelabs.sitepat_customer.viewmodel.ContactOutletInformation;
import com.codelabs.sitepat_customer.viewmodel.Data;
import com.codelabs.sitepat_customer.viewmodel.Outlet;
import com.codelabs.sitepat_customer.viewmodel.OutletListSelected;
import com.codelabs.sitepat_customer.viewmodel.ProvincieSelected;
import com.codelabs.sitepat_customer.viewmodel.SaveContactInformation;
import com.codelabs.sitepat_customer.viewmodel.SaveOutletInformation;
import com.codelabs.sitepat_customer.viewmodel.TimeSelected;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BottomSheetOutlet extends BottomSheetDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_close)
    AppCompatImageView btnClose;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_province)
    AppCompatEditText etProvince;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_city)
    AppCompatEditText etCity;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_outlet)
    AppCompatEditText etOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_date)
    AppCompatEditText etDate;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_time)
    AppCompatEditText etTime;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_save)
    AppCompatTextView btnSave;

    String nameProv = "";

    BottomSheetDialog dialog;
    BottomSheetBehavior<View> bottomSheetBehavior;
    private BottomSheetProvincie bottomSheetProvincie = new BottomSheetProvincie();
    private BottomSheetCities bottomSheetCities = new BottomSheetCities();
    private BottomSheetListOutlet bottomSheetListOutlet = new BottomSheetListOutlet();
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
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_outlet, container, false);
        ButterKnife.bind(this, view);

        initSetup();
        fetchData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int dm = Resources.getSystem().getDisplayMetrics().heightPixels;
        view.setMinimumHeight(dm);

        View bottomSheetView = LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_bottom_sheet_outlet, null);
        dialog.setContentView(view);

        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setDraggable(false);

    }

    private void initSetup(){
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().setProvId("");
                DataManager.getInstance().setCityId("");
                dismiss();
            }
        });
        etProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!Objects.equals(nameProv, "")){
//                    etProvince.setText(nameProv);
//                }
//                bottomSheetProvincie.show(getChildFragmentManager(), bottomSheetProvincie.getTag());
            }
        });
        etCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (DataManager.getInstance().getProvId().equals("")){
//                    Toast.makeText(requireContext(), "Province is empty", Toast.LENGTH_SHORT).show();
//                }else{
//                    bottomSheetCities.show(getChildFragmentManager(), bottomSheetProvincie.getTag());
//                }
            }
        });
        etOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (DataManager.getInstance().getCityId().equals("")){
//                    Toast.makeText(requireContext(), "City is empty", Toast.LENGTH_SHORT).show();
//                }else{
//                    bottomSheetListOutlet.show(getChildFragmentManager(), bottomSheetProvincie.getTag());
//                }
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etProvince.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Province cannot be empty", Toast.LENGTH_LONG).show();
                }
                if (etCity.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "City cannot be empty", Toast.LENGTH_LONG).show();
                }
                if (etOutlet.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Outlet cannot be empty", Toast.LENGTH_LONG).show();
                }
                if (etDate.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Date cannot be empty", Toast.LENGTH_LONG).show();
                }
                if (etTime.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Time cannot be empty", Toast.LENGTH_LONG).show();
                }
                else{
                    DataManager.getInstance().setProvinceOutlet(etProvince.getText().toString());
                    DataManager.getInstance().setCityOutlet(etCity.getText().toString());
                    DataManager.getInstance().setOutletOutlet(etOutlet.getText().toString());
                    DataManager.getInstance().setDateOutlet(etDate.getText().toString());
                    DataManager.getInstance().setTimeOutlet(etTime.getText().toString());
                    EventBus.getDefault().post(new SaveOutletInformation());
                    Toast.makeText(v.getContext(), "Success", Toast.LENGTH_SHORT).show();
                    dismiss();
//                    saveOutletInformation();
                }
            }
        });
    }

    private void fetchData() {
        loadOutlet();
    }

    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();
        Calendar c = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                etDate.setText(simpleDateFormat.format(calendar.getTime()));
                DataManager.getInstance().setDateOutletDb(sdf.format(calendar.getTime()));
                Log.e("TAG Date", "onTimeSet: " + sdf.format(calendar.getTime()) );
                DataManager.getInstance().setChooseDate(simpleDateFormat.format(calendar.getTime()).replace("-",""));
                DataManager.getInstance().setNowDate(simpleDateFormat.format(c.getTime()).replace("-",""));
//                EventBus.getDefault().post(new DateSelected(etDate.getText().toString()));
            }
        };

        DatePickerDialog dpd = new DatePickerDialog(requireActivity(), dataSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.show();
    }

    private void showTimeDialog(){
        Calendar calendar = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
//        Long date = Long.parseLong(DataManager.getInstance().getChooseDate());
//        Long dateNow = Long.parseLong(DataManager.getInstance().getNowDate());

        TimePickerDialog.OnTimeSetListener timeSetListener =  new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Log.e("calendar", "onTimeSet: " + simpleDateFormat.format(calendar.getTime()));
                Log.e("c", "onTimeSet: " + simpleDateFormat.format(c.getTime()));
//                if (calendar.getTimeInMillis() >= c.getTimeInMillis() && date >= dateNow) {
//                    //it's after current
//                    int hour = hourOfDay % 12;
////                    etTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
////                            minute, hourOfDay < 12 ? "am" : "pm"));
//                    etTime.setText(simpleDateFormat.format(calendar.getTime()));
////                    EventBus.getDefault().post(new TimeSelected(etTime.getText().toString()));
//                }
//                if (date == dateNow){
//                    Toast.makeText(requireContext(), "Cannot select past time", Toast.LENGTH_LONG).show();
//                    etTime.setText("");
//                }
//                else {
//                    //it's before current'
//                    Toast.makeText(requireContext(), "Cannot select past time", Toast.LENGTH_LONG).show();
//                    etTime.setText("");
//                }
                etTime.setText(simpleDateFormat.format(calendar.getTime()));
                DataManager.getInstance().setTimeOutletDb(sdf.format(calendar.getTime()));
                Log.e("TAG Time", "onTimeSet: " + sdf.format(calendar.getTime()) );
                EventBus.getDefault().post(new TimeSelected(etTime.getText().toString()));
            }
        };

        TimePickerDialog tmd = new TimePickerDialog(requireActivity(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
//        tmd.
        tmd.show();
    }

//    private void saveOutletInformation(){
//        RetrofitInterface apiService = ApiUtils.getApiService();
//        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
//        String latitude = DataManager.getInstance().getLatitude();
//        String longitude = DataManager.getInstance().getLongitude();
//        String cartProductId = String.valueOf(DataManager.getInstance().getCartProduct());
//        String province = etProvince.getText().toString();
//        String city = etCity.getText().toString();
//        String outlet = etOutlet.getText().toString();
//        String date = etDate.getText().toString();
//        String time = etTime.getText().toString();
//
//        list.put("cart_product_id", Utils.INSTANCE.createRequestBody(cartProductId));
//        list.put("province", Utils.INSTANCE.createRequestBody(province));
//        list.put("city", Utils.INSTANCE.createRequestBody(String.valueOf(city)));
//        list.put("outlet_name", Utils.INSTANCE.createRequestBody(String.valueOf(outlet)));
//        list.put("date", Utils.INSTANCE.createRequestBody(date));
//        list.put("time", Utils.INSTANCE.createRequestBody(time));
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

    public void loadOutlet() {
//        baseActivity.showDialogProgress("Getting outlet");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        String valueLatitude = DataManager.getInstance().getLatitude();
        String valueLongitude = DataManager.getInstance().getLongitude();
        Call<Outlet> call = apiService.getListOutlet(auth, valueLatitude, valueLongitude);
        call.enqueue(new Callback<Outlet>() {
            @Override
            public void onResponse(@NonNull Call<Outlet> call, @NonNull Response<Outlet> response) {
//                baseActivity.hideDialogProgress();
                if (response.isSuccessful()) {
                    Outlet data = response.body();
                    if (response.code() == 200) {
                        etProvince.setText(data.getData().getItems().get(0).getProvincename());
                        etCity.setText(data.getData().getItems().get(0).getCityname());
                        etOutlet.setText(data.getData().getItems().get(0).getSitename());
                        Log.e("cek lat", String.valueOf(valueLatitude));
                        Log.e("cek prov", data.getData().getItems().get(0).getProvincename());
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

    @Subscribe
    public void onSelectProvincie(ProvincieSelected provincieSelected){
        Log.e("provName", "onSelectProvincie: " + provincieSelected.provincieName );
        Log.e("provId", "onSelectProvincie: " + provincieSelected.provincieId );
//        nameProv = provincieSelected.provincieName;
        etProvince.setText(provincieSelected.provincieName);
        DataManager.getInstance().setProvId(String.valueOf(provincieSelected.provincieId));
        bottomSheetProvincie.dismiss();
//        dismiss();
    }

    @Subscribe
    public void onSelectCity(CitySelected citySelected){
        Log.e("provName", "onSelectProvincie: " + citySelected.cityName );
        Log.e("provId", "onSelectProvincie: " + citySelected.cityId );
        etCity.setText(citySelected.cityName);
        DataManager.getInstance().setCityId(String.valueOf(citySelected.cityId));
        bottomSheetCities.dismiss();
    }

    @Subscribe
    public void onSelectOutlet(OutletListSelected outletListSelected){
        Log.e("provName", "onSelectProvincie: " + outletListSelected.outletName );
        Log.e("provId", "onSelectProvincie: " + outletListSelected.outletId );
        etOutlet.setText(outletListSelected.outletName);
//        DataManager.getInstance().setCityId(String.valueOf(outletListSelected.cityId));
        bottomSheetListOutlet.dismiss();
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