package com.codelabs.dokter_mobil_customer.utils

import android.content.Context
import android.text.Spanned
import android.widget.Toast
import androidx.core.text.HtmlCompat
import java.text.NumberFormat
import java.util.*

object RecentUtils {
    fun fromHtml(html:String?): Spanned {
        if(!html.isNullOrEmpty()){

            return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }

        return HtmlCompat.fromHtml("", HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    fun showToast(context: Context, message:String?){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun rupiahFormat(resource: Any?) : String {
        if(resource != null) {
            val price = resource.toString().toDouble()
            val localeID = Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            val result = numberFormat.format(price.toInt()).toString()
            return result.replace("Rp", "Rp ").replace(",00","")
        }

        return "Rp 0"
    }
}