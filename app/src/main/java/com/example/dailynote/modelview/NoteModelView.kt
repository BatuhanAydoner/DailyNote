package com.example.dailynote.modelview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dailynote.model.Note

class NoteModelView: ViewModel() {
    var notes = MutableLiveData<List<Note>>()

    fun setupNotes(list: ArrayList<Note>) {
        notes.value = list
    }
}