package com.codelabs.dokter_mobil_customer.page.outlet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.helper.Utils;
import com.codelabs.dokter_mobil_customer.viewmodel.OutletDetail;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.samlss.lighter.Lighter;
import me.samlss.lighter.interfaces.OnLighterListener;
import me.samlss.lighter.parameter.Direction;
import me.samlss.lighter.parameter.LighterParameter;
import me.samlss.lighter.parameter.MarginOffset;
import me.samlss.lighter.shape.RectShape;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutletDetailActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_outlet)
    AppCompatImageView ivOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_outlet_name)
    AppCompatTextView tvOutletName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_status_outlet)
    AppCompatTextView tvStatusOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_distance)
    AppCompatTextView tvDistance;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_alamat_outlet)
    AppCompatTextView tvAlamatOutlet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_reservation)
    FrameLayout btnReservation;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_title)
    AppCompatTextView tvTitle;

    int idOutlet = -1;
    String phoneNumber = "";
    private OutletDetail.DataOutletDetail responseData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_detail);
        ButterKnife.bind(this);
        getPrevData();
        initView();
        fetchData();
    }

    private void getPrevData() {
        Intent intent = getIntent();
        idOutlet = intent.getIntExtra("outlet_id", -1);
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        ivBack.setOnClickListener(this);
        tvTitle.setText(R.string.detail_outlet);
        btnReservation.setOnClickListener(this);
    }

    private void fetchData() {
        loadOutletDetail();
    }

    public void loadOutletDetail() {
        showDialogProgress("Getting data outlet detail");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<OutletDetail> call = apiService.getOutletDetail(auth, idOutlet);
        call.enqueue(new Callback<OutletDetail>() {
            @Override
            public void onResponse(@NonNull Call<OutletDetail> call, @NonNull Response<OutletDetail> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    OutletDetail data = response.body();
                    if (response.code() == 200) {
                        responseData = data.getDataOutletDetail();
                        phoneNumber = responseData.getSitePhone();
                        dataOutletDetail();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<OutletDetail> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                }
            }
        });
    }

    private void dataOutletDetail() {
        if (responseData.getSiteImage().equals("")) {
            ivOutlet.setImageResource(R.drawable.background_outlet_status);
        } else {
            Picasso.get()
                    .load(responseData.getSiteImage())
                    .fit().centerCrop()
                    .into(ivOutlet);
        }

        if (responseData.getIsOpen() == 1) {
            tvStatusOutlet.setVisibility(View.VISIBLE);
        } else {
            tvStatusOutlet.setVisibility(View.GONE);
        }
        tvOutletName.setText(responseData.getSiteName());
        tvDistance.setText(responseData.getDistance());
        tvAlamatOutlet.setText(responseData.getSiteAddress());
        if (getIntent().getBooleanExtra("IS_HIGHLIGHT_OUTLET", false)) {
            Lighter.with(OutletDetailActivity.this)
                    .addHighlight(new LighterParameter.Builder()
                            .setHighlightedViewId(R.id.btn_reservation)
                            .setTipView(Utils.INSTANCE.createCommonTipViewTop(OutletDetailActivity.this, "Klik Reservation untuk menghubungi outlet yang dipilih"))
                            .setLighterShape(new RectShape(5, 5, 30))
                            .setTipViewRelativeDirection(Direction.TOP)
                            .setTipViewRelativeOffset(new MarginOffset(0, 0, 0, 0))
                            .build())
                    .setOnLighterListener(new OnLighterListener() {
                        @Override
                        public void onShow(int index) {

                        }

                        @Override
                        public void onDismiss() {
                            finish();
                        }
                    })
                    .show();
        }

    }


    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }

        if (btnReservation == view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }
    }
}