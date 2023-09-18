package com.daily.mydailylife.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daily.mydailylife.data.model.NoteModel
import com.daily.mydailylife.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    private val _noteListResponse = MutableLiveData<List<NoteModel>>()
    val noteListResponse: LiveData<List<NoteModel>> = _noteListResponse

    fun insertNote(noteModel: NoteModel) {
        viewModelScope.launch {
            repository.insertNote(noteModel)
        }
    }

    fun updateNote(noteModel: NoteModel) {
        viewModelScope.launch {
            repository.updateNote(noteModel)
        }
    }

    fun deleteNote(noteModel: NoteModel) {
        viewModelScope.launch {
            repository.deleteNote(noteModel)
        }
    }

    fun getAllNotes() {
        viewModelScope.launch {
            _noteListResponse.postValue(repository.getAllNotes())
        }
    }
}