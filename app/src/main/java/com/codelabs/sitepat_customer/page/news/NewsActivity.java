package com.codelabs.sitepat_customer.page.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.adapter.NewsAdapter;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.viewmodel.News;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_search_news)
    AppCompatEditText etSearchNews;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_no_data)
    AppCompatTextView tvNoData;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_no_data)
    RelativeLayout containerNoData;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_news)
    RecyclerView rvNews;

    NewsAdapter newsAdapter;
    private String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();
    }

    private void initView() {
        containerNoData.setVisibility(View.GONE);
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);

        rvNews.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(this);
        newsAdapter.setData(new ArrayList<>());
        rvNews.setAdapter(newsAdapter);
    }

    private void initSetup() {
        functionSearch();
    }

    private void fetchData() {
        loadDataNews();
    }

    private void functionSearch() {
        etSearchNews.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = s.toString();
                searchDataNews();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void loadDataNews() {
        showDialogProgress("Getting data news");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<News> call = apiService.getNews(auth, keyword);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    News data = response.body();
                    if (response.code() == 200) {
                        newsAdapter.setData(data.getData().getItemsNews());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<News> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    t.printStackTrace();
                    hideDialogProgress();
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void searchDataNews() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<News> call = apiService.getNews(auth, keyword);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call,@NonNull  Response<News> response) {
                if (response.isSuccessful()) {
                    News data = response.body();
                    if (response.code() == 200) {
                        newsAdapter.setData(data.getData().getItemsNews());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(getString(R.string.toast_onfailure));
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }

        if (ivSearch == view) {
            if (etSearchNews.getVisibility() == View.VISIBLE) {
                etSearchNews.setVisibility(View.GONE);
            } else {
                etSearchNews.setVisibility(View.VISIBLE);
            }
        }
    }
}