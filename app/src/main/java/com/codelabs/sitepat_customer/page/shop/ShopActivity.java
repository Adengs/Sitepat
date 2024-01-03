package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akiniyalocts.pagingrecycler.PagingDelegate;
import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.NewProductAdapter;
import com.codelabs.sitepat_customer.adapter.ProductAdapter;
import com.codelabs.sitepat_customer.adapter.TypeFilterShopAdapter;
import com.codelabs.sitepat_customer.adapter.TypeProductAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.helper.Utils;
import com.codelabs.sitepat_customer.utils.RecentUtils;
import com.codelabs.sitepat_customer.viewmodel.BrandSelected;
import com.codelabs.sitepat_customer.viewmodel.CartProduct;
import com.codelabs.sitepat_customer.viewmodel.CartSelected;
import com.codelabs.sitepat_customer.viewmodel.NewProduct;
import com.codelabs.sitepat_customer.viewmodel.Product;
import com.codelabs.sitepat_customer.viewmodel.SortSelected;
import com.codelabs.sitepat_customer.viewmodel.TypeFilter;
import com.codelabs.sitepat_customer.viewmodel.TypeFilterSelected;
import com.codelabs.sitepat_customer.viewmodel.TypeFilterShop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
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
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.card_cart)
    CardView cardCart;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_count_cart)
    TextView tvCountCart;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.scrol_view)
    NestedScrollView scrollView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progress_view)
    ProgressBar progressBar;


    private static final int MAX_ITEM = 25;
//    private int page = 1;
//    private int totalPage = 1;
//    private int limit = 15;
    RecyclerView recyclerView;
    boolean isLoading = false;
//    Set<Product.ItemsEntity> rowArrayList = new HashSet<>();
    private ArrayList<Product.ItemsEntity> rowsArrayList = new ArrayList<>();
    private HashMap<String, RequestBody> list = new HashMap<>();

    NewProductAdapter newProductAdapter;
    ProductAdapter productAdapter;
    TypeFilterShopAdapter typeProductAdapter;
    private String search = "";
    private Integer limit = 0;
    private Integer limitNP = 20;
    private String minPrice = "";
    private String maxPrice = "";
    private Integer active = 1;
    private String type = "";
    private String sort = "";
    private String filterBrand = "";
    private String filterType = "";
    private int limit2 = 100;
//    private String sortAZ = "";
//    private String sortPrice = "";

    private int page = 1, totalPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        initSetup();
        initView();
        fetchData();
//        initScrollListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        fetchData();
        getCountCart();
