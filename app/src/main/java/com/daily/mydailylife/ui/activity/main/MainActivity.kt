package com.daily.mydailylife.ui.activity.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.daily.mydailylife.R
import com.daily.mydailylife.data.model.NoteModel
import com.daily.mydailylife.databinding.ActivityMainBinding
import com.daily.mydailylife.ui.activity.BaseActivity
import com.daily.mydailylife.ui.activity.add_note.AddOrUpdateNoteActivity
import com.daily.mydailylife.ui.activity.view_note.ViewNoteActivity
import com.daily.mydailylife.ui.adapter.NoteAdapter
import com.daily.mydailylife.ui.extension.getDateToday
import com.daily.mydailylife.ui.extension.getFormattedDateToday
import com.daily.mydailylife.ui.extension.toastShort
import com.daily.mydailylife.ui.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate,
    MainViewModel::class
) {

    private val noteAdapter = NoteAdapter()
    private var hasNoteToday = false
    private var isDeleteVisible = false
    private var previousOpenedDeleteIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initResponse()
        initOnClicks()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNotes()
    }

    private fun initResponse() {
        viewModel.noteListResponse.observe(this){
            noteAdapter.submitList(it.asReversed())

            it.map { item ->
                if (item.date == getDateToday()) {
                    hasNoteToday = true
                }
            }
        }
    }

    private fun initAdapter(){
        binding.rvDiary.adapter = noteAdapter
        noteAdapter.setAdapterListener{
            //on click of delete
            showConfirmDelete(it)

        }

        noteAdapter.setOnItemClickListener{adapter, _, position ->
            if (isDeleteVisible) {
                hideDeleteButton()
            } else {
                val intent = Intent(this, ViewNoteActivity::class.java).apply {
                    putExtra(Constants.NOTE_ITEM, adapter.getItem(position) as Serializable)
                }
                startActivity(intent)
            }
        }

        //to show delete button
        noteAdapter.setOnItemLongClickListener{_, view, position->
            if (!isDeleteVisible) {
                view.findViewById<CardView>(R.id.parentContainer).translationX = -200f
                view.findViewById<ImageView>(R.id.btnDelete).visibility = View.VISIBLE
                isDeleteVisible = true
                previousOpenedDeleteIndex = position
                true
            } else {
                false
            }
        }

        binding.rvDiary.addOnScrollListener(object : OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                hideDeleteButton()
            }
        })
    }

    private fun hideDeleteButton() {
        if (isDeleteVisible) {
            noteAdapter.recyclerView.children.forEach {
                it.findViewById<CardView>(R.id.parentContainer).translationX = 0f
                it.findViewById<ImageView>(R.id.btnDelete).visibility = View.GONE
            }
            isDeleteVisible = false
        }
    }

    private fun initOnClicks() {
        binding.apply {
            btnAddNote.setOnClickListener {
                hideDeleteButton()

                if (hasNoteToday) {
                    this@MainActivity.toastShort("You already shared your life today.")
                    return@setOnClickListener
                }
                addNoteResultLauncher.launch(Intent(this@MainActivity, AddOrUpdateNoteActivity::class.java))
            }
        }
    }

    private fun showConfirmDelete(model: NoteModel?) {
        val dialog = Dialog(this)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_confirm_delete)

        val width = this.resources.displayMetrics.widthPixels * .8
        dialog.window?.setLayout(width.toInt(),WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)

        dialog.findViewById<TextView>(R.id.btnConfirm).setOnClickListener {
            model?.let { model->
                viewModel.deleteNote(model)
                viewModel.getAllNotes()
                hideDeleteButton()
                dialog.dismiss()
            }
        }
        dialog.findViewById<TextView>(R.id.btnCancel).setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private var addNoteResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.getAllNotes()
    }

    private var isReadyToExit = false
    override fun onBackPressed() {
        if (isReadyToExit) {
            super.onBackPressed()
            return
        }

        isReadyToExit = true
        Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show()

        lifecycleScope.launch {
            delay(2000)
            isReadyToExit = false
        }
    }
}