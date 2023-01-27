package com.codelabs.sitepat_customer.page.service.body_paint;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.Utils;
import com.codelabs.sitepat_customer.page.service.body_paint.fragment.BikeServiceFragmentBody;
import com.codelabs.sitepat_customer.page.service.body_paint.fragment.LocationFragmentBody;
import com.codelabs.sitepat_customer.page.service.body_paint.fragment.OutletTimeFragment;
import com.codelabs.sitepat_customer.page.service.body_paint.fragment.YourInformationFragmentBody;
import com.codelabs.sitepat_customer.page.service.booking_service.BookingServiceActivity;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.LocationFragment;
import com.codelabs.sitepat_customer.page.service.booking_service.fragment.YourInformationFragment;
import com.codelabs.sitepat_customer.page.shop.ShopActivity;
import com.codelabs.sitepat_customer.viewmodel.Cart;
import com.codelabs.sitepat_customer.viewmodel.MyMotocycleSelected;
import com.codelabs.sitepat_customer.viewmodel.NextBP1;
import com.codelabs.sitepat_customer.viewmodel.NextBP2;
import com.codelabs.sitepat_customer.viewmodel.NextBP3;
import com.codelabs.sitepat_customer.viewmodel.NextBS1;
import com.codelabs.sitepat_customer.viewmodel.NextBS2;
import com.codelabs.sitepat_customer.viewmodel.NextBS3;
import com.codelabs.sitepat_customer.viewmodel.OutletSelected;
import com.codelabs.sitepat_customer.viewmodel.Previous2;
import com.codelabs.sitepat_customer.viewmodel.Previous3;
import com.codelabs.sitepat_customer.viewmodel.Previous4;
import com.codelabs.sitepat_customer.viewmodel.PreviousBP2;
import com.codelabs.sitepat_customer.viewmodel.PreviousBP3;
import com.codelabs.sitepat_customer.viewmodel.PreviousBP4;
import com.codelabs.sitepat_customer.viewmodel.TypeService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BodyPaintActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_title)
    TextView title;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.header_menu)
    TextView headerMenu;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator1)
    View indicator1;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator2)
    View indicator2;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator3)
    View indicator3;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.indicator4)
    View indicator4;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.btn_next)
