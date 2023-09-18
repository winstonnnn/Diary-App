package com.daily.mydailylife.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daily.mydailylife.R
import com.daily.mydailylife.ui.adapter.MoodAdapter
import com.daily.mydailylife.ui.util.getMoodImages

class MoodsDialog(context: Context): Dialog(context) {

    private val adapter = MoodAdapter()
    private var listener: DialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_moods)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val width = context.resources.displayMetrics.widthPixels * .8
        val height = context.resources.displayMetrics.heightPixels * .38
        window?.setLayout(width.toInt(),height.toInt())
        window?.setGravity(Gravity.CENTER)
        initAdapter()
    }

    private fun initAdapter() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvMoods)

        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapter
        adapter.submitList(getMoodImages())

        adapter.setOnItemClickListener {_, _, position ->
            listener?.onItemClick(position)
        }
    }

    fun setDialogListener(listener: DialogListener) {
        this.listener = listener
    }
}

fun interface DialogListener {
    fun onItemClick(index: Int)
}