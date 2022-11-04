package com.codelabs.sitepat_customer.page.service.booking_service.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.MyMotocycleAdapter;
import com.codelabs.sitepat_customer.adapter.TypeServiceAdapater;
import com.codelabs.sitepat_customer.adapter.TypeServiceChosess;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.utils.RecentUtils;
import com.codelabs.sitepat_customer.viewmodel.MyMotocycle;
import com.codelabs.sitepat_customer.viewmodel.MyMotocycleSelected;
import com.codelabs.sitepat_customer.viewmodel.NextBS2;
import com.codelabs.sitepat_customer.viewmodel.Previous2;
import com.codelabs.sitepat_customer.viewmodel.TypeService;
import com.codelabs.sitepat_customer.viewmodel.TypeServiceSelected;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BikeServiceFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.add_service)
    TextView addService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_periodic)
    TextView edit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_motocycle_service)
    FrameLayout containerMotocycleService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_motocycle)
    RecyclerView rvMotocycle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_service_chosess)
    RecyclerView rvServiceChosess;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.empty_service)
    AppCompatTextView emptyService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next)
    AppCompatTextView btnNext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_previous)
    AppCompatTextView btnPrevious;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_service_name)
    AppCompatTextView tvServiceName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_desc_service)
    AppCompatTextView tvDescService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_price)
    AppCompatTextView tvPrice;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_service_chooses)
    LinearLayout layServiceChooses;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lay_estimate)
    LinearLayout layEstimate;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.price_est)
    TextView priceEst;

    public String petId = "";
    public int position = -1;
    private Normalizer.Form list;

    MyMotocycleAdapter myMotocycleAdapter;
    TypeServiceAdapater typeServiceAdapater;
    TypeServiceChosess typeServiceChosessAdapter;
//    private BottomSheetBehavior bottomSheetBehavior;
    BottomSheetBehavior<View> bottomSheetBehavior;

    private List<TypeServiceSelected> typeServiceSelect = new ArrayList<>();
    private List<TypeService.ItemsEntity> selectedTypeService = new ArrayList<>();

    private BottomSheetAddService bottomSheetAddService = new BottomSheetAddService();

    private OnListChecked onListChecked;

    public interface OnListChecked{
        void onListChecked(List<TypeService.ItemsEntity> itemChecked);
    }

    public void onListCheckedItem(BikeServiceFragment.OnListChecked onListChecked){
        this.onListChecked = onListChecked;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bike_service, container, false);
        ButterKnife.bind(this, view);

        initView();
//        initSetup();
        fetchData();

        if (typeServiceSelect.size() == 0){
            emptyService.setVisibility(View.VISIBLE);
            rvServiceChosess.setVisibility(View.GONE);
        }
//        else{
//            emptyService.setVisibility(View.GONE);
//            rvServiceChosess.setVisibility(View.VISIBLE);
//        }

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
//
//        if (typeServiceSelect.size() == 0){
//            emptyService.setVisibility(View.VISIBLE);
//            rvServiceChosess.setVisibility(View.GONE);
//        }
//        else{
//            int price = 0;
//            for (int i = 0; i < typeServiceSelect.size(); i++){
//                price = price + typeServiceSelect.get(i).getPrice();
//            }
//
//            typeServiceChosessAdapter.setData(typeServiceSelect);
//            emptyService.setVisibility(View.GONE);
//            rvServiceChosess.setVisibility(View.VISIBLE);
//            layEstimate.setVisibility(View.VISIBLE);
//
//            String totalPrice = rupiah.format(new BigDecimal(price));
//            priceEst.setText(totalPrice.replace(",00","").replace("Rp",""));
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (petId != null) {
                    if (!petId.equals("")) {
                        EventBus.getDefault().post(new NextBS2());
                        DataManager.getInstance().setPositionMotocycle(position);
                    } else {
                        Toast.makeText(v.getContext(), "No motocycle selected", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Previous2());
            }
        });

//        Dialog dialog = getDialog();
//        View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
//        if (dialog != null) {
//
//            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
//        }
////        View view = getView();
//        view.post(() -> {
//            View parent = (View) view.getParent();
//            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
//            CoordinatorLayout.Behavior behavior = params.getBehavior();
//            BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
//            bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
//            ((View)bottomSheet.getParent()).setBackgroundColor(Color.TRANSPARENT)
//
//        });


//        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_EXPANDED);
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });

//        bottomSheetBehavior = BottomSheetBehavior.from(getChildFragmentManager());
//        bottomSheetBehavior.setPeekHeight(300);

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BottomSheetDialog bottomSheet = new BottomSheetDialog(requireActivity());

                bottomSheetAddService.show(getChildFragmentManager(), bottomSheetAddService.getTag());

//                View bottomSheetView = LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_bottom_sheet_add_service, null);
//                bottomSheet.setContentView(bottomSheetView);

//                bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//
//                CoordinatorLayout layout = bottomSheet.findViewById(R.id.bottom_sheet_add_service);
//                assert layout != null;
//                layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
//
//                bottomSheet.show();

            }
        });

        bottomSheetAddService.OnListSelectedItem(new BottomSheetAddService.OnListSelected() {
            @Override
            public void onListSelected(List<TypeServiceSelected> item) {
                NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));

                int price = 0;
                for (int i = 0; i < item.size(); i++){
                    price = price + item.get(i).getPrice();
                }

                typeServiceChosessAdapter.setData(item);
                emptyService.setVisibility(View.GONE);
                rvServiceChosess.setVisibility(View.VISIBLE);
                layEstimate.setVisibility(View.VISIBLE);

                String totalPrice = rupiah.format(new BigDecimal(price));
                priceEst.setText(totalPrice.replace(",00","").replace("Rp",""));

//                selectedTypeService.add(item);
//                onListChecked.onListChecked(selectedTypeService);
            }
        });

