package com.codelabs.sitepat_customer.page.service.booking_service.fragment;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.CategoryServiceAdapter;
import com.codelabs.sitepat_customer.adapter.TypeServiceAdapater;
import com.codelabs.sitepat_customer.adapter.TypeServiceChosess;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.utils.RecentUtils;
import com.codelabs.sitepat_customer.viewmodel.CategoryService;
import com.codelabs.sitepat_customer.viewmodel.ServiceSelected;
import com.codelabs.sitepat_customer.viewmodel.TypeService;
import com.codelabs.sitepat_customer.viewmodel.TypeServiceSelected;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetAddService extends BottomSheetDialogFragment {

    //    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.bottom_sheet_add_service)
//    CoordinatorLayout bottomsheetAdd;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_tab_title)
    RecyclerView rvTabTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_type_service)
    RecyclerView rvTypeService;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_close)
    AppCompatImageView ivClose;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_select)
    AppCompatTextView btnSelect;

    CategoryServiceAdapter categoryServiceAdapter;
    TypeServiceAdapater typeServiceAdapater;
    TypeServiceChosess typeServiceChosessAdapter;
    private List<TypeService.ItemsEntity> selectedTypeService = new ArrayList<>();
    private List<TypeServiceSelected> typeServiceSelect = new ArrayList<>();
//    private List<String> typeServiceSelect = new ArrayList<>();

    private int limit = 100;
    private String category = "";
    private String nameService = "";
    private String descService = "";
    private int price = 0;
    private int medicalId = 0;
    private String sku = "";
    private String taxRate = "";

    private OnListSelected onListSelected;

    //    private BottomSheetBehavior bottomSheetBehavior;
    BottomSheetDialog dialog;
    BottomSheetBehavior<View> bottomSheetBehavior;
    View rootView;

    public BottomSheetAddService() {

    }

    public interface OnListSelected {
        void onListSelected(List<TypeService.ItemsEntity> item);
    }

    public void OnListSelectedItem(BottomSheetAddService.OnListSelected onListSelected) {
        this.onListSelected = onListSelected;
    }

    public void setData(List<TypeService.ItemsEntity> data) {
        this.selectedTypeService = data;
        Log.e("cek this", String.valueOf(selectedTypeService.size()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
//                setupFullHeight(bottomSheetDialog);
//            }
//        });

//        View bottomSheetView = LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_bottom_sheet_add_service, null);
//        dialog.setContentView(bottomSheetView);
//
//        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//
//        CoordinatorLayout layout = dialog.findViewById(R.id.bottom_sheet_add_service);
//        assert layout != null;
//        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        return dialog;
    }

//    private void setupFullHeight(BottomSheetDialog bottomSheetDialog){
//        FrameLayout layout = (FrameLayout) bottomSheetDialog.findViewById(R.id.bottom_sheet_add_service);
//        BottomSheetBehavior behavior = BottomSheetBehavior.from(layout);
//        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
//
//        int windowHeight = getWindowHeight();
//        if (layoutParams != null){
//            layoutParams.height = windowHeight;
//        }
//        layout.setLayoutParams(layoutParams);
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//    }
//
//    private int getWindowHeight(){
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        return displayMetrics.heightPixels;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_bottom_sheet_add_service, container, false);
        ButterKnife.bind(this, rootView);

        category = DataManager.getInstance().getCartId();
        Log.e("TAG", "onCreateView: " + category);
        Log.e("TAG CEK", "onCreateView: " + selectedTypeService.size() );

        initView();
        initSetup();
        fetchData();

//        category = "2";
//        if (DataManager.getInstance().getDefaultCartId() != null){
//            if (DataManager.getInstance().getDefaultCartId() != ""){
//                category = DataManager.getInstance().getDefaultCartId();
//            }
//        }

//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int dm = Resources.getSystem().getDisplayMetrics().heightPixels;
//        int rect = dm.
        view.setMinimumHeight(dm);

        View bottomSheetView = LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_bottom_sheet_add_service, null);
        dialog.setContentView(bottomSheetView);

        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setDraggable(false);

//        CoordinatorLayout layout = dialog.findViewById(R.id.bottom_sheet_add_service);
//        assert layout != null;
//        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

    }

