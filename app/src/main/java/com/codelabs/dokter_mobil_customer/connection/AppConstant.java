package com.codelabs.dokter_mobil_customer.connection;

public class AppConstant {
    public static final String AuthToken = "X-APP-TOKEN";
    public static final String AppToken = "PeTShOpQue";

    public static final String client = "app-android";
    public static final String secret = "SRi9VPFNxXZ0pfvFNGPdA2gJ7mN23mCk";

    public static final String AuthTitle = "Authorization";
    public static final String AuthValue = "Bearer";

    public static final String AcceptTitle = "Content-Type";
    public static final String AcceptValue = "application/json";
    public static final long TIMER_AUTO_LOGOUT = 900000;

    public static final String HostAPI = "http://178.128.62.50/bengkelmobil/gateway/";

    public static final String Walkthrough = "general/api/v1/walkthrough";
    public static final String Login = "api/v1/authenticate";
    public static final String GetToken = "api/v1/auth/token";
    public static final String Register = "api/v1/register";
    public static final String ForgotPassword = "api/v1/forgot_password";
    public static final String ChangePassword = "api/v1/change_password";
    public static final String CheckOTP = "api/v1/check_otp";
    public static final String ResendOTP = "api/v1/resend_otp";
    public static final String ResetPassword = "api/v1/reset_password";
    public static final String AboutUs = "general/api/v1/aboutUs";
    public static final String Profile = "api/v1/profile";
    public static final String Promo = "promo/api/v1/promo";
    public static final String PromoDetail = "promo/api/v1/promo/{id}";
    public static final String TypeComplaint = "general/api/v1/typeComplaint";
    public static final String DetailComplaint = "general/api/v1/detailComplaint";
    public static final String CreateComplaint = "general/api/v1/complaint/store";
    public static final String GetComplaintDetail = "general/api/v1/typeComplaintdetail/id={id}";
    public static final String Outlet = "api/v1/outlets";
    public static final String OutletDetail = "api/v1/outlets/{id}";
    public static final String Articles = "api/v1/general/articles";
    public static final String News = "general/api/v1/news";
    public static final String NewsDetail = "general/api/v1/news/detail/id={id}";
    public static final String ArticlesDetail = "api/v1/general/articles/{id}";
    public static final String Brands = "api/v1/brands";
    public static final String BrandTypes = "api/v1/brand_types";



    public static final String getTC = "api/v1/general/terms";
    public static final String getFaq = "api/v1/general/faq";
    public static final String getPrivacyPolicy = "api/v1/general/privacy_policies";
    public static final String getNotif = "api/v1/notifications";
    public static final String getNotifDetail = "api/v1/notifications/{id}";
    public static final String getCustomerCar = "api/v1/customer/cars";
    public static final String getCarDetail = "api/v1/customer/cars/{id}";
    public static final String getDeleteCar = "api/v1/customer/cars/{id}/delete";
    public static final String addCar = "api/v1/customer/cars";
    public static final String editCar = "api/v1/customer/cars/{id}";
    public static final String getHistoryPoint = "api/v1/points";

}
