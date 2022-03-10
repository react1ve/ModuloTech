package com.example.template.ui.custom

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.example.presentation.R

class LineRulerView : View {
    private var paint: Paint? = null

    private var MAX_DATA = 100f
    private var MIN_DATA = 0f
    private var viewHeight = 0
    private var viewWidth = 0
    private var valueMultiple = 1

    var progress: Float = MIN_DATA
        set(value) {
            field = if (value in MIN_DATA..MAX_DATA) value else MIN_DATA
            invalidate()
        }

    private var displayNumberType = 1
    private var valueTypeMultiple = 4
    private val longHeightRatio = 4
    private val shortHeightRatio = 3
    private val baseHeightRatio = 3

    private val selectedColor = R.color.line_ruler_selected_color
    private val unselectedColor = R.color.line_ruler_unselected_color

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init()
    }

    private fun init() {
        paint = Paint().apply {
            color = context.getColor(unselectedColor)
            strokeWidth = 5f
            isAntiAlias
            style = Paint.Style.STROKE
        }
        invalidate()
    }

    fun setMaxValue(maxValue: Float): LineRulerView {
        MAX_DATA = maxValue
        return this
    }

    fun setMinValue(minValue: Float): LineRulerView {
        MIN_DATA = minValue
        return this
    }

    fun setMinMaxValue(minValue: Float, maxValue: Float): LineRulerView {
        MIN_DATA = minValue
        MAX_DATA = maxValue
        return this
    }

    override fun onDraw(canvas: Canvas) {
        viewHeight = measuredHeight
        viewWidth = measuredWidth
        val viewInterval = viewHeight.toFloat() / (MAX_DATA - MIN_DATA)
        var i = 1
        while (i < MAX_DATA - MIN_DATA) {
            paint?.color = context.getColor(if ((MAX_DATA - MIN_DATA-i) < progress) selectedColor else unselectedColor)
            if (displayNumberType == DISPLAY_NUMBER_TYPE_MULTIPLE) {
                if ((i + MIN_DATA).toInt() * valueMultiple % valueTypeMultiple == 0) {
                    canvas.drawLine(
                        0f,
                        viewInterval * i,
                        (viewWidth / shortHeightRatio * baseHeightRatio).toFloat(),
                        viewInterval * i,
                        paint!!
                    )
                } else {
                    canvas.drawLine(
                        0f,
                        viewInterval * i,
                        (viewWidth / longHeightRatio * baseHeightRatio).toFloat(),
                        viewInterval * i,
                        paint!!
                    )
                }
            } else {
                if (i % 5 == 0) {
                    canvas.drawLine(
                        0f,
                        viewInterval * i,
                        (viewWidth / shortHeightRatio * baseHeightRatio).toFloat(),
                        viewInterval * i,
                        paint!!
                    )
                } else {
                    canvas.drawLine(
                        0f,
                        viewInterval * i,
                        (viewWidth / longHeightRatio * baseHeightRatio).toFloat(),
                        viewInterval * i,
                        paint!!
                    )
                }
            }
            i++
        }
        canvas.drawLine(
            0f,
            viewHeight.toFloat(),
            (viewWidth / longHeightRatio * baseHeightRatio).toFloat(),
            viewHeight.toFloat(),
            paint!!
        )
        super.onDraw(canvas)
    }

    companion object {
        const val DISPLAY_NUMBER_TYPE_MULTIPLE = 2
    }
}
