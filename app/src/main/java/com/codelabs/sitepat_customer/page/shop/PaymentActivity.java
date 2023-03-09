package com.codelabs.sitepat_customer.page.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.EWalletAdapter;
import com.codelabs.sitepat_customer.adapter.TypeServiceChosessHome;
import com.codelabs.sitepat_customer.adapter.VirtualAccountAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.helper.Utils;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.BottomSheetAddService;
import com.codelabs.sitepat_customer.viewmodel.CartProduct;
import com.codelabs.sitepat_customer.viewmodel.ContactInformation;
import com.codelabs.sitepat_customer.viewmodel.ContactOutletInformation;
import com.codelabs.sitepat_customer.viewmodel.CreateInvoice;
import com.codelabs.sitepat_customer.viewmodel.OutletListSelected;
import com.codelabs.sitepat_customer.viewmodel.PaymentMethod;
import com.codelabs.sitepat_customer.viewmodel.Profile;
import com.codelabs.sitepat_customer.viewmodel.SaveContactInformation;
import com.codelabs.sitepat_customer.viewmodel.SaveOutletInformation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends BaseActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_edit)
    AppCompatTextView btnEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_complete)
    AppCompatTextView btnComplete;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_con_pay)
    AppCompatTextView conPay;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.name_cust)
    TextView tvName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.contact_cust)
    TextView tvContact;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_detail)
    TextView showDetail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_less)
    TextView showLess;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.price)
    TextView tvPrice;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.pm_price)
    TextView pmPrice;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.discount)
    TextView discount;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.total)
    TextView tvTotal;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_detail)
    LinearLayout layDetail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.virtual_account)
    LinearLayout layVA;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.open_arrow)
    AppCompatImageView openArrow;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.close_arrow)
    AppCompatImageView closeArrow;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_VA)
    RecyclerView rvVa;
    @BindView(R.id.e_wallet)
    LinearLayout layEw;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.open_arrow_ew)
    AppCompatImageView openArrowEw;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.close_arrow_ew)
    AppCompatImageView closeArrowEw;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_EW)
    RecyclerView rvEw;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.card_location)
    CardView cardLocation;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.outlet_location)
    TextView outletLocation;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.time)
    TextView tvTime;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.date)
    TextView tvDate;

    VirtualAccountAdapter virtualAccountAdapter;
    EWalletAdapter eWalletAdapter;

    private BottomSheetContact bottomSheetContact = new BottomSheetContact();
    private BottomSheetOutlet bottomSheetOutlet = new BottomSheetOutlet();
    private HashMap<String, RequestBody> list = new HashMap<>();

    String name = "";
    String address = "";
    String contact = "";
    String email = "";
    String province = "";
    String city = "";
    String outletName = "";
    String date = "";
    String time = "";

    //value create invoice
    String price = "";
    String transactionId = "";
    String siteId = "";
    String customerId = "";
    String customerName = "";
    String linkSuccess = "http://13.230.37.127/cart/public/api/v1/payment/redirect-mobile?";
    String linkFailure = "http://13.230.37.127/cart/public/api/v1/payment/redirect-mobile?";
    String description = "No Description";
    String platform = "android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();

        layDetail.setVisibility(View.GONE);
        showLess.setVisibility(View.GONE);
        showDetail.setVisibility(View.VISIBLE);
        rvVa.setVisibility(View.GONE);
        rvEw.setVisibility(View.GONE);

        if (DataManager.getInstance().getProvinceOutlet().equals("")){

        }else{
            btnComplete.setVisibility(View.GONE);
            cardLocation.setVisibility(View.VISIBLE);
            outletLocation.setText(DataManager.getInstance().getOutletOutlet());
            tvTime.setText(DataManager.getInstance().getTimeOutlet());
            tvDate.setText(DataManager.getInstance().getDateOutlet());
        }
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        rvVa.setLayoutManager(new LinearLayoutManager(this));
        virtualAccountAdapter = new VirtualAccountAdapter(this);
        rvVa.setAdapter(virtualAccountAdapter);

        rvEw.setLayoutManager(new LinearLayoutManager(this));
        eWalletAdapter = new EWalletAdapter(this);
        rvEw.setAdapter(eWalletAdapter);
    }

    private void initSetup() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().setNameContact("");
                DataManager.getInstance().setAddressContact("");
                DataManager.getInstance().setContactContact("");
                DataManager.getInstance().setemailContact("");
                DataManager.getInstance().setProvinceOutlet("");
                DataManager.getInstance().setCityOutlet("");
                DataManager.getInstance().setOutletOutlet("");
                DataManager.getInstance().setDateOutlet("");
                DataManager.getInstance().setTimeOutlet("");
                DataManager.getInstance().setDateOutletDb("");
                DataManager.getInstance().setTimeOutletDb("");
                finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetContact.show(getSupportFragmentManager(), bottomSheetContact.getTag());
