package com.example.dailynote.modelview

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.dailynote.database.DatabaseHelper
import com.example.dailynote.model.Note
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.util.*

class NoteViewModel() : ViewModel() {
    var notes = MutableLiveData<List<Note>>()

    fun setNotes(context: Context) {
        getNotesFromDatabase(context)
    }

    // Get all notes fom database
    private fun getNotesFromDatabase(context: Context) {
        var dao = DatabaseHelper(context).dao()
        CoroutineScope(Dispatchers.IO).launch {
            var list = dao.query()
            withContext(Dispatchers.Main) {
                notes.value = list
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}