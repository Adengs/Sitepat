package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.viewmodel.SortSelected;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BottomSheetSort extends BottomSheetDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_sheet_sort)
    FrameLayout bottomsheetSort;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_close)
    AppCompatImageView btnClose;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cb_az)
    CheckBox cbAz;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cb_za)
    CheckBox cbZa;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cb_lowest)
    CheckBox cbLowest;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cb_highest)
    CheckBox cbHighest;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.az)
    AppCompatTextView az;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.za)
    AppCompatTextView za;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lowest)
    AppCompatTextView lowest;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.highest)
    AppCompatTextView highest;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.reset)
    AppCompatTextView reset;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_apply)
    AppCompatTextView btnApply;

    String ischeck = "";
//    String isZa = "2";
//    String islowest = "3";
//    String ishighest = "4";


    BottomSheetBehavior<View> bottomSheetBehavior;

    public BottomSheetSort(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_sort, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (DataManager.getInstance().getSort() == null){

        }else if (DataManager.getInstance().getSort().equals("1")){
            cbAz.setChecked(true);
            ischeck = DataManager.getInstance().getSort();
        }else if (DataManager.getInstance().getSort().equals("2")){
            cbZa.setChecked(true);
            ischeck = DataManager.getInstance().getSort();
        } else if (DataManager.getInstance().getSort().equals("3")){
            cbLowest.setChecked(true);
            ischeck = DataManager.getInstance().getSort();
        }else if (DataManager.getInstance().getSort().equals("4")){
            cbHighest.setChecked(true);
            ischeck = DataManager.getInstance().getSort();
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        az.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbAz.setChecked(true);
                cbZa.setChecked(false);
                cbLowest.setChecked(false);
                cbHighest.setChecked(false);

                ischeck = "1";
            }
        });
        za.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbAz.setChecked(false);
                cbZa.setChecked(true);
                cbLowest.setChecked(false);
                cbHighest.setChecked(false);

                ischeck = "2";
            }
        });
        lowest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbAz.setChecked(false);
                cbZa.setChecked(false);
                cbLowest.setChecked(true);
                cbHighest.setChecked(false);

                ischeck = "3";
            }
        });
        highest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbAz.setChecked(false);
                cbZa.setChecked(false);
                cbLowest.setChecked(false);
                cbHighest.setChecked(true);

                ischeck = "4";
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbAz.setChecked(false);
                cbZa.setChecked(false);
                cbLowest.setChecked(false);
                cbHighest.setChecked(false);

                DataManager.getInstance().setSort("");
                EventBus.getDefault().post(new SortSelected(ischeck));
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ischeck.equals("")){
                    Toast.makeText(requireContext(), "No data selected", Toast.LENGTH_LONG).show();
                }
//                else if(DataManager.getInstance().getSort() != ""){
//                    DataManager.getInstance().setSort(ischeck);
//                    EventBus.getDefault().post(new SortSelected(ischeck));
//                    dismiss();
//                }
                else{
                    DataManager.getInstance().setSort(ischeck);
                    EventBus.getDefault().post(new SortSelected(ischeck));
                    dismiss();
                }
            }
        });

//        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//
//        bottomsheetSort.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
    }
}