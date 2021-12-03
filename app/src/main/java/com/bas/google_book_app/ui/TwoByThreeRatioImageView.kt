package com.bas.google_book_app.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import static com.bas.google_book_app.utilsdata.Constants.THREE;
import static com.bas.google_book_app.utilsdata.Constants.TWO;

public class TwoByThreeRatioImageView extends AppCompatImageView {

    public TwoByThreeRatioImageView(Context context) {
        super(context);
    }

    public TwoByThreeRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoByThreeRatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int twoThreeHeight = MeasureSpec.getSize(widthMeasureSpec) * THREE / TWO;
        int twoThreeHeightSpec =
                MeasureSpec.makeMeasureSpec(twoThreeHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, twoThreeHeightSpec);
    }
}
