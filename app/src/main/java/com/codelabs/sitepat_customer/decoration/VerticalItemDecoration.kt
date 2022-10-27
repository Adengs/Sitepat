package com.codelabs.sitepat_customer.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Indra on 7/25/2019.
 * Codelabs Indonesia
 * indra@codelabs.co.id
 */


class VerticalItemDecoration(private var spacing: Int, private var includeEdge:Boolean? = false, var allPosition:Boolean? = false) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position

        if(includeEdge == true){
            if(position == 0){
                outRect.top = spacing // item top
            }
            outRect.left = spacing
            outRect.right = spacing
            if (position == parent.adapter!!.itemCount - 1) {
                outRect.bottom = spacing
            }
        }

        if(allPosition == true){
            outRect.top = spacing // item top
        }else {
            if (position > 0)
                outRect.top = spacing // item top
        }

    }
}