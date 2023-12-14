package com.adhi.githubuser.ui.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.adhi.githubuser.R

class MyTabLayout: TabLayout {

    private var firstTab: Drawable? = null
    private var secondTab: Drawable? = null
    private var thirdTab: Drawable? = null
    private var indicator: Drawable? = null

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        firstTab = ContextCompat.getDrawable(context, R.drawable.lg_repositori)
        secondTab = ContextCompat.getDrawable(context, R.drawable.lg_followers)
        thirdTab = ContextCompat.getDrawable(context, R.drawable.lg_following)
        indicator = ContextCompat.getDrawable(context, R.drawable.ic_indicator_tab)
    }

    private fun Drawable?.setIconAtTab(position: Int) = getTabAt(position)?.apply { icon = this@setIconAtTab }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setSelectedTabIndicator(indicator)
        tabRippleColor = null
        firstTab.setIconAtTab(0)
        secondTab.setIconAtTab(1)
        thirdTab.setIconAtTab(2)
    }
}