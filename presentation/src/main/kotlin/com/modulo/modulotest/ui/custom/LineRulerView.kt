package com.modulo.modulotest.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.modulo.presentation.R

class LineRulerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    companion object {
        private const val MAX_DATA = 100f
        private const val MIN_DATA = 0f
        const val DISPLAY_NUMBER_TYPE_MULTIPLE = 2
    }

    private var minValue = MIN_DATA
    private var maxValue = MAX_DATA
    private var viewHeight = 0
    private var viewWidth = 0
    private var valueMultiple = 1

    var progress: Float = minValue
        set(value) {
            field = if (value in minValue..maxValue) value else minValue
            invalidate()
        }

    private var displayNumberType = 1
    private var valueTypeMultiple = 4
    private val longHeightRatio = 4
    private val shortHeightRatio = 3
    private val baseHeightRatio = 3

    private val selectedColor = R.color.line_ruler_selected_color
    private val unselectedColor = R.color.line_ruler_unselected_color

    private val paint: Paint by lazy {
        Paint().apply {
            color = context.getColor(unselectedColor)
            strokeWidth = 5f
            isAntiAlias
            style = Paint.Style.STROKE
        }
    }

    fun setMinMaxValue(minValue: Float, maxValue: Float) {
        this.minValue = minValue
        this.maxValue = maxValue
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        viewHeight = measuredHeight
        viewWidth = measuredWidth
        val viewInterval = viewHeight.toFloat() / (maxValue - minValue)
        var i = 1
        while (i < maxValue - minValue) {
            paint.color =
                context.getColor(if ((maxValue - minValue - i) < progress) selectedColor else unselectedColor)
            if (displayNumberType == DISPLAY_NUMBER_TYPE_MULTIPLE) {
                if ((i + minValue).toInt() * valueMultiple % valueTypeMultiple == 0) {
                    canvas.drawLine(
                        0f,
                        viewInterval * i,
                        (viewWidth / shortHeightRatio * baseHeightRatio).toFloat(),
                        viewInterval * i,
                        paint
                    )
                } else {
                    canvas.drawLine(
                        0f,
                        viewInterval * i,
                        (viewWidth / longHeightRatio * baseHeightRatio).toFloat(),
                        viewInterval * i,
                        paint
                    )
                }
            } else {
                if (i % 5 == 0) {
                    canvas.drawLine(
                        0f,
                        viewInterval * i,
                        (viewWidth / shortHeightRatio * baseHeightRatio).toFloat(),
                        viewInterval * i,
                        paint
                    )
                } else {
                    canvas.drawLine(
                        0f,
                        viewInterval * i,
                        (viewWidth / longHeightRatio * baseHeightRatio).toFloat(),
                        viewInterval * i,
                        paint
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
            paint
        )
        super.onDraw(canvas)
    }
}
