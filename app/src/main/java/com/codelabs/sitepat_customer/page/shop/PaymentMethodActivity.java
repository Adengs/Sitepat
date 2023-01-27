package com.codelabs.sitepat_customer.page.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.codelabs.sitepat_customer.R;
import com.codelabs.sitepat_customer.connection.DataManager;
import com.codelabs.sitepat_customer.page.main.MainActivity;
import com.codelabs.sitepat_customer.page.order.DetailOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentMethodActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.wv)
    WebView wv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_back)
    AppCompatImageView btnBack;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    WebSettings webSettings;

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        ButterKnife.bind(this);

        initSetup();
        initView();

        String url = DataManager.getInstance().getInvoiceUrl();
        Log.e("TAG", "onCreate: " + url );

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(wv, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }

        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebChromeClient(new WebChromeClient());
        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

        });

        wv.addJavascriptInterface(new WebViewJavaScriptInterface(), "Android");

        String baseUrl = "https://api.xendit.co";
        String data = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                "<style type=\"text/css\">" +
                "html, body {margin: 0; padding: 0;}" +
                "iframe {border: none;width: 100%;height: 100%;position: fixed;left: 0;top: 0;}" +
                "</style>" +
                "<script>addEventListener('message', function(e){ try {MobileBridge.postMessage(e['data']);} catch(err) {console.log('Android call error');} }, false);</script>" +
                "</head>" +
                "<body><iframe src='" + url + "'></iframe></body>" +
                "</html>";
        wv.loadUrl(url);
//        wv.loadDataWithBaseURL(baseUrl, data, "text/html", "utf-8", null);
        Log.e("TAG", "onCreate: " + data );
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void initSetup() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentMethodActivity.this, ShopActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
//                moveTaskToBack(true);
            }
        });
    }

    private class WebViewJavaScriptInterface {

        @JavascriptInterface
        public void vtstatus(String statusCode, String message, String reservationCode) {
//            sendBroadcastReceiver(message);
            Log.e("TAG", "vtstatus 1: " + statusCode);
            Log.e("TAG", "vtstatus 2: " + message);
            Log.e("TAG", "vtstatus 3: " + reservationCode);

            Intent intent = new Intent(PaymentMethodActivity.this, DetailOrderActivity.class);
            intent.putExtra("transaction_id", Integer.parseInt(DataManager.getInstance().getTransactionId()));
            Log.e("TAG", "vtstatus: "+ Integer.parseInt(DataManager.getInstance().getTransactionId()) );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
//            DataManager.getInstance().setTransactionId("");
        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(PaymentMethodActivity.this, ShopActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
//        moveTaskToBack(true);
    }

//    private void sendBroadcastReceiver(String message) {
//        Intent intent = new Intent();
//        intent.setAction(Xendit.ACTION_KEY);
//        intent.putExtra(MESSAGE_KEY, message);
//        sendBroadcast(intent);
//    }
}