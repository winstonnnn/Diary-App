package com.daily.mydailylife.ui.activity.add_note

import android.content.Intent
import android.os.Bundle
import com.daily.mydailylife.data.model.NoteModel
import com.daily.mydailylife.databinding.ActivityAddNoteBinding
import com.daily.mydailylife.ui.activity.BaseActivity
import com.daily.mydailylife.ui.activity.main.MainActivity
import com.daily.mydailylife.ui.activity.main.MainViewModel
import com.daily.mydailylife.ui.activity.view_note.ViewNoteActivity
import com.daily.mydailylife.ui.dialog.DialogListener
import com.daily.mydailylife.ui.dialog.MoodsDialog
import com.daily.mydailylife.ui.extension.getDateToday
import com.daily.mydailylife.ui.extension.getFormattedDateToday
import com.daily.mydailylife.ui.extension.parseDate
import com.daily.mydailylife.ui.extension.toastShort
import com.daily.mydailylife.ui.util.Constants
import com.daily.mydailylife.ui.util.getMoodByIndex
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddOrUpdateNoteActivity: BaseActivity<ActivityAddNoteBinding, MainViewModel>(
    ActivityAddNoteBinding::inflate,
    MainViewModel::class
) {
    private lateinit var date: String
    private var isUpdateNote = false
    private var noteModel: NoteModel? = null
    private var mood: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isUpdateNote()) {
            getDate()
        }
        initOnClicks()
    }

    private fun initOnClicks() {
        binding.apply {
            btnBack.setOnClickListener { finish() }
            btnSave.setOnClickListener { saveNote() }
            ivMood.setOnClickListener { showMoodDialog() }
        }
    }

    private fun getDate() {
        val dateToday = getFormattedDateToday()
        date = getDateToday()

        binding.apply {
            tvNumDay.text = dateToday.dayNum
            tvMonthYear.text = String.format("%s %s", dateToday.month, dateToday.year)
            tvTextDay.text = dateToday.dayName
        }
    }

    private fun saveNote() {
        binding.apply {
            if (edtTitle.text.isEmpty()) {
                this@AddOrUpdateNoteActivity.toastShort("Please add title")
                return
            }
            if (edtDescription.text.isEmpty()) {
                this@AddOrUpdateNoteActivity.toastShort("Please add more experience")
                return
            }

            val stringBuilder = StringBuilder()
            if (edtDescription.text.toString().contains("#")) {
                val hashtags = edtDescription.text.toString().substringAfter("#")
                stringBuilder.append(hashtags)
            }

            val note = NoteModel(
                noteModel?.id ?: 0,
                    date,
                edtTitle.text.toString(),
                edtDescription.text.toString(),
                stringBuilder.toString(),
                mood
            )

            if (isUpdateNote) {
                viewModel.updateNote(note)
            } else {
                viewModel.insertNote(note)
            }

            val intent = Intent(this@AddOrUpdateNoteActivity, ViewNoteActivity::class.java).apply {
                putExtra(Constants.NOTE_ITEM, note)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun isUpdateNote(): Boolean {
        noteModel = intent.extras?.getSerializable(Constants.NOTE_ITEM) as NoteModel?

        if (noteModel != null) {
            noteModel?.let { model->
                binding.apply {
                    date = model.date
                    val dates = parseDate(date)

                    tvNumDay.text = dates.dayNum
                    tvMonthYear.text = String.format("%s %s", dates.month, dates.year)
                    tvTextDay.text = dates.dayName

                    edtTitle.setText(model.title)
                    edtDescription.setText(model.description)
                    ivMood.setImageResource(getMoodByIndex(model.mood))
                }
            }
            isUpdateNote = true
            return true
        }

        isUpdateNote = false
        return false
    }

    private fun showMoodDialog() {
        val dialog = MoodsDialog(this)
        dialog.setDialogListener {
            binding.ivMood.setImageResource(getMoodByIndex(it))
            mood = it
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun onBackPressed() {
        if (isUpdateNote) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            finish()
        }
    }
}