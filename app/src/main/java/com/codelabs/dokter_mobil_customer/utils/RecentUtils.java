package com.codelabs.dokter_mobil_customer.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;


import com.codelabs.dokter_mobil_customer.connection.AppController;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecentUtils {

    public static int ConvertDpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (connectivityManager != null) {
            netInfo = connectivityManager.getActiveNetworkInfo();
        }
        return (netInfo != null && netInfo.isConnected());
    }

    public static String toRupiah(String currency, String price){
        if(TextUtils.isEmpty(price)){
            price = "0";
        }

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.getDefault());
        //format.setCurrency(Currency.getInstance(currency));
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol(currency);
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setMinimumFractionDigits(0);
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(Double.parseDouble(price));
    }

    public static void setStatusColor(TextView textView, int color){
//        Drawable drawable = ContextCompat.getDrawable(AppController.getInstance().getApplicationContext(), R.drawable.urgentsmall);
//        drawable = DrawableCompat.wrap(drawable);
//        DrawableCompat.setTint(drawable.mutate(), color);

//        drawable.setBounds( 0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

//        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setTextColor(color);
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        if(!TextUtils.isEmpty(html)) {
            html = html.replaceAll("\\\\r\\\\n", "<br />");
            html = html.replaceAll("\\r\\n", "<br />");
            html = html.replaceAll("\\n", "<br />");
        }

        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            if(!TextUtils.isEmpty(html))
                result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
            else
                result = Html.fromHtml("", Html.FROM_HTML_MODE_LEGACY);

        } else {
            if(!TextUtils.isEmpty(html))
                result = Html.fromHtml(html);
            else
                result = Html.fromHtml("");

        }
        return result;
    }

    public static void handleRetrofitError(int code) {
        switch (code) {
            case 404:
                Toast.makeText(AppController.getInstance().getApplicationContext(), "Not found", Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(AppController.getInstance().getApplicationContext(), "Server broken", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(AppController.getInstance().getApplicationContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public static void handleRetrofitError(int code, TextView textView) {
        switch (code) {
            case 404:
                textView.setText("Not found");
                break;
            case 500:
                textView.setText("Server broken");
                break;
            default:
                textView.setText("Unknown error");
                break;
        }
    }


    public static String formatDateTimeToDate(String date) {

//        Date dt;
//        SimpleDateFormat sdf;
//        SimpleDateFormat sdfs;
//        String time1;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat toFormat = new SimpleDateFormat("yyyy MMMM dd", Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;

    }

    public static String formatDateToDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat toFormat = new SimpleDateFormat(" MMMM dd yyyy", Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;
    }

    public static String formatDateToDateDMY(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat toFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;
    }

    public static String formatDateToDateDM(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat toFormat = new SimpleDateFormat("dd MMMM", Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;
    }

    public static String formatDateTimeToTime(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat toFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;
    }

    public static String formatDateTimeToDayTime(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat toFormat = new SimpleDateFormat("EEEE, HH:mm", Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;
    }


    public static Date formatDateTimeToDateFormat(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat toFormat = new SimpleDateFormat("yyyy MMMM dd", Locale.getDefault());
        try {
            Date dt1 = simpleDateFormat.parse(date);
            String result = toFormat.format(dt1);
            return toFormat.parse(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String formatDateToDateString(Date date) {
        SimpleDateFormat toFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return toFormat.format(date);
    }

    public static String formatDateTimeToDateYYYYMMDD(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        final SimpleDateFormat toFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy \nHH:mm a", Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;
    }
    public static String new1formatDateTimeToDateYYYYMMDD(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        final SimpleDateFormat toFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;
    }
    public static String newformatDateTimeToDateYYYYMMDD(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        final SimpleDateFormat toFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a" , Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;
    }

    public static String newformatDateTimeToDateYYYYMMDDnotEEEE(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        final SimpleDateFormat toFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm " , Locale.getDefault());
        Date dt1 = null;
        try {
            dt1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dt1 !=null)
            return toFormat.format(dt1);

        return date;
    }

    public static boolean checkInternet(){
        ConnectivityManager cm =
                (ConnectivityManager) AppController.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        MyLog.logE("isConnected : "+isConnected);
        return isConnected;
    }


}
