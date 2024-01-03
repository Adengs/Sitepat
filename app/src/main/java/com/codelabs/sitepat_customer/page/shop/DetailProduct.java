package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.helper.Utils;
import com.codelabs.sitepat_customer.utils.RecentUtils;
import com.codelabs.sitepat_customer.viewmodel.CartProduct;
import com.codelabs.sitepat_customer.viewmodel.DoPostV2;
import com.codelabs.sitepat_customer.viewmodel.NewProductDetail;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
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
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.card_cart)
    CardView cardCart;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_count_cart)
    TextView tvCountCart;

    private int idProduct = 0;
    private String productName = "";
    private String productSku = "";
    private String taxtRate = "";
    private int categoryId = 0;
    private String categoryName = "";
    private int productPrice = 0;
    private int produckQty = 0;
    private NewProductDetail.DataEntity responseData;
    private HashMap<String, RequestBody> list = new HashMap<>();
    private HashMap<String, RequestBody> listCart = new HashMap<>();

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

    @Override
    protected void onResume() {
        super.onResume();
        getCountCart();
    }

    private void getPrefData() {
        Intent intent = getIntent();
        idProduct = intent.getIntExtra("product_id",1);
        productName = intent.getStringExtra("product_name");
        productSku = intent.getStringExtra("product_sku");
        taxtRate = intent.getStringExtra("tax_rate");
        categoryId = intent.getIntExtra("category_id",1);
        categoryName = intent.getStringExtra("category_name");
        productPrice = intent.getIntExtra("product_price", 1);
        produckQty = intent.getIntExtra("product_qty",1);
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
//                showToast("On Develov :(");
                Intent intent = new Intent(DetailProduct.this, CartActivity.class);
                startActivity(intent);
            }
        });
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToast("On Develov :(");
                addToCart();
                getCountCart();
                onResume();
//                finish();
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
        getCartProductId();
        getCountCart();
    }

    public void loadDetailNewProduct() {
        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();

        showDialogProgress("Getting product detail");
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

    private void getCartProductId(){
        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();
        String custId = String.valueOf(DataManager.getInstance().getCustomerId());
        String custName = DataManager.getInstance().getName();
        int cleanCart = 0;

        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();

        list.put("customer_id", Utils.INSTANCE.createRequestBody(custId));
        list.put("customer_name", Utils.INSTANCE.createRequestBody(custName));
        list.put("clean_cart", Utils.INSTANCE.createRequestBody(String.valueOf(cleanCart)));

        Call<CartProduct> call = apiService.createCartProduct(auth, lat, lon, list);
        call.enqueue(new Callback<CartProduct>() {
            @Override
            public void onResponse(@NonNull Call<CartProduct> call, @NonNull Response<CartProduct> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    CartProduct data = response.body();
                    if (response.code() == 200) {
                        DataManager.getInstance().setCartProduct(data.getData().getCartProductId());
//                        responseData = data.getData();
//                        dataNewProductDetail();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CartProduct> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                }
            }
        });
    }

    public void dataNewProductDetail() {

        String image = responseData.getProductImage();
        String type = responseData.getProductType();

        if (image.isEmpty()){

        }else {
            Glide.with(context)
                    .load(image)
                    .thumbnail(0.25f)
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
//                    .dontTransform()
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

    private void addToCart(){
        RetrofitInterface apiService = ApiUtils.getApiService();
        String cartProductId = String.valueOf(DataManager.getInstance().getCartProduct());
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();
        int productQtyDefault = 1;
//        String lat = "-6.2611493";
//        String lon = "106.8776033";
        String productNote = "";
        String discId = "";
        String discName = "";
        String docId = "";
        String docName = "";

        listCart.put("cart_product_id", Utils.INSTANCE.createRequestBody(cartProductId));
        listCart.put("product_id", Utils.INSTANCE.createRequestBody(String.valueOf(idProduct)));
        listCart.put("product_name", Utils.INSTANCE.createRequestBody(productName));
        listCart.put("product_sku", Utils.INSTANCE.createRequestBody(productSku));
        listCart.put("tax_rate", Utils.INSTANCE.createRequestBody(taxtRate));
        listCart.put("category_id", Utils.INSTANCE.createRequestBody(String.valueOf(categoryId)));
        listCart.put("category_name", Utils.INSTANCE.createRequestBody(categoryName));
        listCart.put("product_price", Utils.INSTANCE.createRequestBody(String.valueOf(productPrice)));
        listCart.put("product_qty", Utils.INSTANCE.createRequestBody(String.valueOf(productQtyDefault)));
//        listCart.put("product_note", Utils.INSTANCE.createRequestBody(String.valueOf(productNote)));
//        listCart.put("discount_id", Utils.INSTANCE.createRequestBody(String.valueOf(discId)));
//        listCart.put("discount_name", Utils.INSTANCE.createRequestBody(String.valueOf(discName)));
//        listCart.put("doctor_id", Utils.INSTANCE.createRequestBody(String.valueOf(docId)));
//        listCart.put("doctor_name", Utils.INSTANCE.createRequestBody(String.valueOf(docName)));

        Call<DoPostV2> call = apiService.addCartProduct(auth, lat, lon, listCart);
        call.enqueue(new Callback<DoPostV2>() {
            @Override
            public void onResponse(@NonNull Call<DoPostV2> call, @NonNull Response<DoPostV2> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    DoPostV2 data = response.body();
                    if (response.code() == 200) {
                        showToast(data.getMessage());
                        getCountCart();
//                        responseData = data.getData();
//                        dataNewProductDetail();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoPostV2> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                }
            }
        });
    }

    private void getCountCart(){
        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();

//        showDialogProgress("Add to cart");
//        String lat = "-6.2611493";
//        String lon = "106.8776033";
        String custId = String.valueOf(DataManager.getInstance().getCustomerId());
        String custName = DataManager.getInstance().getName();
        int cleanCart = 0;

        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();

        list.put("customer_id", Utils.INSTANCE.createRequestBody(custId));
        list.put("customer_name", Utils.INSTANCE.createRequestBody(custName));
        list.put("clean_cart", Utils.INSTANCE.createRequestBody(String.valueOf(cleanCart)));

        Call<CartProduct> call = apiService.createCartProduct(auth, lat, lon, list);
        call.enqueue(new Callback<CartProduct>() {
            @Override
            public void onResponse(@NonNull Call<CartProduct> call, @NonNull Response<CartProduct> response) {
//                hideDialogProgress();
                if (response.isSuccessful()) {
                    CartProduct data = response.body();
                    if (response.code() == 200) {
                        if (data.getData().getItems().size() == 0){
                            cardCart.setVisibility(View.GONE);
                        }else{
                            cardCart.setVisibility(View.VISIBLE);
                            tvCountCart.setText(String.valueOf(data.getData().getItems().size()));
                        }
                        Log.e("TAG", "onResponse: " + data.getData().getItems().size() );
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CartProduct> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
//                    hideDialogProgress();
                }
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//        if (ivBack == view) {
//            finish();
//        }
//    }

}