//        initScrollListener();
    }

    private void initView() {
        newProductAdapter = new NewProductAdapter(ShopActivity.this);
        rvNewProduct.setAdapter(newProductAdapter);
        rvNewProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (0 == rvNewProduct.getItemDecorationCount()){
            rvNewProduct.addItemDecoration(new RecentUtils.PaddingItemDecoration(40));
        }

        typeProductAdapter = new TypeFilterShopAdapter(ShopActivity.this);
        rvTypeProduct.setAdapter(typeProductAdapter);
        rvTypeProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (0 == rvTypeProduct.getItemDecorationCount()){
            rvTypeProduct.addItemDecoration(new RecentUtils.PaddingItemDecoration(30));
        }

//        productAdapter = new ProductAdapter(ShopActivity.this, rowsArrayList);
        productAdapter = new ProductAdapter(ShopActivity.this);

//        PagingDelegate pagingDelegate = new PagingDelegate.Builder(productAdapter)
//                .attachTo(rvProduct)
//                .listenWith(this)
//                .build();

        rvProduct.setAdapter(productAdapter);
        rvProduct.setLayoutManager(new GridLayoutManager(this, 2));

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rvProduct.getLayoutManager();
//                int visibleItemCount = linearLayoutManager.getChildCount();
//                int pastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                int total = productAdapter.getItemCount();
//
//                if (!isLoading && page < totalPage) {
////                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == rowsArrayList.size() - 1) {
//                    if (visibleItemCount + pastVisibleItem >= total) {
//                        //bottom of list!
//                        page++;
//                        progressBar.setVisibility(View.VISIBLE);
//                        loadProduct();
//                        isLoading = true;
//                    }
//                }


                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    if (page > totalPage){
                        Toast.makeText(context, "That's all the data..", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }else{
                        loadProduct();
                        Log.e("TAG", "onScrollChange: " + totalPage );
                    }
                }
            }
        });
    }

    private void fetchData(){
        searchData();
        loadNewProduct();
        loadType();
        loadProduct();
        getCountCart();
//        initScrollListener();
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
                Intent intent = new Intent(ShopActivity.this, CartActivity.class);
                startActivity(intent);
                getCartProductId();
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
//        showDialogProgress("Getting New Product");
        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();

        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<NewProduct> call = apiService.getNewProduct(auth, limitNP, active, search, filterBrand, filterType, minPrice, maxPrice, sort, lat, lon);
        call.enqueue(new Callback<NewProduct>() {
            @Override
            public void onResponse(@NonNull Call<NewProduct> call, @NonNull Response<NewProduct> response) {
//                hideDialogProgress();
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
//                    hideDialogProgress();
                    t.printStackTrace();
                }
            }
        });
    }

    public void loadType() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<TypeFilterShop> call = apiService.getTypeShop(auth);
        call.enqueue(new Callback<TypeFilterShop>() {
            @Override
            public void onResponse(@NonNull Call<TypeFilterShop> call, @NonNull Response<TypeFilterShop> response) {
                if (response.isSuccessful()) {
                    TypeFilterShop data = response.body();
                    if (response.code() == 200) {
                        typeProductAdapter.setData(data.getData().getItems());
//                        DataManager.getInstance().setCustomerId(data.getData().getItems().get(0).getProductId());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TypeFilterShop> call,@NonNull Throwable t) {
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

//        showDialogProgress("Getting Product");
        String min = DataManager.getInstance().getMinPrice();
        String max = DataManager.getInstance().getMaxPrice();

        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();

        minPrice = min;
        maxPrice = max;

        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Product> call = apiService.getProduct(auth, limit, active, search, filterBrand, type, minPrice, maxPrice, sort, lat, lon, page);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
//                hideDialogProgress();
                if (response.isSuccessful()) {
                    Product data = response.body();
                    if (response.code() == 200) {
                        if (page == 1){
                            totalPage = data.getData().getLastPage();
                            rowsArrayList.clear();
                            rowsArrayList.addAll(data.getData().getItems());
                            productAdapter.setData(rowsArrayList);
                            progressBar.setVisibility(View.GONE);
                            isLoading = false;
                        }else {
                            totalPage = data.getData().getLastPage();
                            rowsArrayList.addAll(data.getData().getItems());
//                            ArrayList<Product.ItemsEntity> rowsArrayList = new ArrayList<>(rowArrayList);
                            productAdapter.setData(rowsArrayList);
                            progressBar.setVisibility(View.GONE);
                            isLoading = false;

                            Log.e("TAG", "list size: " + rowsArrayList.size());
                        }





//                        int i = 0;
//                        while (i < 10){
//                            rowsArrayList.add(data.getData().getItems().get(i));
////                            data.getData().getItems().size();
////                            productAdapter.setData(data.getData().getItems());
//                            i++;
//
//                            productAdapter.setData(rowsArrayList);
//                            Log.e("TAG", "cek output 1: " + rowsArrayList.size() );
//                        }
//                        initScrollListener();



//                        progressBar.setVisibility(View.GONE);
//                        try {
//                            JSONArray jsonArray = new JSONArray(response.body());
//                            parseResult(jsonArray);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Log.e("TAG", "25 Januari: " + rowsArrayList.size() );
//                        productAdapter.setData(rowsArrayList);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Product> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
//                    hideDialogProgress();
                    t.printStackTrace();
                }
            }
        });
    }

//    private void parseResult(JSONArray jsonArray) {
//        for (int i = 0; i < jsonArray.length(); i++) {
//            try {
//                JSONObject object = jsonArray.getJSONObject(i);
//                Product data = new Product();
//                data.getData().getItems().get(i).setProductPrice(Integer.parseInt(object.getString("product_price")));
//                data.getData().getItems().get(i).setProductImage(object.getString("product_image"));
//                data.getData().getItems().get(i).setProductName(object.getString("product_name"));
//                data.getData().getItems().get(i).getCategory().setCategoryName(object.getString("category_name"));
//
//                rowsArrayList.add(data.getData().getItems().get(i));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
////            productAdapter = new ProductAdapter(ShopActivity.this, rowsArrayList);
////            rvProduct.setAdapter(productAdapter);
//        }
//    }
//
//    private void initScrollListener() {
//        rvProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//
//                if (!isLoading) {
//                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == rowsArrayList.size() - 1) {
//                        //bottom of list!
//                        loadMore();
//                        isLoading = true;
//                    }
//                }
//            }
//        });
//
//
//    }
//
//    private void loadMore() {
//        rowsArrayList.add(null);
//        productAdapter.notifyItemInserted(rowsArrayList.size() - 1);
//
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                rowsArrayList.remove(rowsArrayList.size() - 1);
//                int scrollPosition = rowsArrayList.size();
//                productAdapter.notifyItemRemoved(scrollPosition);
//                int currentSize = scrollPosition;
//                int nextLimit = currentSize + 5;
//
//                while (currentSize - 1 < nextLimit) {
//                    rowsArrayList.add(productAdapter.productList.get(currentSize));
////                    rowsArrayList.add("Item " + currentSize);
//                    currentSize++;
//
//                    productAdapter.setData(rowsArrayList);
//                    Log.e("TAG", "cek output 2: " + rowsArrayList.size() );
//                }
//
//                productAdapter.notifyDataSetChanged();
//                isLoading = false;
//            }
//        }, 2000);
//
//
//    }

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

    private void getCountCart(){
        String lat = DataManager.getInstance().getLatitude();
        String lon = DataManager.getInstance().getLongitude();

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
                if (response.isSuccessful()) {
                    CartProduct data = response.body();
                    if (response.code() == 200) {
                            Log.e("TAG", "Size item cart: " + data.getData().getItems().size());
                            if (data.getData().getItems().size() == 0) {
                                cardCart.setVisibility(View.GONE);
                            } else {
                                cardCart.setVisibility(View.VISIBLE);
                                tvCountCart.setText(String.valueOf(data.getData().getItems().size()));
                            }
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CartProduct> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                }
            }
        });
    }

    @Subscribe
    public void onItemSelected(CartSelected cartSelected){
        type = cartSelected.productType;
        page = 1;
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

//    @Override
//    public void onPage(int i) {
//        progressBar.setVisibility(View.VISIBLE);
//        if (i < MAX_ITEM){
//            new Handler(Looper.myLooper())
//                    .postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            int lastSize = productAdapter.getPagingItemCount();
//                            progressBar.setVisibility(View.GONE);
//                            productAdapter.addNewItem(5);
//                        }
//                    }, 1500);
//        }
//    }
//
//    @Override
//    public void onDonePaging() {
//
//    }
}