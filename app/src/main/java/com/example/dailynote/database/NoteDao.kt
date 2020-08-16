package com.example.dailynote.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dailynote.model.Note
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(vararg note: Note): List<Long>

    @Query("SELECT * FROM notes")
    fun query(): LiveData<List<Note>>

    @Update
    suspend fun update(note: Note): Int

    @Query("DELETE FROM notes")
    suspend fun delete()
}