//    public void onClickCart(){
//        View bottomSheetView = LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_bottom_sheet_add_service, null);
//        dialog.setContentView(bottomSheetView);
//
//        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        bottomSheetBehavior.setDraggable(false);
//
//        CoordinatorLayout layout = dialog.findViewById(R.id.bottom_sheet_add_service);
//        assert layout != null;
//        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
//    }

    private void initView() {
        categoryServiceAdapter = new CategoryServiceAdapter(getApplicationContext());
        rvTabTitle.setAdapter(categoryServiceAdapter);
        rvTabTitle.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        if (0 == rvTabTitle.getItemDecorationCount()) {
            rvTabTitle.addItemDecoration(new RecentUtils.PaddingItemDecoration(55));
        }

        typeServiceAdapater = new TypeServiceAdapater(getApplicationContext());
        rvTypeService.setAdapter(typeServiceAdapater);
        rvTypeService.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //implement value


//        typeServiceChosessAdapter = new TypeServiceChosess(getApplicationContext());
////        rvTypeService.setAdapter(typeServiceAdapater);
////        rvTypeService.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        typeServiceChosessAdapter.notifyDataSetChanged();

//        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheetAdd);
//        bottomSheetBehavior.setPeekHeight(900);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        DisplayMetrics metrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        getDialog().getWindow().setGravity(Gravity.BOTTOM);
//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (metrics.heightPixels * 0.30));// here i have fragment height 30% of window's height you can set it as per your requirement
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimationUpDown;
//    }

    private void initSetup() {
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = DataManager.getInstance().getCartId();
//                selectedTypeService.clear();
                dismiss();
//                dialog.setCancelable(true);
//                dialog == MotionEvent.ACTION_UP;
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectedTypeService.clear();
//                typeServiceChosessAdapter.notifyDataSetChanged();

                if (selectedTypeService.size() != typeServiceAdapater.typeServiceList.size()){
//                    selectedTypeService.clear();
                    for (int i = 0; i < typeServiceAdapater.typeServiceList.size(); i++) {
//                        selectedTypeService.clear();
                        if (typeServiceAdapater.typeServiceList.get(i).isSelected()) {
//                            if (selectedTypeService.get(i).getMedicalId() == typeServiceAdapater.typeServiceList.get(i).getMedicalId()){
//                                selectedTypeService.clear();
//                            }
//                            selectedTypeService.clear();
//                            selectedTypeService.remove(typeServiceAdapater.typeServiceList.get(i));
                            selectedTypeService.add(typeServiceAdapater.typeServiceList.get(i));
//                            selectedTypeService.add(selectedTypeService.get(i));
//                            selectedTypeService.clear();
                        }
                        else {
//                            selectedTypeService.clear();
                            selectedTypeService.remove(typeServiceAdapater.typeServiceList.get(i));
//                            selectedTypeService.add(typeServiceAdapater.typeServiceList.get(i));
                        }
                    }

//                    selectedTypeService.clear();
                }

//                for (int i = 0; i < typeServiceAdapater.typeServiceList.size(); i++) {
//                    if (typeServiceAdapater.typeServiceList.get(i).isSelected()) {
//                        selectedTypeService.add(typeServiceAdapater.typeServiceList.get(i));
////                        typeServiceAdapater.typeServiceList.get(i).setSelected(true);
//                    }
//                }
//                selectedTypeService.clear();
                Log.e("22 nov 1", String.valueOf(selectedTypeService.size()));
                onListSelected.onListSelected(selectedTypeService);
                category = DataManager.getInstance().getCartId();
//                selectedTypeService.clear();
                dismiss();
//                selectedTypeService.clear();
//                Log.e("13 feb 2023", String.valueOf(selectedTypeService.size()));

            }
        });

        typeServiceAdapater.OnClickSelectedItem(new TypeServiceAdapater.OnItemCLickTypeService() {
            @Override
            public void onItemClick(TypeService.ItemsEntity item) {

                //buat kondisi disini biar ngga double

                if (item.isSelected()) {
//                    if (selectedTypeService.size() != 0){
//                        selectedTypeService.clear();
//                    }
                    for (int i = 0; i < typeServiceAdapater.typeServiceList.size(); i++) {
                        if (typeServiceAdapater.typeServiceList.get(i).isSelected()) {
                    selectedTypeService.remove(typeServiceAdapater.typeServiceList.get(i));
                    selectedTypeService.add(typeServiceAdapater.typeServiceList.get(i));
//                    onListSelected.onListSelected(selectedTypeService);
                    Log.e("cek isi after add", String.valueOf(selectedTypeService.size()));
                        }

//                    if (!typeServiceAdapater.typeServiceList.get(i).isSelected())  {
//                        selectedTypeService.remove(typeServiceAdapater.typeServiceList.get(i));
//                        Log.e("cek isi after remove", String.valueOf(selectedTypeService.size()));
//                    }

                    }
                } else {
                    for (int i = 0; i < typeServiceAdapater.typeServiceList.size(); i++) {
                        if (!typeServiceAdapater.typeServiceList.get(i).isSelected()) {
                    selectedTypeService.remove(typeServiceAdapater.typeServiceList.get(i));
//                    selectedTypeService.clear();
//                    selectedTypeService.add(item);
//                    onListSelected.onListSelected(selectedTypeService);
                    Log.e("cek isi after remove", String.valueOf(selectedTypeService.size()));
                        }
                    }
////                    selectedTypeService.clear();
                }
            }
        });

        categoryServiceAdapter.onCart = new CategoryServiceAdapter.OnCart() {
            @Override
            public void onCart(String cart) {
                category = cart;
                loadTypeService();
            }
        };