//                showToast("On develop, Waiting API");
            }
        });
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetOutlet.show(getSupportFragmentManager(), bottomSheetOutlet.getTag());
//                showToast("On develop, Waiting API");
            }
        });
        conPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataManager.getInstance().getProvinceOutlet().equals("")){
                    Toast.makeText(v.getContext(), "Outlet not selected", Toast.LENGTH_SHORT).show();
                }else{
                    confirmPayment();
                    DataManager.getInstance().setNameContact("");
                    DataManager.getInstance().setAddressContact("");
                    DataManager.getInstance().setContactContact("");
                    DataManager.getInstance().setemailContact("");
                    DataManager.getInstance().setProvinceOutlet("");
                    DataManager.getInstance().setCityOutlet("");
                    DataManager.getInstance().setOutletOutlet("");
                    DataManager.getInstance().setDateOutlet("");
                    DataManager.getInstance().setTimeOutlet("");
                    DataManager.getInstance().setDateOutletDb("");
                    DataManager.getInstance().setTimeOutletDb("");
                }
//                showToast("On Develov :(");
//                Intent intent = new Intent(PaymentActivity.this, ShopActivity.class);
//                startActivity(intent);
            }
        });
        showDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layDetail.setVisibility(View.VISIBLE);
                showLess.setVisibility(View.VISIBLE);
                showDetail.setVisibility(View.GONE);
            }
        });

        showLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layDetail.setVisibility(View.GONE);
                showLess.setVisibility(View.GONE);
                showDetail.setVisibility(View.VISIBLE);
            }
        });

        openArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openArrow.setVisibility(View.GONE);
                rvVa.setVisibility(View.VISIBLE);
                closeArrow.setVisibility(View.VISIBLE);
            }
        });

        closeArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openArrow.setVisibility(View.VISIBLE);
                rvVa.setVisibility(View.GONE);
                closeArrow.setVisibility(View.GONE);
            }
        });

        openArrowEw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openArrowEw.setVisibility(View.GONE);
                rvEw.setVisibility(View.VISIBLE);
                closeArrowEw.setVisibility(View.VISIBLE);
            }
        });

        closeArrowEw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openArrowEw.setVisibility(View.VISIBLE);
                rvEw.setVisibility(View.GONE);
                closeArrowEw.setVisibility(View.GONE);
            }
        });
    }

    private void fetchData(){
        loadContactInformation();
        getCartProduct();
        loadVA();
        loadEW();
    }

    public void loadContactInformation(){
        showDialogProgress("Getting data customer");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<ContactInformation> call = apiService.getContactInformation(auth);
        call.enqueue(new Callback<ContactInformation>() {
            @Override
            public void onResponse(@NonNull Call<ContactInformation> call, @NonNull Response<ContactInformation> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    ContactInformation data = response.body();
                    if (response.code() == 200) {
                        tvName.setText(data.getData().getName());
                        tvContact.setText(data.getData().getContact());

                        DataManager.getInstance().setNameContact(data.getData().getName());
                        DataManager.getInstance().setAddressContact(data.getData().getAddress());
                        DataManager.getInstance().setContactContact(data.getData().getContact());
                        DataManager.getInstance().setemailContact(data.getData().getEmail());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
//                    Toast.makeText(this, error.message(), Toast.LENGTH_LONG).show();
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ContactInformation> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    private void getCartProduct(){
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
                        String totalPrice = rupiah.format(new BigDecimal(data.getData().getPaymentAmountSuggestion()));
//                        String discountt = rupiah.format(new BigDecimal(data.getData().getItems().get(i).getDiscountAmount()));
                        String afterDiscountPrice = rupiah.format(new BigDecimal(data.getData().getTotalTransactionBeforeDiscount()));
                        String allPrice = rupiah.format(new BigDecimal(data.getData().getPaymentAmountSuggestion()));

                        tvPrice.setText(totalPrice.replace(",00","").replace("Rp",""));
//                        discount.setText(discountt.replace(",00","").replace("Rp",""));
                        pmPrice.setText(afterDiscountPrice.replace(",00","").replace("Rp",""));
//                        cartProductAdapter.setData(data.getData().getItems());

                        tvTotal.setText(allPrice.replace(",00","").replace("Rp",""));


//                        price = allPrice.replace(",00","").replace("Rp","");
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

    public void loadVA(){
//        showDialogProgress("Getting List Virtual Account");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        String categoryVa = "va";
        Call<PaymentMethod> call = apiService.getListPayment(auth, categoryVa);
        call.enqueue(new Callback<PaymentMethod>() {
            @Override
            public void onResponse(@NonNull Call<PaymentMethod> call, @NonNull Response<PaymentMethod> response) {
//                hideDialogProgress();
                if (response.isSuccessful()) {
                    PaymentMethod data = response.body();
                    if (response.code() == 200) {
                        virtualAccountAdapter.setData(data.getData());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
//                    Toast.makeText(this, error.message(), Toast.LENGTH_LONG).show();
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PaymentMethod> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (!call.isCanceled()) {
//                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void loadEW(){
//        showDialogProgress("Getting List Virtual Account");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        String categoryVa = "ewallet";
        Call<PaymentMethod> call = apiService.getListPayment(auth, categoryVa);
        call.enqueue(new Callback<PaymentMethod>() {
            @Override
            public void onResponse(@NonNull Call<PaymentMethod> call, @NonNull Response<PaymentMethod> response) {
//                hideDialogProgress();
                if (response.isSuccessful()) {
                    PaymentMethod data = response.body();
                    if (response.code() == 200) {
                        eWalletAdapter.setData(data.getData());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
//                    Toast.makeText(this, error.message(), Toast.LENGTH_LONG).show();
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PaymentMethod> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (!call.isCanceled()) {
//                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void confirmPayment(){
        showDialogProgress("Getting Payment Method");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        String latitude = DataManager.getInstance().getLatitude();
        String longitude = DataManager.getInstance().getLongitude();
        String cartProductId = String.valueOf(DataManager.getInstance().getCartProduct());
        name = DataManager.getInstance().getNameContact();
        address = DataManager.getInstance().getAddressContact();
        contact = DataManager.getInstance().getContactContact();
        email = DataManager.getInstance().getEmailContact();
        province = DataManager.getInstance().getProvinceOutlet();
        city = DataManager.getInstance().getCityOutlet();
        outletName = DataManager.getInstance().getOutletOutlet();
        date = DataManager.getInstance().getDateOutletDb();
        time = DataManager.getInstance().getTimeOutletDb();

        list.put("cart_product_id", Utils.INSTANCE.createRequestBody(cartProductId));
        list.put("name", Utils.INSTANCE.createRequestBody(name));
        list.put("address", Utils.INSTANCE.createRequestBody(String.valueOf(address)));
        list.put("contact", Utils.INSTANCE.createRequestBody(String.valueOf(contact)));
        list.put("email", Utils.INSTANCE.createRequestBody(email));
        list.put("province", Utils.INSTANCE.createRequestBody(province));
        list.put("city", Utils.INSTANCE.createRequestBody(String.valueOf(city)));
        list.put("outlet_name", Utils.INSTANCE.createRequestBody(String.valueOf(outletName)));
        list.put("date", Utils.INSTANCE.createRequestBody(date));
        list.put("time", Utils.INSTANCE.createRequestBody(time));

        Call<ContactOutletInformation> call = apiService.createContactOutlet(auth, latitude, longitude, list);
        call.enqueue(new Callback<ContactOutletInformation>() {
            @Override
            public void onResponse(@NonNull Call<ContactOutletInformation> call, @NonNull Response<ContactOutletInformation> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    ContactOutletInformation data = response.body();
                    if (response.code() == 200) {
                        showToast(data.getMessage());
                        price = data.getData().getPaymentAmountSuggestion().replace(".00", "");
                        Log.e("TAG", "onResponse: " + price );
                        transactionId = String.valueOf(data.getData().getTransactionId());
                        siteId = String.valueOf(data.getData().getSiteId());
                        customerId = String.valueOf(data.getData().getCustomerId());
                        customerName = data.getData().getCustomerName();

                        DataManager.getInstance().setTransactionId(transactionId);
                        createInvoice();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ContactOutletInformation> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    t.printStackTrace();
                }
            }
        });
    }

    public void createInvoice(){
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        linkSuccess += "platform="+platform+"&language=id&statusCode=200&transaction_id="+transactionId;
        linkFailure += "platform="+platform+"&language=id&statusCode=400&transaction_id="+transactionId;

        list.put("price", Utils.INSTANCE.createRequestBody(price));
        list.put("transaction_id", Utils.INSTANCE.createRequestBody(transactionId));
        list.put("site_id", Utils.INSTANCE.createRequestBody(siteId));
        list.put("customer_id", Utils.INSTANCE.createRequestBody(customerId));
        list.put("customer_name", Utils.INSTANCE.createRequestBody(customerName));
        list.put("link_success", Utils.INSTANCE.createRequestBody(linkSuccess));
        list.put("link_failure", Utils.INSTANCE.createRequestBody(linkFailure));
        list.put("description", Utils.INSTANCE.createRequestBody(description));
        list.put("platform", Utils.INSTANCE.createRequestBody(platform));

        Call<CreateInvoice> call = apiService.createInvoice(auth, list);
        call.enqueue(new Callback<CreateInvoice>() {
            @Override
            public void onResponse(@NonNull Call<CreateInvoice> call, @NonNull Response<CreateInvoice> response) {
                if (response.isSuccessful()) {
                    CreateInvoice data = response.body();
                    if (response.code() == 200) {
                        showToast(data.getMessage());
                        DataManager.getInstance().setInvoiceUrl(data.getData().getInvoiceUrl());
                        Intent intent = new Intent(PaymentActivity.this, PaymentMethodActivity.class);
                        startActivity(intent);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateInvoice> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }

//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//        DataManager.getInstance().setNameContact("");
//        DataManager.getInstance().setAddressContact("");
//        DataManager.getInstance().setContactContact("");
//        DataManager.getInstance().setemailContact("");
//        DataManager.getInstance().setProvinceOutlet("");
//        DataManager.getInstance().setCityOutlet("");
//        DataManager.getInstance().setOutletOutlet("");
//        DataManager.getInstance().setDateOutlet("");
//        DataManager.getInstance().setTimeOutlet("");
//        finish();
//    }

    @Subscribe
    public void onSaveContact(SaveContactInformation saveContactInformation){
        Log.e("TAG", "onSaveContact: " + DataManager.getInstance().getNameContact() );
        tvName.setText(DataManager.getInstance().getNameContact());
        tvContact.setText(DataManager.getInstance().getContactContact());
//        etOutlet.setText(saveContactInformation.outletName);
    }

    @Subscribe
    public void onSaveOutlet(SaveOutletInformation saveOutletInformation){
        btnComplete.setVisibility(View.GONE);
        cardLocation.setVisibility(View.VISIBLE);
        outletLocation.setText(DataManager.getInstance().getOutletOutlet());
        tvTime.setText(DataManager.getInstance().getTimeOutlet());
        tvDate.setText(DataManager.getInstance().getDateOutlet());
//        etOutlet.setText(saveContactInformation.outletName);
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