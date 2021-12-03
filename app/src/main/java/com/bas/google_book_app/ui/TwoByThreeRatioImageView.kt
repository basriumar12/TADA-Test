package com.bas.google_book_app.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bas.google_book_app.utilsdata.Constants

class TwoByThreeRatioImageView : AppCompatImageView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val twoThreeHeight = MeasureSpec.getSize(widthMeasureSpec) * Constants.THREE / Constants.TWO
        val twoThreeHeightSpec = MeasureSpec.makeMeasureSpec(twoThreeHeight, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, twoThreeHeightSpec)
    }
}