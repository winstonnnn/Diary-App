package com.daily.mydailylife.ui.activity.view_note

import android.content.Intent
import android.os.Bundle
import com.daily.mydailylife.data.model.NoteModel
import com.daily.mydailylife.databinding.ActivityViewNoteBinding
import com.daily.mydailylife.ui.activity.BaseActivity
import com.daily.mydailylife.ui.activity.add_note.AddOrUpdateNoteActivity
import com.daily.mydailylife.ui.activity.main.MainActivity
import com.daily.mydailylife.ui.extension.parseDate
import com.daily.mydailylife.ui.util.Constants
import com.daily.mydailylife.ui.util.NoneViewModel
import com.daily.mydailylife.ui.util.getMoodByIndex

class ViewNoteActivity: BaseActivity<ActivityViewNoteBinding, NoneViewModel>(
    ActivityViewNoteBinding::inflate,
    NoneViewModel::class
) {
    private var noteModel: NoteModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOnClicks()

        noteModel = intent.extras?.getSerializable(Constants.NOTE_ITEM) as NoteModel
        initViews()
    }

    private fun initViews() {
        binding.apply {
            noteModel?.let {
                val date = parseDate(it.date)
                tvNumDay.text = date.dayNum
                tvMonthYear.text = String.format("%s %s", date.month, date.year)
                tvTextDay.text = date.dayName

                tvTitle.text = it.title
                tvDescription.text = it.description
                ivMood.setImageResource(getMoodByIndex(it.mood))
            }
        }
    }

    private fun initOnClicks() {
        binding.apply {
            btnBack.setOnClickListener { startActivity(Intent(this@ViewNoteActivity, MainActivity::class.java)) }
            btnEdit.setOnClickListener {
                val intent = Intent(this@ViewNoteActivity, AddOrUpdateNoteActivity::class.java).apply {
                    putExtra(Constants.NOTE_ITEM, noteModel)
                }
                startActivity(intent)
            }
        }
    }
}