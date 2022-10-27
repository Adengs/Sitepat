package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.utils.RecentUtils;
import com.codelabs.sitepat_customer.viewmodel.NewProductDetail;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProduct extends BaseActivity{

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_cart)
    AppCompatImageView ivCart;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_product)
    AppCompatImageView imgProduct;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.type_product)
    AppCompatTextView typeProduct;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.name_product)
    AppCompatTextView nameProduct;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sub_product)
    AppCompatTextView subProduct;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.desc)
    AppCompatTextView desc;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.price)
    AppCompatTextView price;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_no_data)
    AppCompatTextView tvNoData;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_no_data)
    RelativeLayout containerNoData;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_detail_product)
    ConstraintLayout containerDetailProduct;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.card_type_product)
    CardView cardTypeProduct;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_add_cart)
    AppCompatTextView btnAddCart;

    private int idProduct = 0;
    private NewProductDetail.DataEntity responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);
        getPrefData();
        initView();
        fetchData();
        initSetup();
    }

    private void getPrefData() {
        Intent intent = getIntent();
        idProduct = intent.getIntExtra("product_id",1);
    }

    private void initSetup(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("On Develov :(");
            }
        });
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("On Develov :(");
            }
        });
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void fetchData() {
        loadDetailNewProduct();
    }

    public void loadDetailNewProduct() {
        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();

        showDialogProgress("Getting data news detail");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<NewProductDetail> call = apiService.getNewProductDetail(auth, idProduct, lat, lon);
        call.enqueue(new Callback<NewProductDetail>() {
            @Override
            public void onResponse(@NonNull Call<NewProductDetail> call, @NonNull Response<NewProductDetail> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    NewProductDetail data = response.body();
                    if (response.code() == 200) {
                        responseData = data.getData();
                        dataNewProductDetail();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                    containerNoData.setVisibility(View.VISIBLE);
                    containerDetailProduct.setVisibility(View.GONE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewProductDetail> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    containerNoData.setVisibility(View.VISIBLE);
                    containerDetailProduct.setVisibility(View.GONE);
                    tvNoData.setText(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void dataNewProductDetail() {

        String image = responseData.getProductImage();
        String type = responseData.getProductType();

        if (image.isEmpty()){

        }else {
            Picasso.get()
                    .load(image)
                    .into(imgProduct);
        }

        if (type.equals("")){
            cardTypeProduct.setVisibility(View.GONE);
        } else {
            cardTypeProduct.setVisibility(View.VISIBLE);
            typeProduct.setText(type);
        }

        nameProduct.setText(responseData.getProductName());
        subProduct.setText(responseData.getProductBrand());
        desc.setText(responseData.getProductDesc());
        price.setText(RecentUtils.toCurrency(String.valueOf(responseData.getProductPrice()).replace(".00","")));


//        tvTitleArticle.setText(responseData.getTitle());
//        tvDateArticle.setText(RecentUtils.formatDateToDateDMY(responseData.getCreatedAt()));
//        tvDescArticle.setText(RecentUtils.fromHtml(responseData.getContent()));
//        tvAuthor.setText(responseData.getAuthor());

    }

//    @Override
//    public void onClick(View view) {
//        if (ivBack == view) {
//            finish();
//        }
//    }

}