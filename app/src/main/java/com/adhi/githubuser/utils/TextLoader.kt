package com.adhi.githubuser.utils

import android.widget.TextView
import com.adhi.githubuser.utils.ViewVisibilityUtil.setGone

object TextLoader {
    fun TextView.loadData(data: String?) {
        if (!data.isNullOrEmpty()) this.text = data else this.setGone()
    }
}