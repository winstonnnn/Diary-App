package com.daily.mydailylife.di

import android.content.Context
import androidx.room.Room
import com.daily.mydailylife.data.local.MyDatabase
import com.daily.mydailylife.data.local.NoteDao
import com.daily.mydailylife.data.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) : MyDatabase {
        return Room.databaseBuilder(
            context,
            MyDatabase::class.java,
            "my_database"
            ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: MyDatabase) : NoteDao {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun provideRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }
}
