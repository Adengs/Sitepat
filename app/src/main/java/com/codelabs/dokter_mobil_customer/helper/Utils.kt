package com.codelabs.dokter_mobil_customer.helper

import android.app.Activity
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.codelabs.dokter_mobil_customer.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun parseDate(s: String, from: String, to: String) : String {
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

    fun createCommonTipViewBottom(activity: Activity, string: String?, rotate: Int): View? {
        val view: View = activity.layoutInflater.inflate(R.layout.layout_tip, null)
        val textView = view.findViewById<TextView>(R.id.tv_text)
        textView.text = string
        val imageView = view.findViewById<ImageView>(R.id.iv_image)
        imageView.rotation = rotate.toFloat()
        return view
    }
    fun createCommonTipViewBottom(activity: Activity, string: String?): View? {
        val view: View = activity.layoutInflater.inflate(R.layout.layout_tip, null)
        val textView = view.findViewById<TextView>(R.id.tv_text)
        textView.text = string
        return view
    }

    fun createCommonTipViewTop(activity: Activity, string: String?, rotate: Int): View? {
        val view: View = activity.layoutInflater.inflate(R.layout.layout_tip_2, null)
        val textView = view.findViewById<TextView>(R.id.tv_text)
        textView.text = string
        val imageView = view.findViewById<ImageView>(R.id.iv_image)
        imageView.rotation = rotate.toFloat()
        return view
    }
    fun createCommonTipViewTop(activity: Activity, string: String?): View? {
        val view: View = activity.layoutInflater.inflate(R.layout.layout_tip_2, null)
        val textView = view.findViewById<TextView>(R.id.tv_text)
        textView.text = string
        return view
    }

    fun createRequestBody(s: String): RequestBody {
        return RequestBody.create(
            MediaType.parse("multipart/from-data"),
            s
        )
    }

    fun createRequestImage(foto : File, name : String): MultipartBody.Part {
        val requestFile =
            RequestBody.create(MediaType.parse("image/*"), foto)
        val paramImage =
            MultipartBody.Part.createFormData(name, foto.getName(), requestFile)

        return paramImage

    }
}