//        bottomSheetAddService.OnListSelectedItem(new BottomSheetAddService.OnListSelected() {
//            @Override
//            public void onListSelected(List<TypeService.ItemsEntity> item) {
//                typeServiceChosessAdapter.setData(item);
//            }
//        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetAddService bottomSheetAddService = new BottomSheetAddService();
                bottomSheetAddService.show(getChildFragmentManager(), bottomSheetAddService.getTag());

            }
        });
    }


    private void initView() {
        rvMotocycle.setLayoutManager(new LinearLayoutManager(requireContext()));
        myMotocycleAdapter = new MyMotocycleAdapter(requireContext());
        myMotocycleAdapter.setData(new ArrayList<>());
        rvMotocycle.setAdapter(myMotocycleAdapter);

        rvMotocycle.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        if (0 == rvMotocycle.getItemDecorationCount()){
            rvMotocycle.addItemDecoration(new RecentUtils.PaddingItemDecoration(55));
        }

//        bottomSheetBehavior = BottomSheetBehavior.from(containerMotocycleService);
//        bottomSheetBehavior.setPeekHeight(900);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        rvServiceChosess.setLayoutManager(new LinearLayoutManager(requireContext()));
        typeServiceChosessAdapter = new TypeServiceChosess(requireContext());
        typeServiceChosessAdapter.setData(new ArrayList<>());
        rvServiceChosess.setAdapter(typeServiceChosessAdapter);

    }

    private void fetchData() {
        loadOutlet();
//        loadServiceChosess();
    }

    public void loadOutlet() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<MyMotocycle> call = apiService.getMotocycle(auth);
        call.enqueue(new Callback<MyMotocycle>() {
            @Override
            public void onResponse(@NonNull Call<MyMotocycle> call, @NonNull Response<MyMotocycle> response) {
                if (response.isSuccessful()) {
                    MyMotocycle data = response.body();
                    if (response.code() == 200) {
                        myMotocycleAdapter.setData(data.getData().getItems());
//                        Log.e("cek lat", String.valueOf(valueLatitude));

//                        ArrayList<TypeServiceSelected> service = new ArrayList<>();
////        service.addAll(TypeServiceSelected.);
//                        Log.e("cek model" , String.valueOf(typeServiceSelect.size()));
//                        if (typeServiceSelect.size() == 0){
//                            emptyService.setVisibility(View.VISIBLE);
//                            rvServiceChosess.setVisibility(View.GONE);
//                        }else{
//                            emptyService.setVisibility(View.GONE);
//                            rvServiceChosess.setVisibility(View.VISIBLE);
//                            typeServiceChosessAdapter.setData(service);
//                        }
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_LONG).show();
//                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyMotocycle> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    Toast.makeText(requireContext(), getString(R.string.toast_onfailure), Toast.LENGTH_LONG).show();
//                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void loadServiceChosess() {

//        Log.e("cek model" , String.valueOf(typeServiceSelect.size()));
        ArrayList<TypeServiceSelected> service = new ArrayList<>();
//        service.addAll(TypeServiceSelected.);
//        if (typeServiceSelect.size() == 0){
//            emptyService.setVisibility(View.VISIBLE);
//            rvServiceChosess.setVisibility(View.GONE);
//        }else{
//            emptyService.setVisibility(View.GONE);
//            rvServiceChosess.setVisibility(View.VISIBLE);
//            typeServiceChosessAdapter.setData(typeServiceSelect);

            BikeServiceFragment fragment = new BikeServiceFragment();
            Bundle bundle = fragment.getArguments();
            if (bundle != null){
                Type listType = new TypeToken<ArrayList<TypeServiceSelected>>() {}.getType();
                typeServiceSelect = new Gson().fromJson((String) bundle.getSerializable("list service"), listType);

                typeServiceChosessAdapter.setData(typeServiceSelect);
                Log.e("cek model" , String.valueOf(typeServiceSelect.size()));
            }
//        }

    }

    @Subscribe
    public void onMotocycleSelect(MyMotocycleSelected myMotocycleSelected) {
        petId = myMotocycleSelected.motocycleSelect;
        position = myMotocycleSelected.motocyclePosition;
    }

    @Subscribe
    public void onServiceChooses(TypeServiceSelected typeServiceSelected) {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String price = rupiah.format(new BigDecimal(typeServiceSelected.price));

//        if (typeServiceSelected.serviceName.equals("")){
//            emptyService.setVisibility(View.VISIBLE);
//            layServiceChooses.setVisibility(View.GONE);
//            edit.setVisibility(View.GONE);
//            addService.setVisibility(View.VISIBLE);
//
//            layEstimate.setVisibility(View.GONE);
//        }else{
//            tvServiceName.setText(typeServiceSelected.serviceName);
//            tvDescService.setText(typeServiceSelected.descService);
//            tvPrice.setText(price.replace(",00","").replace("Rp",""));
//            emptyService.setVisibility(View.GONE);
//            layServiceChooses.setVisibility(View.VISIBLE);
//            edit.setVisibility(View.VISIBLE);
//            addService.setVisibility(View.GONE);
//
//            layEstimate.setVisibility(View.VISIBLE);
//            priceEst.setText(price.replace(",00","").replace("Rp",""));
//        }

//        loadServiceChosess();
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