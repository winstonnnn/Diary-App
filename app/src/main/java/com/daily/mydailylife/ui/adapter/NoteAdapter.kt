package com.daily.mydailylife.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.daily.mydailylife.R
import com.daily.mydailylife.data.model.NoteModel
import com.daily.mydailylife.ui.extension.parseDate
import com.daily.mydailylife.ui.extension.toastShort
import com.daily.mydailylife.ui.util.getMoodByIndex

class NoteAdapter: BaseQuickAdapter<NoteModel, QuickViewHolder>() {

    private var listener: NoteAdapterListener? = null

    fun setAdapterListener(listener: NoteAdapterListener) {
        this.listener = listener
    }
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: NoteModel?) {
        val numDay = holder.getView<TextView>(R.id.tvNumDay)
        val numText = holder.getView<TextView>(R.id.tvTextDay)
        val title = holder.getView<TextView>(R.id.tvTitle)
        val description = holder.getView<TextView>(R.id.tvDescription)
        val hashtag = holder.getView<TextView>(R.id.tvHashTags)
        val mood = holder.getView<ImageView>(R.id.ivMood)
        val delete = holder.getView<ImageView>(R.id.btnDelete)

        item?.apply {
            val date = parseDate(item.date)

            numDay.text = date.dayNum
            numText.text = String.format("%s, %s %s", date.dayName, date.month, date.year)
            title.text = item.title
            description.text = item.description
            if (item.hashtags.isNotEmpty()) {
                hashtag.text = String.format("#%s", item.hashtags)
            }
            mood.setImageResource(getMoodByIndex(item.mood))
        }

        delete.setOnClickListener {
            listener?.onClickDelete(item)
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_diary, parent)
    }
}

fun interface NoteAdapterListener {
    fun onClickDelete(model: NoteModel?)
}