package com.codelabs.dokter_mobil_customer.page.article;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.adapter.ArticleAdapter;
import com.codelabs.dokter_mobil_customer.adapter.NewsAdapter;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.viewmodel.Articles;
import com.codelabs.dokter_mobil_customer.viewmodel.News;
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_search_article)
    AppCompatEditText edtSeacrhArticle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_no_data)
    AppCompatTextView tvNoData;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_no_data)
    RelativeLayout containerNoData;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_news)
    RecyclerView rvNews;

    ArticleAdapter mAdapter;
    NewsAdapter newsAdapter;
    private String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
        initView();
        initSetup();
        fetchData();
    }

    private void initView() {
        containerNoData.setVisibility(View.GONE);
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);

        rvArticle.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ArticleAdapter(this);
        mAdapter.setData(new ArrayList<>());
        rvArticle.setAdapter(mAdapter);

        rvNews.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(this);
        newsAdapter.setData(new ArrayList<>());
        rvNews.setAdapter(newsAdapter);
    }

    private void initSetup() {
        functionSearch();
    }

    private void fetchData() {
        loadDataArticle();
        loadDataNews();
    }

    private void functionSearch() {
        edtSeacrhArticle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = s.toString();
                searchDataArticle();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void loadDataArticle() {
        showDialogProgress("Getting data article");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Articles> call = apiService.getArticles(auth, keyword, 0,0);
        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(@NonNull Call<Articles> call, @NonNull Response<Articles> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    Articles data = response.body();
                    if (response.code() == 200) {
                        assert data != null;
                        mAdapter.setData(data.getData().getItemsArticles());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Articles> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void searchDataArticle() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<Articles> call = apiService.getArticles(auth, keyword,0,0);
        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(@NonNull Call<Articles> call, @NonNull Response<Articles> response) {
                if (response.isSuccessful()) {
                    Articles data = response.body();
                    if (response.code() == 200) {
                        mAdapter.setData(data.getData().getItemsArticles());
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Articles> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    containerNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void loadDataNews() {
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<News> call = apiService.getNews(auth);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
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
            if (edtSeacrhArticle.getVisibility() == View.VISIBLE) {
                edtSeacrhArticle.setVisibility(View.GONE);
            } else {
                edtSeacrhArticle.setVisibility(View.VISIBLE);
            }
        }
    }
}