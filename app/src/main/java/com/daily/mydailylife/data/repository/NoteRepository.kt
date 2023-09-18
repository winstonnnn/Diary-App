package com.daily.mydailylife.data.repository

import com.daily.mydailylife.data.local.NoteDao
import com.daily.mydailylife.data.model.NoteModel
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun insertNote(noteModel: NoteModel){
        noteDao.insertNote(noteModel)
    }

    suspend fun updateNote(noteModel: NoteModel){
        noteDao.updateNote(noteModel)
    }

    suspend fun deleteNote(noteModel: NoteModel){
        noteDao.deleteNote(noteModel)
    }

    suspend fun getAllNotes() : List<NoteModel> {
        return noteDao.getAllNotes()
    }
}