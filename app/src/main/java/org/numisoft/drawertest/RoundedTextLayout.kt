package org.numisoft.drawertest

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RoundedTextLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ViewGroup(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = RectF()

    private val extraPadding = context.resources.getDimensionPixelSize(R.dimen.circle_text_extra_padding)
    private val childTextView get() = getChildAt(0) as TextView

    init {
        setWillNotDraw(false)
        parseAttributes(attrs, defStyleAttr)
    }

    private fun parseAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.RoundedTextLayout, defStyleAttr, 0)
            paint.color = array.getColor(R.styleable.RoundedTextLayout_color, Color.TRANSPARENT)
            array.recycle()
        }
    }

    fun setColor(color: Int) {
        paint.color = color
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        checkChildren()

        val child = childTextView

        if (child.visibility != View.GONE) {
            val preferredSize = calculatePreferredSize(child, widthMeasureSpec, heightMeasureSpec)
            val extraPadds = if (child.length() <= 1) 0 else extraPadding

            preferredSize.right += paddingStart + paddingEnd + extraPadds * 2
            preferredSize.bottom += paddingTop + paddingBottom

            val width = measureWidth(widthMeasureSpec, preferredSize.width())
            val height = measureHeight(heightMeasureSpec, preferredSize.height())
            setMeasuredDimension(width, height)
        } else {
            setMeasuredDimension(0, 0)
        }
    }

    private fun calculatePreferredSize(child: TextView, widthMeasureSpec: Int, heightMeasureSpec: Int): Rect {
        measureChild(child, widthMeasureSpec, heightMeasureSpec)

        val childWidth = child.measuredWidth
        val childHeight = child.measuredHeight
        val rect = Rect()

        if (child.length() <= 1) {
            rect.right = Math.max(childWidth, childHeight)
            rect.bottom = rect.right
        } else {
            rect.right = childWidth
            rect.bottom = childHeight
        }

        return rect
    }

    private fun measureWidth(widthMeasureSpec: Int, preferredWidth: Int): Int {
        val measuredWidth = View.MeasureSpec.getSize(widthMeasureSpec)

        when (View.MeasureSpec.getMode(widthMeasureSpec)) {
            View.MeasureSpec.EXACTLY -> return measuredWidth
            View.MeasureSpec.AT_MOST -> return if (preferredWidth <= measuredWidth) preferredWidth else measuredWidth
            View.MeasureSpec.UNSPECIFIED -> return preferredWidth
        }

        return 0
    }

    private fun measureHeight(heightMeasureSpec: Int, preferredHeight: Int): Int {
        val measuredHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        when (View.MeasureSpec.getMode(heightMeasureSpec)) {
            View.MeasureSpec.EXACTLY -> return measuredHeight
            View.MeasureSpec.AT_MOST -> return if (preferredHeight <= measuredHeight) preferredHeight else measuredHeight
            View.MeasureSpec.UNSPECIFIED -> return preferredHeight
        }

        return 0
    }

    private fun checkChildren() {
        if (childCount > 1) throw IllegalStateException("CircleTextView must contain only one child")
        if (getChildAt(0) !is TextView) throw IllegalArgumentException("Child must be instance of TextView")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val child = childTextView
        val childWidth = child.measuredWidth
        val childHeight = child.measuredHeight
        val x = (right - left - paddingStart - paddingEnd) / 2 + paddingStart - childWidth / 2
        val y = (bottom - top - paddingBottom - paddingTop) / 2 + paddingTop - childHeight / 2
        child.layout(x, y, x + childWidth, y + childHeight)
    }

    override fun onDraw(canvas: Canvas) {
        val child = childTextView

        if (child.visibility == View.VISIBLE) {
            val radius = Math.min(width, height) / 2
            rect.right = width.toFloat()
            rect.bottom = height.toFloat()
            canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)
        }
    }
}
