package com.codelabs.dokter_mobil_customer.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Indra on 7/25/2019.
 * Codelabs Indonesia
 * indra@codelabs.co.id
 */


class HorizontalItemDecoration : RecyclerView.ItemDecoration {

    private var left: Int = 0
    private var spacing: Int = 0

    constructor(spacing: Int) {
        this.spacing = spacing
    }

    constructor(left: Int, spacing: Int) {
        this.left = left
        this.spacing = spacing
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position

        if (position == 0) {
            outRect.left = left
        } else {
            outRect.left = spacing // item top
        }

        if (position == parent.adapter!!.itemCount - 1) {
            outRect.right = left
        }

    }
}