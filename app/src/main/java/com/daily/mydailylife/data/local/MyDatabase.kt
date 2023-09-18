package com.daily.mydailylife.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daily.mydailylife.data.model.NoteModel

@Database(entities = [NoteModel::class], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao
}