//    TextView btnNext;
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.btn_previous)
//    TextView btnPrevious;
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.btn_book_now)
//    TextView btnBookNow;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private double valueLatitude, valueLongitude;

    Integer page = 0;
    private String siteId = "";
    private String petId = "";
    private int serviceType = 3;
    private int cartServiceId = 0;
    private String complainUser = "";
    private String orderDate = "";
    private String time = "";
    private String email = "";
    private String promoCode = "";

    private List<TypeService.ItemsEntity> selectedTypeService = new ArrayList<>();
    private HashMap<String, RequestBody> list = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_paint);
        ButterKnife.bind(this);
        initSetup();
        initView();
        changePage();

    }

    private void initSetup() {
        ivBack.setOnClickListener(this);
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void onClick(View view){
        if (ivBack == view) {
            page -= 1;
            changePage();

            if (page == 2) {
                indicator4.setBackgroundResource(R.color.grey_200);
                return;
            }
            if (page == 1) {
                indicator3.setBackgroundResource(R.color.grey_200);
                petId = "";
                return;
            }
            if (page == 0) {
                indicator2.setBackgroundResource(R.color.grey_200);
                siteId = "";
                return;
            }

            DataManager.getInstance().setPositionLocation(-1);
            DataManager.getInstance().setClose("");
            DataManager.getInstance().setPositionMotocycle(-1);
            DataManager.getInstance().setDate("");
            DataManager.getInstance().setTime("");
            DataManager.getInstance().setOutlet("");
            finish();
        }

    }

//    interface OnPageChangeListener{
//        public void onPage(Integer page);
//    }

    public void changePage(){
//        FragmentManager fragment = getSupportFragmentManager();
        if (page == 0) {
            title.setText(R.string.body);
            headerMenu.setText(R.string.location);

            indicator1.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new LocationFragmentBody()).commit();


        }
        if (page == 1){
            title.setText(R.string.body);
            headerMenu.setText(R.string.bikeservice);

            indicator1.setBackgroundResource(R.color.red_text);
            BikeServiceFragmentBody bikeServiceFragmentBody = new BikeServiceFragmentBody();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, bikeServiceFragmentBody).commit();
            bikeServiceFragmentBody.onNext = new BikeServiceFragmentBody.OnNext() {
                @Override
                public void onNext(String complain, List<TypeService.ItemsEntity> listService) {
                    complainUser = complain ;
                    selectedTypeService = listService;
                }
            };

            Log.e("TAG", "changePage: " + complainUser);
            bikeServiceFragmentBody.setData(complainUser, selectedTypeService);

        }
        if (page == 2){
            title.setText(R.string.body);
            headerMenu.setText(R.string.outlet_time);

            indicator2.setBackgroundResource(R.color.red_text);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new OutletTimeFragment()).commit();


        }
        if (page == 3){
            title.setText(R.string.body);
            headerMenu.setText(R.string.your_information);

            indicator3.setBackgroundResource(R.color.red_text);
            YourInformationFragmentBody yourInformationFragmentBody = new YourInformationFragmentBody();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, yourInformationFragmentBody).commit();
            yourInformationFragmentBody.bookNow = new YourInformationFragmentBody.BookNow() {
                @Override
                public void bookNow() {
                    eventBookNow();
                }
            };
        }
    }

    private void eventBookNow(){
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        email = DataManager.getInstance().getEmail();
        orderDate = DataManager.getInstance().getDate();
        time = DataManager.getInstance().getTime();

        for (int i = 0; i < selectedTypeService.size(); i++) {
            list.put("service_id["+i+"]", Utils.INSTANCE.createRequestBody(String.valueOf(selectedTypeService.get(i).getMedicalId())));
            list.put("service_name["+i+"]", Utils.INSTANCE.createRequestBody(selectedTypeService.get(i).getMedicalName()));
            list.put("service_price["+i+"]", Utils.INSTANCE.createRequestBody(String.valueOf(selectedTypeService.get(i).getRetailPrice())));
            list.put("sku["+i+"]", Utils.INSTANCE.createRequestBody(selectedTypeService.get(i).getSku()));
            list.put("tax_rate["+i+"]", Utils.INSTANCE.createRequestBody(selectedTypeService.get(i).getTaxRate()));
        }

//        HashMap param = new HashMap<String, RequestBody>();
        list.put("pet_id", Utils.INSTANCE.createRequestBody(petId));
        list.put("site_id", Utils.INSTANCE.createRequestBody(siteId));
        list.put("service_type", Utils.INSTANCE.createRequestBody(String.valueOf(serviceType)));
        list.put("cart_service_id", Utils.INSTANCE.createRequestBody(String.valueOf(cartServiceId)));
        list.put("complaint", Utils.INSTANCE.createRequestBody(complainUser));

        list.put("order_date", Utils.INSTANCE.createRequestBody(orderDate));
        list.put("time", Utils.INSTANCE.createRequestBody(time));
        list.put("email", Utils.INSTANCE.createRequestBody(email));
        list.put("promo_code", Utils.INSTANCE.createRequestBody(promoCode));

        Call<Cart> call = apiService.createCart(auth, list);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(@NonNull Call<Cart> call, @NonNull Response<Cart> response) {
                if (response.isSuccessful()) {
                    Cart data = response.body();
                    if (response.code() == 200) {
                        Toast.makeText(BodyPaintActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        DataManager.getInstance().setPositionLocation(-1);
                        DataManager.getInstance().setClose("");
                        DataManager.getInstance().setPositionMotocycle(-1);
                        DataManager.getInstance().setDate("");
                        DataManager.getInstance().setTime("");
                        DataManager.getInstance().setOutlet("");
                        finish();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(BodyPaintActivity.this, error.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cart> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }

    @Subscribe
    public void onOutletSelect(OutletSelected outletSelected) {
        siteId = outletSelected.outletSelect;
        Log.e("TAG", "onOutletSelect: " + siteId );
    }

    @Subscribe
    public void onNext1(NextBP1 nextBP1) {
        page += 1;
        changePage();
    }

    @Subscribe
    public void onNext2(NextBP2 nextBP2) {
        page += 1;
        changePage();
    }

    @Subscribe
    public void onPrevious2(PreviousBP2 previous2) {
        page -= 1;
        changePage();

        if (page == 0) {
            indicator2.setBackgroundResource(R.color.grey_200);
        }
    }

    @Subscribe
    public void onNext3(NextBP3 nextBS3) {
        page += 1;
        changePage();
    }

    @Subscribe
    public void onPrevious3(PreviousBP3 previous3) {
        page -= 1;
        changePage();

        if (page == 1) {
            indicator3.setBackgroundResource(R.color.grey_200);
        }
    }

    @Subscribe
    public void onPrevious4(PreviousBP4 previous4) {
        page -= 1;
        changePage();

        if (page == 2) {
            indicator4.setBackgroundResource(R.color.grey_200);
        }
    }

    @Subscribe
    public void onMotocycleSelect(MyMotocycleSelected myMotocycleSelected) {
        petId = myMotocycleSelected.motocycleSelect;
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