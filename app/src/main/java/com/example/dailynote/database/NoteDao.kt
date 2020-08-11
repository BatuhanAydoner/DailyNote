package com.example.dailynote.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailynote.model.Note
import io.reactivex.rxjava3.core.Flowable

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(vararg note: Note): List<Long>

    @Query("SELECT * FROM notes")
    suspend fun query(): List<Note>

    @Query("DELETE FROM notes WHERE note = :text")
    suspend fun delete(text: String)
}