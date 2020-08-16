package com.example.dailynote.modelview

import android.content.Context
import androidx.lifecycle.*
import com.example.dailynote.database.DatabaseHelper
import com.example.dailynote.model.Note
import kotlinx.coroutines.*


class NoteViewModel() : ViewModel() {
    var notes = MutableLiveData<List<Note>>()

    fun setNotes(context: Context) {
        getNotesFromDatabase(context)
    }

    // Get all notes fom database
    private fun getNotesFromDatabase(context: Context) {
        /*var dao = DatabaseHelper(context).dao()
        CoroutineScope(Dispatchers.IO).launch {
            var list = dao.query()
            withContext(Dispatchers.Main) {
                notes.value = list
            }
        }*/
    }

    override fun onCleared() {
        super.onCleared()
    }
}