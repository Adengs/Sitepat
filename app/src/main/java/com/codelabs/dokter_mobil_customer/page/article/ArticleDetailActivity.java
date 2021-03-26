package com.codelabs.dokter_mobil_customer.page.article;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.Articles;
import com.codelabs.dokter_mobil_customer.viewmodel.ArticlesDetail;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleDetailActivity extends BaseActivity implements View.OnClickListener {

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

    int idArticle = -1;
    private ArticlesDetail.DataArticlesDetail responseData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_article_detail);
        ButterKnife.bind(this);
        getPrefData();
        initView();
        fetchData();
    }

    private void getPrefData() {
        Intent intent = getIntent();
        idArticle = intent.getIntExtra("article_id",1);
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        ivBack.setOnClickListener(this);
        containerNoData.setVisibility(View.GONE);

    }

    private void fetchData() {
        loadDataArticle();
    }

    public void loadDataArticle() {
        showDialogProgress("Getting data article detail");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getToken();
        Call<ArticlesDetail> call = apiService.getArticlesDetail(auth, idArticle);
        call.enqueue(new Callback<ArticlesDetail>() {
            @Override
            public void onResponse(@NonNull Call<ArticlesDetail> call,@NonNull Response<ArticlesDetail> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    ArticlesDetail data = response.body();
                    if (response.code() == 200) {
                        responseData = data.getData();
                        dataArticleDetail();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    containerNoData.setVisibility(View.VISIBLE);
                    containerArticle.setVisibility(View.GONE);
                    tvNoData.setText(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArticlesDetail> call,@NonNull Throwable t) {
                if (!call.isCanceled()) {
                    hideDialogProgress();;
                    containerNoData.setVisibility(View.VISIBLE);
                    containerArticle.setVisibility(View.GONE);
                    tvNoData.setText(getString(R.string.toast_onfailure));
                }
            }
        });
    }



    public void dataArticleDetail() {
        Picasso.get()
                .load(responseData.getImageUrl())
                .into(ivPromo);

        if (responseData.getIsVideo() == 1) {
            containerVideoYoutube.setVisibility(View.VISIBLE);
            containerTypeArticle.setVisibility(View.GONE);
            containerTypeVideo.setVisibility(View.VISIBLE);
        } else {
            containerVideoYoutube.setVisibility(View.GONE);
            containerTypeVideo.setVisibility(View.GONE);
            containerTypeArticle.setVisibility(View.VISIBLE);
        }


        tvTitleArticle.setText(responseData.getTitle());
        tvDateArticle.setText(RecentUtils.formatDateToDateDMY(responseData.getCreatedAt()));
        tvDescArticle.setText(RecentUtils.fromHtml(responseData.getContent()));

    }

    @Override
    public void onClick(View view) {
        if (ivBack == view) {
            finish();
        }
    }
}