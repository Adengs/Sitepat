package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.NewProductAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.viewmodel.BrandSelected;
import com.codelabs.sitepat_customer.viewmodel.NewProduct;
import com.codelabs.sitepat_customer.viewmodel.SortSelected;
import com.codelabs.sitepat_customer.viewmodel.TypeFilterSelected;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewProductActivity extends BaseActivity {

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
    @BindView(R.id.et_search)
    AppCompatEditText etSearch;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;

    NewProductAdapter newProductAdapter;
    private String keyword = "";
    private String sort = "";
    private Integer limit = 1000;
    private String minPrice = "";
    private String maxPrice = "";
    private Integer active = 1;
    private String filterBrand = "";
    private String filterType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        ButterKnife.bind(this);
        initSetup();
        initView();
        fetchData();
    }

    private void initView() {
        newProductAdapter = new NewProductAdapter(getApplicationContext());
        rvProduct.setAdapter(newProductAdapter);
        rvProduct.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void fetchData(){
        loadNewProduct();
        searchData();
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
                showToast("On Develov :(");
//                Intent intent = new Intent(NewProductActivity.this, CartActivity.class);
//                startActivity(intent);
            }
        });
    }

    public void loadNewProduct() {
        String min = DataManager.getInstance().getMinPrice();
        String max = DataManager.getInstance().getMaxPrice();

        if(min.equals("")){
            minPrice = "";
        }else{
            minPrice = min;
        }
        if(max.equals("")){
            maxPrice = "";
        }else{
            maxPrice = max;
        }

        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();

        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<NewProduct> call = apiService.getNewProduct(auth,  limit, active, keyword, filterBrand, filterType, minPrice, maxPrice, sort, lat, lon);
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

    public void searchData(){
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = s.toString();
                loadNewProduct();
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
    public void onSort(SortSelected sortSelected){
        sort = sortSelected.sortSelect;
        loadNewProduct();
    }

    @Subscribe
    public void onFilterBrand(BrandSelected brandSelected){
        filterBrand = brandSelected.brandSelect;
        loadNewProduct();
    }

    @Subscribe
    public void onFilterType(TypeFilterSelected typeFilterSelected){
        filterType = typeFilterSelected.typeFilterSelect;
        loadNewProduct();
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