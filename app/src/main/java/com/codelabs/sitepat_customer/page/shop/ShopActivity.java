package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.NewProductAdapter;
import com.codelabs.sitepat_customer.adapter.ProductAdapter;
import com.codelabs.sitepat_customer.adapter.TypeProductAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.utils.RecentUtils;
import com.codelabs.sitepat_customer.viewmodel.BrandSelected;
import com.codelabs.sitepat_customer.viewmodel.CartSelected;
import com.codelabs.sitepat_customer.viewmodel.NewProduct;
import com.codelabs.sitepat_customer.viewmodel.Product;
import com.codelabs.sitepat_customer.viewmodel.SortSelected;
import com.codelabs.sitepat_customer.viewmodel.TypeFilter;
import com.codelabs.sitepat_customer.viewmodel.TypeFilterSelected;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopActivity extends BaseActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_sort)
    AppCompatImageView ivSort;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_filter)
    AppCompatImageView ivFilter;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_cart)
    AppCompatImageView ivCart;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_see_more)
    AppCompatTextView tvSeeMore;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_search)
    AppCompatEditText etSearch;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_new_product)
    RecyclerView rvNewProduct;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_type_product)
    RecyclerView rvTypeProduct;



    NewProductAdapter newProductAdapter;
    ProductAdapter productAdapter;
    TypeProductAdapter typeProductAdapter;
    private String search = "";
    private Integer limit = 1000;
    private Integer limitNP = 20;
    private String minPrice = "";
    private String maxPrice = "";
    private Integer active = 1;
    private String type = "";
    private String sort = "";
    private String filterBrand = "";
    private String filterType = "";
//    private String sortAZ = "";
//    private String sortPrice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        initSetup();
        initView();
        fetchData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        fetchData();
    }

    private void initView() {
        newProductAdapter = new NewProductAdapter(getApplicationContext());
        rvNewProduct.setAdapter(newProductAdapter);
        rvNewProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (0 == rvNewProduct.getItemDecorationCount()){
            rvNewProduct.addItemDecoration(new RecentUtils.PaddingItemDecoration(40));
        }

        typeProductAdapter = new TypeProductAdapter(getApplicationContext());
        rvTypeProduct.setAdapter(typeProductAdapter);
        rvTypeProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (0 == rvTypeProduct.getItemDecorationCount()){
            rvTypeProduct.addItemDecoration(new RecentUtils.PaddingItemDecoration(30));
        }

        productAdapter = new ProductAdapter(getApplicationContext());
        rvProduct.setAdapter(productAdapter);
        rvProduct.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void fetchData(){
        searchData();
        loadNewProduct();
        loadType();
        loadProduct();
    }

    private void initSetup() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetSort bottomSheetSort = new BottomSheetSort();
                bottomSheetSort.show(getSupportFragmentManager(), "Dialog");
//                getSupportFragmentManager().beginTransaction().add(R.id.bottom_sheet_sort, new BottomSheetSort()).commit();
            }
        });
        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFilter bottomSheetFilter = new BottomSheetFilter();
                bottomSheetFilter.show(getSupportFragmentManager(), "Dialog");
            }
        });
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ShopActivity.this, CartActivity.class);
//                startActivity(intent);
                showToast("On Develop :(");
            }
        });
        tvSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, NewProductActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadNewProduct() {
        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();

        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<NewProduct> call = apiService.getNewProduct(auth, limitNP, active, search, filterBrand, filterType, minPrice, maxPrice, sort, lat, lon);
        call.enqueue(new Callback<NewProduct>() {
            @Override
            public void onResponse(@NonNull Call<NewProduct> call, @NonNull Response<NewProduct> response) {
                if (response.isSuccessful()) {
                    NewProduct data = response.body();
                    if (response.code() == 200) {
                        newProductAdapter.setData(data.getData().getItems());
//                        DataManager.getInstance().setCustomerId(data.getData().getItems().get(0).getProductId());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewProduct> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }

    public void loadType() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<TypeFilter> call = apiService.getType(auth);
        call.enqueue(new Callback<TypeFilter>() {
            @Override
            public void onResponse(@NonNull Call<TypeFilter> call, @NonNull Response<TypeFilter> response) {
                if (response.isSuccessful()) {
                    TypeFilter data = response.body();
                    if (response.code() == 200) {
                        typeProductAdapter.setData(data.getData());
//                        DataManager.getInstance().setCustomerId(data.getData().getItems().get(0).getProductId());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TypeFilter> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }

    public void loadProduct() {
//        String typee = getIntent().getStringExtra("item_selected");
//        if (typee == null){
//
//        }else{
//            type = typee;
//        }
        String min = DataManager.getInstance().getMinPrice();
        String max = DataManager.getInstance().getMaxPrice();

        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();

        minPrice = min;
        maxPrice = max;

        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Product> call = apiService.getProduct(auth, limit, active, search, filterBrand, type, minPrice, maxPrice, sort, lat, lon);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                if (response.isSuccessful()) {
                    Product data = response.body();
                    if (response.code() == 200) {
                        productAdapter.setData(data.getData().getItems());
//                        DataManager.getInstance().setCustomerId(data.getData().getItems().get(0).getProductId());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Product> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }

    public void searchData(){
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = s.toString();
                loadProduct();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
//                    etSearch.hideKeyboard();
                    hideKeyboard();
                    etSearch.clearFocus();
                    return true;
                }
                return false;
            }
        });
    }

    @Subscribe
    public void onItemSelected(CartSelected cartSelected){
        type = cartSelected.productType;
        loadProduct();
    }

    @Subscribe
    public void onSort(SortSelected sortSelected){
        sort = sortSelected.sortSelect;
        loadProduct();
    }

    @Subscribe
    public void onFilterBrand(BrandSelected brandSelected){
        filterBrand = brandSelected.brandSelect;
        loadProduct();
    }

    @Subscribe
    public void onFilterType(TypeFilterSelected typeFilterSelected){
        type = typeFilterSelected.typeFilterSelect;
        loadProduct();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}