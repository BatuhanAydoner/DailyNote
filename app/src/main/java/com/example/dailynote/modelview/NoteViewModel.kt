package com.example.dailynote.modelview

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dailynote.database.DatabaseHelper
import com.example.dailynote.model.Note
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import java.util.*

class NoteViewModel : ViewModel() {
    var notes = MutableLiveData<List<Note>>()

    private var compositeDisposable = CompositeDisposable()

    fun setNotes(context: Context) {
        getNotesFromDatabase(context)
    }

    // Get all notes fom database
    private fun getNotesFromDatabase(context: Context) {
        var dao = DatabaseHelper(context).dao()
        compositeDisposable.add(
            dao.query()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Note>>() {
                    override fun onSuccess(t: List<Note>) {
                        notes.value = t
                    }

                    override fun onError(e: Throwable?) {
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}