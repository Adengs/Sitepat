package com.codelabs.sitepat_customer.page.news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.ApiError;
import com.codelabs.sitepat_customer.connection.ApiUtils;
import com.codelabs.sitepat_customer.connection.AppConstant;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.connection.ErrorUtils;
import com.codelabs.sitepat_customer.connection.RetrofitInterface;
import com.codelabs.sitepat_customer.helper.BaseActivity;
import com.codelabs.sitepat_customer.utils.RecentUtils;
import com.codelabs.sitepat_customer.viewmodel.NewsDetail;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_title_article)
    AppCompatTextView tvTitleArticle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_type_article)
    LinearLayout containerTypeArticle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_type_video)
    LinearLayout containerTypeVideo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_date_article)
    AppCompatTextView tvDateArticle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_promo)
    AppCompatImageView ivPromo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_desc_article)
    AppCompatTextView tvDescArticle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_author)
    AppCompatTextView tvAuthor;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_no_data)
    AppCompatTextView tvNoData;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_no_data)
    RelativeLayout containerNoData;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_article)
    RelativeLayout containerArticle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_video_youtube)
    RelativeLayout containerVideoYoutube;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_youtube)
    AppCompatImageView ivYoutube;

    int idNews = -1;
    private NewsDetail.DataNewsDetail responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        getPrefData();
        initView();
        fetchData();
    }

    private void getPrefData() {
        Intent intent = getIntent();
        idNews = intent.getIntExtra("news_id",1);
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        ivBack.setOnClickListener(this);
        ivYoutube.setOnClickListener(this);
        containerNoData.setVisibility(View.GONE);
    }

    private void fetchData() {
        loadDataNews();
    }

    public void loadDataNews() {
        showDialogProgress("Getting data news detail");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<NewsDetail> call = apiService.getNewsDetail(auth, idNews);
        call.enqueue(new Callback<NewsDetail>() {
            @Override
            public void onResponse(@NonNull Call<NewsDetail> call, @NonNull Response<NewsDetail> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    NewsDetail data = response.body();
                    if (response.code() == 200) {
                        responseData = data.getData();
                        dataNewsDetail();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    containerArticle.setVisibility(View.GONE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsDetail> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();
                    containerNoData.setVisibility(View.VISIBLE);
                    containerArticle.setVisibility(View.GONE);
                    tvNoData.setText(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    public void dataNewsDetail() {

        Glide.with(this)
                .load(responseData.getImage())
                .thumbnail(0.25f)
                .diskCacheStrategy( DiskCacheStrategy.ALL )
//                .dontTransform()
                .into(ivPromo);

        tvTitleArticle.setText(responseData.getTitle());
        tvDateArticle.setText(RecentUtils.formatDateToDateDMY(responseData.getCreatedAt()));
        tvDescArticle.setText(RecentUtils.fromHtml(responseData.getContent()));
        tvAuthor.setText(responseData.getAuthor());

    }

    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }
    }
}