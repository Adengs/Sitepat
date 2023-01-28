package com.codelabs.sitepat_customer.page.service.body_paint.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.viewmodel.NextBP3;
import com.codelabs.sitepat_customer.viewmodel.NextHS3;
import com.codelabs.sitepat_customer.viewmodel.PreviousBP3;
import com.codelabs.sitepat_customer.viewmodel.PreviousHS3;
import com.codelabs.sitepat_customer.viewmodel.TimeSelected;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutletTimeFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.outlet)
    AppCompatEditText etOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.select_date)
    AppCompatEditText etDate;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.select_time)
    AppCompatEditText etTime;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next)
    AppCompatTextView btnNext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_previous)
    AppCompatTextView btnPrevious;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outlet_time, container, false);
        ButterKnife.bind(this, view);

        initSetup();
//        hideKeyboard();

        return view;
    }

    public void initSetup(){

        etOutlet.setText(DataManager.getInstance().getOutlet());

        if (DataManager.getInstance().getDate() != ""){
            etDate.setText(DataManager.getInstance().getDate());
            etTime.setText(DataManager.getInstance().getTime());
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDate.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Date cannot be empty", Toast.LENGTH_LONG).show();
                }
                if (etTime.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Time cannot be empty", Toast.LENGTH_LONG).show();
                }else{
                    EventBus.getDefault().post(new NextBP3());
                    DataManager.getInstance().setDate(etDate.getText().toString());
                    DataManager.getInstance().setTime(etTime.getText().toString());
                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new PreviousBP3());
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
    }

    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                etDate.setText(simpleDateFormat.format(calendar.getTime()));
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

        TimePickerDialog.OnTimeSetListener timeSetListener =  new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//                if (calendar.getTimeInMillis() >= c.getTimeInMillis()) {
//                    //it's after current
//                    int hour = hourOfDay % 12;
////                    etTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
////                            minute, hourOfDay < 12 ? "am" : "pm"));
//                    etTime.setText(simpleDateFormat.format(calendar.getTime()));
////                    EventBus.getDefault().post(new TimeSelected(etTime.getText().toString()));
//                } else {
//                    //it's before current'
//                    Toast.makeText(requireContext(), "Cannot select past time", Toast.LENGTH_LONG).show();
//                    etTime.setText("");
//                }
                etTime.setText(simpleDateFormat.format(calendar.getTime()));
                EventBus.getDefault().post(new TimeSelected(etTime.getText().toString()));
            }
        };

        TimePickerDialog tmd = new TimePickerDialog(requireActivity(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
//        tmd.
        tmd.show();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }
}