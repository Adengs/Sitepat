package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.CartProductAdapter;
import com.codelabs.sitepat_customer.adapter.ProductAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.helper.Utils;
import com.codelabs.sitepat_customer.viewmodel.CartProduct;
import com.codelabs.sitepat_customer.viewmodel.DeleteProduct;
import com.codelabs.sitepat_customer.viewmodel.DoPostV2;
import com.codelabs.sitepat_customer.viewmodel.SaveOutletInformation;
import com.codelabs.sitepat_customer.viewmodel.TypeService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends BaseActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_promo)
    AppCompatEditText etPromo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_apply_orange)
    AppCompatTextView tvOrange;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.apply_promo)
    AppCompatTextView applyPromo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.price_total)
    TextView priceTotal;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.discount)
    TextView discount;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.price_after_discount)
    TextView priceAfterDiscount;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.price)
    TextView priceAll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_payment)
    AppCompatTextView btnPay;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_cart)
    RecyclerView rvCart;

    private int productIdP = 0;
    private String nameProductP = "";
    private String skuProductP = "";
    private String rateTaxtP = "";
    private int idCategoryP = 0;
    private String nameCategoryP = "";
    private int priceProductP = 0;
    private int qtyProductP = 0;
    private int productIdM = 0;
    private String nameProductM = "";
    private String skuProductM = "";
    private String rateTaxtM = "";
    private int idCategoryM = 0;
    private String nameCategoryM = "";
    private int priceProductM = 0;
    private int qtyProductM = 0;
    private int siteID = 0;
    private int productId = 0;

    CartProductAdapter cartProductAdapter;
    private HashMap<String, RequestBody> list = new HashMap<>();
    private HashMap<String, RequestBody> listP = new HashMap<>();
    private HashMap<String, RequestBody> listM = new HashMap<>();
    private List<CartProduct> productList = new ArrayList<>();

    private DialogDeleteProduct dialogDeleteProduct = new DialogDeleteProduct();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();
    }

    private void initSetup() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToast("On Develop :(");
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        etPromo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                keyword = s.toString();
                if (s.length() > 0){
                    applyPromo.setVisibility(View.GONE);
                    tvOrange.setVisibility(View.VISIBLE);
                }
                if (s.length() < 1){
                    applyPromo.setVisibility(View.VISIBLE);
                    tvOrange.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Promo not available now");
                hideKeyboard();
            }
        });

        cartProductAdapter.onPlus = new CartProductAdapter.OnPlus() {
            @Override
            public void onPlus(int idProduct, String productName, String productSku, String taxtRate, int categoryId, String categoryName, int productPrice, int produckQty, int siteId) {
                productIdP = idProduct;
                nameProductP = productName;
                skuProductP = productSku;
                rateTaxtP = taxtRate;
                idCategoryP = categoryId;
                nameCategoryP = categoryName;
                priceProductP = productPrice;
                qtyProductP = produckQty + 1;
                siteID = siteId;
                addToCart();
            }
        };

        cartProductAdapter.onMin = new CartProductAdapter.OnMin() {
            @Override
            public void onMin(int idProduct, String productName, String productSku, String taxtRate, int categoryId, String categoryName, int productPrice, int produckQty, int siteId) {
                productIdM = idProduct;
                nameProductM = productName;
                skuProductM = productSku;
                rateTaxtM = taxtRate;
                idCategoryM = categoryId;
                nameCategoryM = categoryName;
                priceProductM = productPrice;
                qtyProductM = produckQty - 1;
                siteID = siteId;
                if (produckQty == 1){
                    deleteProduct();
                }else{
                    minFromCart();
                }
            }
        };

        cartProductAdapter.onTrash = new CartProductAdapter.OnTrash() {
            @Override
            public void onTrash(int idProduct, int siteId) {
                productId = idProduct;
                siteID = siteId;

                dialogDeleteProduct.show(getSupportFragmentManager(), dialogDeleteProduct.getTag());
//                trashProduct();
            }
        };
    }

    private void initView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        cartProductAdapter = new CartProductAdapter(CartActivity.this);
        rvCart.setAdapter(cartProductAdapter);
        rvCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
    }

    private void fetchData(){
        getCartProduct();
    }

    private void getCartProduct(){
//        String lat = DataManager.getInstance().getLatitude();
//        String lon = DataManager.getInstance().getLongitude();
        showDialogProgress("Getting Cart Product");

        String lat = "-6.2611493";
        String lon = "106.8776033";
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
                NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));

                hideDialogProgress();
                if (response.isSuccessful()) {
                    CartProduct data = response.body();
                    if (response.code() == 200) {
//                        showToast(String.valueOf(data.getData().getItems().size()));
//                        for (int i = 0; i < data.getData().getItems().size(); i++) {
//                            String totalPrice = rupiah.format(new BigDecimal(data.getData().getItems().get(i).getTotal()));
//                            String discountt = rupiah.format(new BigDecimal(data.getData().getItems().get(i).getDiscountAmount()));
//                            String afterDiscountPrice = rupiah.format(new BigDecimal(data.getData().getItems().get(i).getGrandTotal()));
//
//                            priceTotal.setText(totalPrice.replace(",00","").replace("Rp",""));
//                            discount.setText(discountt.replace(",00","").replace("Rp",""));
//                            priceAfterDiscount.setText(afterDiscountPrice.replace(",00","").replace("Rp",""));
//                        }
                        String totalPrice = rupiah.format(new BigDecimal(data.getData().getTotalTransactionBeforeDiscount()));
//                        String discountt = rupiah.format(new BigDecimal(data.getData().getItems().get(i).getDiscountAmount()));
                        String afterDiscountPrice = rupiah.format(new BigDecimal(data.getData().getPaymentAmountSuggestion()));
                        String allPrice = rupiah.format(new BigDecimal(data.getData().getPaymentAmountSuggestion()));

                        priceTotal.setText(totalPrice.replace(",00","").replace("Rp",""));
//                        discount.setText(discountt.replace(",00","").replace("Rp",""));
                        priceAfterDiscount.setText(afterDiscountPrice.replace(",00","").replace("Rp",""));
                        cartProductAdapter.setData(data.getData().getItems());
                        Log.e("TAG", "cek size cart: " + data.getData().getItems().size() );

                        priceAll.setText(allPrice.replace(",00","").replace("Rp",""));
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

    private void addToCart(){
        RetrofitInterface apiService = ApiUtils.getApiService();
        String cartProductId = String.valueOf(DataManager.getInstance().getCartProduct());
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
//        String lat = DataManager.getInstance().getLatitude();
//        String lon = DataManager.getInstance().getLongitude();
//        int productQtyDefault = qtyProduct + 1;
//        int price = priceProduct * productQtyDefault;
        String lat = "-6.2611493";
        String lon = "106.8776033";
        String productNote = "";
        String discId = "";
        String discName = "";
        String docId = "";
        String docName = "";

        listP.put("cart_product_id", Utils.INSTANCE.createRequestBody(cartProductId));
        listP.put("product_id", Utils.INSTANCE.createRequestBody(String.valueOf(productIdP)));
        listP.put("product_name", Utils.INSTANCE.createRequestBody(nameProductP));
        listP.put("product_sku", Utils.INSTANCE.createRequestBody(skuProductP));
        listP.put("tax_rate", Utils.INSTANCE.createRequestBody(rateTaxtP));
        listP.put("category_id", Utils.INSTANCE.createRequestBody(String.valueOf(idCategoryP)));
        listP.put("category_name", Utils.INSTANCE.createRequestBody(nameCategoryP));
        listP.put("product_price", Utils.INSTANCE.createRequestBody(String.valueOf(priceProductP)));
        listP.put("product_qty", Utils.INSTANCE.createRequestBody(String.valueOf(qtyProductP)));
//        listCart.put("product_note", Utils.INSTANCE.createRequestBody(String.valueOf(productNote)));
//        listCart.put("discount_id", Utils.INSTANCE.createRequestBody(String.valueOf(discId)));
//        listCart.put("discount_name", Utils.INSTANCE.createRequestBody(String.valueOf(discName)));
//        listCart.put("doctor_id", Utils.INSTANCE.createRequestBody(String.valueOf(docId)));
//        listCart.put("doctor_name", Utils.INSTANCE.createRequestBody(String.valueOf(docName)));

        Call<DoPostV2> call = apiService.addCartProduct(auth, lat, lon, listP);
        call.enqueue(new Callback<DoPostV2>() {
            @Override
            public void onResponse(@NonNull Call<DoPostV2> call, @NonNull Response<DoPostV2> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    DoPostV2 data = response.body();
                    if (response.code() == 200) {
                        getCartProduct();
//                        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
//                        String formatRupiah = rupiah.format(new BigDecimal(priceProductP));
//
//                        priceAll.setText(formatRupiah.replace(",00","").replace("Rp",""));
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

    private void minFromCart(){
        RetrofitInterface apiService = ApiUtils.getApiService();
        String cartProductId = String.valueOf(DataManager.getInstance().getCartProduct());
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
//        String lat = DataManager.getInstance().getLatitude();
//        String lon = DataManager.getInstance().getLongitude();
//        int productQtyDefault = qtyProduct - 1;
//        int price = priceProduct * productQtyDefault;
        String lat = "-6.2611493";
        String lon = "106.8776033";
        String productNote = "";
        String discId = "";
        String discName = "";
        String docId = "";
        String docName = "";

        listM.put("cart_product_id", Utils.INSTANCE.createRequestBody(cartProductId));
        listM.put("product_id", Utils.INSTANCE.createRequestBody(String.valueOf(productIdM)));
        listM.put("product_name", Utils.INSTANCE.createRequestBody(nameProductM));
        listM.put("product_sku", Utils.INSTANCE.createRequestBody(skuProductM));
        listM.put("tax_rate", Utils.INSTANCE.createRequestBody(rateTaxtM));
        listM.put("category_id", Utils.INSTANCE.createRequestBody(String.valueOf(idCategoryM)));
        listM.put("category_name", Utils.INSTANCE.createRequestBody(nameCategoryM));
        listM.put("product_price", Utils.INSTANCE.createRequestBody(String.valueOf(priceProductM)));
        listM.put("product_qty", Utils.INSTANCE.createRequestBody(String.valueOf(qtyProductM)));
//        listCart.put("product_note", Utils.INSTANCE.createRequestBody(String.valueOf(productNote)));
//        listCart.put("discount_id", Utils.INSTANCE.createRequestBody(String.valueOf(discId)));
//        listCart.put("discount_name", Utils.INSTANCE.createRequestBody(String.valueOf(discName)));
//        listCart.put("doctor_id", Utils.INSTANCE.createRequestBody(String.valueOf(docId)));
//        listCart.put("doctor_name", Utils.INSTANCE.createRequestBody(String.valueOf(docName)));

        Call<DoPostV2> call = apiService.addCartProduct(auth, lat, lon, listM);
        call.enqueue(new Callback<DoPostV2>() {
            @Override
            public void onResponse(@NonNull Call<DoPostV2> call, @NonNull Response<DoPostV2> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    DoPostV2 data = response.body();
                    if (response.code() == 200) {
                        getCartProduct();
//                        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
//                        String formatRupiah = rupiah.format(new BigDecimal(priceProductM));
//
//                        priceAll.setText(formatRupiah.replace(",00","").replace("Rp",""));
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

    private void deleteProduct() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String cartProductId = String.valueOf(DataManager.getInstance().getCartProduct());
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
//        String lat = DataManager.getInstance().getLatitude();
//        String lon = DataManager.getInstance().getLongitude();
//        int productQtyDefault = qtyProduct - 1;
//        int price = priceProduct * productQtyDefault;
        String lat = "-6.2611493";
        String lon = "106.8776033";
        String productNote = "";
        String discId = "";
        String discName = "";
        String docId = "";
        String docName = "";

        list.put("cart_product_id", Utils.INSTANCE.createRequestBody(cartProductId));
        list.put("product_id", Utils.INSTANCE.createRequestBody(String.valueOf(productIdM)));
        list.put("site_id", Utils.INSTANCE.createRequestBody(String.valueOf(siteID)));

        Call<DoPostV2> call = apiService.deleteCartProduct(auth, lat, lon, list);
        call.enqueue(new Callback<DoPostV2>() {
            @Override
            public void onResponse(@NonNull Call<DoPostV2> call, @NonNull Response<DoPostV2> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    DoPostV2 data = response.body();
                    if (response.code() == 200) {
                        showToast(data.getMessage());
                        getCartProduct();
//                        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
//                        String formatRupiah = rupiah.format(new BigDecimal(priceProductM));
//
//                        priceAll.setText(formatRupiah.replace(",00","").replace("Rp",""));
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

    private void trashProduct() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String cartProductId = String.valueOf(DataManager.getInstance().getCartProduct());
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
//        String lat = DataManager.getInstance().getLatitude();
//        String lon = DataManager.getInstance().getLongitude();
//        int productQtyDefault = qtyProduct - 1;
//        int price = priceProduct * productQtyDefault;
        String lat = "-6.2611493";
        String lon = "106.8776033";
        String productNote = "";
        String discId = "";
        String discName = "";
        String docId = "";
        String docName = "";

        list.put("cart_product_id", Utils.INSTANCE.createRequestBody(cartProductId));
        list.put("product_id", Utils.INSTANCE.createRequestBody(String.valueOf(productId)));
        list.put("site_id", Utils.INSTANCE.createRequestBody(String.valueOf(siteID)));

        Call<DoPostV2> call = apiService.deleteCartProduct(auth, lat, lon, list);
        call.enqueue(new Callback<DoPostV2>() {
            @Override
            public void onResponse(@NonNull Call<DoPostV2> call, @NonNull Response<DoPostV2> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    DoPostV2 data = response.body();
                    if (response.code() == 200) {
                        showToast(data.getMessage());
                        getCartProduct();
//                        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
//                        String formatRupiah = rupiah.format(new BigDecimal(priceProductM));
//
//                        priceAll.setText(formatRupiah.replace(",00","").replace("Rp",""));
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

    @Subscribe
    public void onDeleteProduct(DeleteProduct deleteProduct){
        trashProduct();
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