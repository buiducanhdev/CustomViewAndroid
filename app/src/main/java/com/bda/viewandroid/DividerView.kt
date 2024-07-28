package com.bda.viewandroid

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import com.google.android.material.shape.MaterialShapeDrawable

class DividerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var TAG = "CustomButton"
    private var thickness = 0

    private var insetStart = 0
    private var insetEnd = 0
    private var color = 0
    private var dividerDrawable: MaterialShapeDrawable? = null

    init {
        dividerDrawable = MaterialShapeDrawable()
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DividerView,
            0, 0
        ).apply {
            try {
                thickness = getDimensionPixelSize(
                    R.styleable.DividerView_dividerThickness, resources.getDimensionPixelSize(
                        R.dimen.divider_thickness
                    )
                )

                insetStart =
                    getDimensionPixelOffset(
                        R.styleable.DividerView_dividerInsetStart,
                        0
                    )

                insetEnd = getDimensionPixelOffset(
                    R.styleable.DividerView_dividerInsetEnd,
                    0
                )

                color = getColor(R.styleable.DividerView_dividerColor,Color.BLACK)
                dividerDrawable!!.fillColor = ColorStateList.valueOf(color)

            } finally {
                recycle()
            }
        }


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var newThickness = measuredHeight
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            if (thickness > 0 && newThickness != thickness) {
                newThickness = thickness
            }
            setMeasuredDimension(measuredWidth, newThickness)
        }
        Log.d(TAG, "onMeasure: $thickness")
    }

    fun setDividerColor(@ColorInt color: Int) {
        if (this.color != color) {
            this.color = color
            dividerDrawable!!.fillColor = ColorStateList.valueOf(color)
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val isRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
        val left: Int = if (isRtl) insetEnd else insetStart
        val right: Int = if (isRtl) width - insetStart else width - insetEnd
        dividerDrawable?.setBounds(left, 0, right, bottom - top)
        dividerDrawable?.draw(canvas)
    }
}