//        rvTypeService.setOnTouchListener(new View.OnTouchListener() {
//            @SuppressLint("ClickableViewAccessibility")
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                v.onTouchEvent(event);
//                return true;
//            }
//        });
    }

    private void fetchData() {
        loadCategoryService();
        loadTypeService();
//        onClickCart();
    }

    public void loadCategoryService() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<CategoryService> call = apiService.getCategory(auth);
        call.enqueue(new Callback<CategoryService>() {
            @Override
            public void onResponse(@NonNull Call<CategoryService> call, @NonNull Response<CategoryService> response) {
                if (response.isSuccessful()) {
                    CategoryService data = response.body();
                    if (response.code() == 200) {
//                        categoryServiceAdapter.variant = 0;
                        categoryServiceAdapter.setData(data.getData().getItems());
//                        DataManager.getInstance().setCartId(String.valueOf(data.getData().getItems().get(0).getCategoryId()));
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_LONG).show();
//                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryService> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }

    public void loadTypeService() {
//        category = DataManager.getInstance().getCartId();
        Log.e("TAG", "loadTypeService: " + category);
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<TypeService> call = apiService.getTypeService(auth, limit, category);
        call.enqueue(new Callback<TypeService>() {
            @Override
            public void onResponse(@NonNull Call<TypeService> call, @NonNull Response<TypeService> response) {
                if (response.isSuccessful()) {
                    TypeService data = response.body();
                    if (response.code() == 200) {

                        typeServiceAdapater.setData(data.getData().getItems());

                        Log.e("cek size select", String.valueOf(selectedTypeService.size()));
                        for (int i = 0; i < selectedTypeService.size(); i++) {
                            for (int i1 = 0; i1 < data.getData().getItems().size(); i1++) {
                                if (selectedTypeService.get(i).getMedicalId() == typeServiceAdapater.typeServiceList.get(i1).getMedicalId()) {
                                    typeServiceAdapater.typeServiceList.get(i1).setSelected(true);
                                    Log.e("TAG", "onResponse: " + String.valueOf(selectedTypeService.get(i).getMedicalId()));
//                                    selectedTypeService.clear();
//                                    typeServiceChosessAdapter.notifyDataSetChanged();
                                }
                            }

//                            Log.e("22 november", "onResponse: select " + typeServiceAdapater.typeServiceList.get(i).isSelected() );
//                            if (typeServiceAdapater.typeServiceList.get(i).isSelected()){
//                                typeServiceAdapater.typeServiceList.get(i).setSelected(true);
//                            }
                        }

//                        typeServiceAdapater.notifyDataSetChanged();
//                        selectedTypeService.clear();
//                        category = "";
//                        DataManager.getInstance().setCartId("");

                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(requireContext(), error.message(), Toast.LENGTH_LONG).show();
//                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TypeService> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                }
            }
        });
    }

//    @Subscribe
//    public void onServiceSelect(ServiceSelected serviceSelected) {
//        category = serviceSelected.categoryId;
////        onClickCart();
//        loadTypeService();
//    }

//    @Subscribe
//    public void onBtnSelect(TypeServiceSelected typeServiceSelected) {
//        nameService = typeServiceSelected.serviceName;
//        descService = typeServiceSelected.descService;
//        price = typeServiceSelected.price;
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }

}