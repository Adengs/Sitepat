package com.codelabs.dokter_mobil_customer.dialog;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatTextView;

import com.codelabs.dokter_mobil_customer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogProgress extends AppCompatDialog {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_loading)
    AppCompatTextView tvLoading;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_loading_desc)
    AppCompatTextView tvLoadingDesc;

    public DialogProgress(Context context, String title, boolean stat) {
        super(context, R.style.TransparentDialogTheme);
        setContentView(R.layout.dialog_loading);
        ButterKnife.bind(this);

        tvLoading.setText(title);
        String desc;
        if(stat)
            desc= "Please wait for "+title.toLowerCase();
        else
            desc= "Please wait a moment";

        tvLoadingDesc.setText(desc);

        setCanceledOnTouchOutside(false);
    }
}
