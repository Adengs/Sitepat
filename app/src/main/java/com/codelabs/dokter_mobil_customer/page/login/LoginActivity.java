package com.codelabs.dokter_mobil_customer.page.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.codelabs.dokter_mobil_customer.R;
import com.codelabs.dokter_mobil_customer.connection.ApiError;
import com.codelabs.dokter_mobil_customer.connection.ApiUtils;
import com.codelabs.dokter_mobil_customer.connection.AppConstant;
import com.codelabs.dokter_mobil_customer.connection.DataManager;
import com.codelabs.dokter_mobil_customer.connection.ErrorUtils;
import com.codelabs.dokter_mobil_customer.connection.RetrofitInterface;
import com.codelabs.dokter_mobil_customer.helper.BaseActivity;
import com.codelabs.dokter_mobil_customer.page.main.MainActivity;
import com.codelabs.dokter_mobil_customer.page.password.ForgotPasswordActivity;
import com.codelabs.dokter_mobil_customer.page.register.RegisterActivity;
import com.codelabs.dokter_mobil_customer.utils.RecentUtils;
import com.codelabs.dokter_mobil_customer.viewmodel.DataLogin;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONException;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    /*declare layout component in here*/

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_email)
    AppCompatEditText edtEmail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_password)
    AppCompatEditText edtPassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_forgot_password)
    AppCompatTextView tvForgotPassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_google)
    RelativeLayout containerGoogle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_fb)
    RelativeLayout containerFb;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_register)
    AppCompatTextView tvRegister;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_eye_password)
    AppCompatImageView imgEyePassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container_login_page)
    CoordinatorLayout containerLoginPage;

    /* declare global variable in here */

    private Boolean showPassword = false;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initSetup();
        initFb();
        initGoogleLogin();

    }

    private void initFb(){
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        AccessToken accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            Map<String, String> params = new HashMap<>();
                                            if (profile!=null) {
                                                params.put("fullname",profile.getName());
                                                params.put("facebook_id",profile.getId());
                                                DataManager.getInstance().setFacebokId(profile.getId());
                                                if (object.has("email")){
                                                    params.put("username", object.getString("email"));
                                                }else{
                                                    params.put("username",object.getString("id"));
                                                }
                                                handleLoginFacebook(params);
                                            } else {
//                                                showToast("Cannot show fb");
                                               showToast("Please check your account" + " " + response.getRawResponse());
                                            }



                                        } catch(JSONException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        showToast("Maaf, telah terjadi kesalahan");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        showToast(exception.getMessage());
                        exception.printStackTrace();

                    }
                });
    }

    private void handleLoginFacebook(Map<String, String> params){
        showDialogProgress("Load data login");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
        Call<DataLogin> call = apiService.doLogin(auth, params);
        call.enqueue(new Callback<DataLogin>() {
            @Override
            public void onResponse(@NonNull Call<DataLogin> call,@NonNull Response<DataLogin> response) {
                hideDialogProgress();
                if (response.isSuccessful()) {
                    DataLogin data = response.body();
                    if (response.code() == 200) {
                        DataManager.getInstance().setToken(data.getData().getToken());
                        DataManager.getInstance().setLoginData(data.getData().getDataCustomer());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                        String currrentDateandTime = sdf.format(new Date());
                        DataManager.getInstance().setLastLogin(currrentDateandTime);
                        DataManager.getInstance().setLogoutDuration(data.getData().getLogout_duration());

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataLogin> call, @NonNull Throwable t) {
                if (!call.isCanceled()){
                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }

    private void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void initSetup() {
        tvForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        containerGoogle.setOnClickListener(this);
        containerFb.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        imgEyePassword.setOnClickListener(this);
    }

    private void initGoogleLogin() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /*validation field in here */

    private boolean valid() {
        if (!RecentUtils.isEmailValid(Objects.requireNonNull(edtEmail.getText()).toString().trim())) {
            showToast("please enter your valid email");
//            Snackbar snackbar = Snackbar.make(containerLoginPage,"please enter your valid email", Snackbar.LENGTH_SHORT);
//            View view = snackbar.getView();
//            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)view.getLayoutParams();
//            params.gravity = Gravity.BOTTOM ;
//            params.setMargins(0,150,0,0);
//            snackbar.show();
            return false;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(edtPassword.getText()).toString().trim())) {
            showToast("please enter your password");
            return false;
        }
        return true;
    }

    /*function response data from API in here */

    public void doLogin() {
        if (!valid())
            return;

        Map<String, String> params = new HashMap<>();
        params.put("username", Objects.requireNonNull(edtEmail.getText()).toString().trim());
        params.put("password", Objects.requireNonNull(edtPassword.getText()).toString().trim());
        showDialogProgress("Load data login");
        RetrofitInterface apiService = ApiUtils.getApiService();
        String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
        Call<DataLogin> call = apiService.doLogin(auth,params);
        call.enqueue(new Callback<DataLogin>() {
            @Override
            public void onResponse(@NonNull Call<DataLogin> call, @NonNull Response<DataLogin> data) {
                hideDialogProgress();
                if (data.isSuccessful()) {
                    DataLogin response = data.body();
                    if (data.code() == 200) {
                        assert response != null;
                        showToast(response.getMessage());
                        DataManager.getInstance().setPassword(edtPassword.getText().toString().trim());
                        DataManager.getInstance().setToken(response.getData().getToken());
                        DataManager.getInstance().setLoginData(response.getData().getDataCustomer());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                        String currrentDateandTime = sdf.format(new Date());
                        DataManager.getInstance().setLastLogin(currrentDateandTime);

                        DataManager.getInstance().setLogoutDuration(response.getData().getLogout_duration());

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(data);
                    showToast(error.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataLogin> call, @NonNull Throwable t) {
                if (!call.isCanceled()){
                    hideDialogProgress();
                    showToast(getString(R.string.toast_onfailure));
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                handleSignInResult(task);
            }else {
                showToast(task.getException().getMessage());
            }
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> result) {
        try {
            GoogleSignInAccount account = result.getResult(ApiException.class);
            Map<String, String> params = new HashMap<>();
            assert account != null;
            params.put("google_id", account.getId());
            params.put("username", account.getEmail());
            params.put("fullname", account.getDisplayName());
            showDialogProgress("Load data login");
            RetrofitInterface apiService = ApiUtils.getApiService();
            String auth = AppConstant.AuthValue + " " + DataManager.getInstance().getTokenAccess();
            Call<DataLogin> call = apiService.doLogin(auth, params);
            call.enqueue(new Callback<DataLogin>() {
                @Override
                public void onResponse(@NonNull Call<DataLogin> call,@NonNull Response<DataLogin> response) {
                    hideDialogProgress();
                    if (response.isSuccessful()) {
                        DataLogin data = response.body();
                        if (response.code() == 200) {
                            DataManager.getInstance().setGoogleId(account.getId());
                            DataManager.getInstance().setFullname(account.getDisplayName());
                            DataManager.getInstance().setEmail(account.getEmail());

                            DataManager.getInstance().setToken(data.getData().getToken());
                            DataManager.getInstance().setLoginData(data.getData().getDataCustomer());

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                            String currrentDateandTime = sdf.format(new Date());
                            DataManager.getInstance().setLastLogin(currrentDateandTime);
                            DataManager.getInstance().setLogoutDuration(data.getData().getLogout_duration());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    } else {
                        ApiError error = ErrorUtils.parseError(response);
                        showToast(error.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DataLogin> call, @NonNull Throwable t) {
                    if (!call.isCanceled()){
                        hideDialogProgress();
                        showToast(getString(R.string.toast_onfailure));
                    }
                }
            });

        } catch (ApiException e) {
            e.printStackTrace();
        }


    }

    /*declare function click in here */

    @Override
    public void onClick(View view) {
       if (btnLogin == view) {
           doLogin();
       }

       if (containerGoogle == view) {
           signInGoogle();
       }

       if (containerFb == view) {
           signInFacebook();
//           Toast.makeText(LoginActivity.this, "On Develop", Toast.LENGTH_SHORT).show();
       }

       if (tvForgotPassword == view) {
           Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
           startActivity(intent);
       }

       if (tvRegister == view) {
           Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
           startActivity(intent);
       }

       if (imgEyePassword == view) {
           assert imgEyePassword != null;
           if (!showPassword) {
               imgEyePassword.setImageResource(R.drawable.ic_eye_gone);
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showPassword = true;
            } else {
               imgEyePassword.setImageResource(R.drawable.ic_eye_view);
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                showPassword = false;
            }
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginManager.getInstance().logOut();

    }

    @Override
    protected void onStop() {
        super.onStop();
        LoginManager.getInstance().logOut();
    }

    private void signInFacebook(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
    }
}