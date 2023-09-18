package com.daily.mydailylife.ui.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.daily.mydailylife.R

class MoodAdapter: BaseQuickAdapter<Int, QuickViewHolder>() {
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: Int?) {
        val ivMood = holder.getView<ImageView>(R.id.ivMood)

        item?.let { ivMood.setImageResource(item) }

    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_mood, parent)
    }
}