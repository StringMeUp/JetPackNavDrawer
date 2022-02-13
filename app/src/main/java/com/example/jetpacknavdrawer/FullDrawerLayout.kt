package com.example.jetpacknavdrawer

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View

import androidx.drawerlayout.widget.DrawerLayout
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException


class FullDrawerLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null, defstyle: Int = 0
) : DrawerLayout(context, attributeSet, defstyle) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            throw IllegalArgumentException(
                "DrawerLayout must be measured with MeasureSpec.EXACTLY."
            )
        }
        setMeasuredDimension(widthSize, heightSize)

        // Gravity value for each drawer we've seen. Only one of each permitted.
        val foundDrawers = 0
        val childCount = childCount
        for (i in 0 until childCount) {
            val child: View = getChildAt(i)
            if (child.getVisibility() === GONE) {
                continue
            }
            val lp = child.getLayoutParams() as LayoutParams
            if (isContentView(child)) {
                // Content views get measured at exactly the layout's size.
                val contentWidthSpec = MeasureSpec.makeMeasureSpec(
                    widthSize - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY
                )
                val contentHeightSpec = MeasureSpec.makeMeasureSpec(
                    heightSize - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY
                )
                child.measure(contentWidthSpec, contentHeightSpec)
            } else if (isDrawerView(child)) {
                val childGravity = getDrawerViewGravity(child) and Gravity.HORIZONTAL_GRAVITY_MASK
                if (foundDrawers and childGravity != 0) {
                    throw IllegalStateException(
                        "Child drawer has absolute gravity " +
                                gravityToString(childGravity) + " but this already has a " +
                                "drawer view along that edge"
                    )
                }
                val drawerWidthSpec = getChildMeasureSpec(
                    widthMeasureSpec,
                    MIN_DRAWER_MARGIN + lp.leftMargin + lp.rightMargin,
                    lp.width
                )
                val drawerHeightSpec = getChildMeasureSpec(
                    heightMeasureSpec,
                    lp.topMargin + lp.bottomMargin,
                    lp.height
                )
                child.measure(drawerWidthSpec, drawerHeightSpec)
            } else {
                throw IllegalStateException(
                    ("Child " + child + " at index " + i +
                            " does not have a valid layout_gravity - must be Gravity.LEFT, " +
                            "Gravity.RIGHT or Gravity.NO_GRAVITY")
                )
            }
        }
    }

    fun isContentView(child: View): Boolean {
        return (child.getLayoutParams() as LayoutParams).gravity == Gravity.NO_GRAVITY
    }

    fun isDrawerView(child: View): Boolean {
        val gravity = (child.getLayoutParams() as LayoutParams).gravity
        val absGravity = Gravity.getAbsoluteGravity(
            gravity,
            child.getLayoutDirection()
        )
        return (absGravity and (Gravity.LEFT or Gravity.RIGHT)) != 0
    }

    fun getDrawerViewGravity(drawerView: View): Int {
        val gravity = (drawerView.getLayoutParams() as LayoutParams).gravity
        return Gravity.getAbsoluteGravity(gravity, drawerView.getLayoutDirection())
    }

    companion object {
        private val MIN_DRAWER_MARGIN = 0 // dp
        fun gravityToString(gravity: Int): String {
            if ((gravity and Gravity.LEFT) == Gravity.LEFT) {
                return "LEFT"
            }
            return if ((gravity and Gravity.RIGHT) == Gravity.RIGHT) {
                "RIGHT"
            } else Integer.toHexString(gravity)
        }
    }
}