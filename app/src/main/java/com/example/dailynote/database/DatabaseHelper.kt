package com.example.dailynote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailynote.model.Note

@Database(entities = arrayOf(Note::class), version = 1)
abstract class DatabaseHelper: RoomDatabase() {
    abstract fun dao(): NoteDao

    companion object {
        @Volatile
        private var instance: DatabaseHelper? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: createInstaceOfDatabaseHelper(context).also {
                instance = it
            }
        }
        fun createInstaceOfDatabaseHelper(context: Context) = Room.databaseBuilder(context, DatabaseHelper::class.java, "dailyNote.db").build()
    }

}