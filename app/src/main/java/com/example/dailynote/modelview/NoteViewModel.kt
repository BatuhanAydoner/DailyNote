package com.example.dailynote.modelview

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dailynote.database.DatabaseHelper
import com.example.dailynote.model.Note
import kotlinx.coroutines.*

class NoteViewModel : ViewModel() {
    var notes = MutableLiveData<List<Note>>()

    fun setNotes(context: Context) {
        getNotesFromDatabase(context)
    }

    // Get all notes fom database
    private fun getNotesFromDatabase(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            var dao = DatabaseHelper(context).dao()

            var list = dao.query()

            withContext(Dispatchers.Main) {
                notes.value = list
                Log.e("myApp", "" + list.size)
            }
        }
    }
}