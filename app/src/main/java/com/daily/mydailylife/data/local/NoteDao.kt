package com.daily.mydailylife.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.daily.mydailylife.data.model.NoteModel

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(noteModel: NoteModel) : Long

    @Update
    suspend fun updateNote(noteModel: NoteModel)

    @Query("SELECT * FROM note_tbl")
    suspend fun getAllNotes(): List<NoteModel>

    @Delete
    suspend fun deleteNote(noteModel: NoteModel)
}