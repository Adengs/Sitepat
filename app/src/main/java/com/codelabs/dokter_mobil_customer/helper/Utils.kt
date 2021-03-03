package com.codelabs.dokter_mobil_customer.helper

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun parseDate(s : String, from : String, to : String) : String {
        if(s != "") {
//            var sdf = SimpleDateFormat(from, Locale.US)
            var sdf = SimpleDateFormat(from, Locale.getDefault())
            //sdf.timeZone = TimeZone.getTimeZone("GMT")
            var date : Date? = null
            date = sdf.parse(s)

            sdf = SimpleDateFormat(to, Locale.getDefault())

            return sdf.format(date)
        }

        return s
    }


    fun fromHtml(html: String?): Spanned? {
        return if (html == null) {
            // return an empty spannable if the html is null
            SpannableString("")
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

}