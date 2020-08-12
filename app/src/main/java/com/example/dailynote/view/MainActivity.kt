package com.example.dailynote.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailynote.R
import com.example.dailynote.adapter.NoteAdapter
import com.example.dailynote.database.DatabaseHelper
import com.example.dailynote.model.Note
import com.example.dailynote.modelview.NoteViewModel
import com.example.dailynote.util.showToast
<<<<<<< HEAD
=======
import io.reactivex.rxjava3.core.Completable
>>>>>>> 0f06794... Setup rxjava2 for room database
import kotlinx.android.synthetic.main.note_content.*
import kotlinx.android.synthetic.main.note_recyclerview.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private var myAdapter = NoteAdapter(arrayListOf())
    private var myList = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViewModel()
        initRecylerview()
        observeViewModel()
        buttonEvents()
    }

    // etNote
    private fun buttonEvents() {
        btnCheck.setOnClickListener {
            if (etNote.text.toString().isNotEmpty()) {
                insertData(etNote.text.toString())
            }else {
                showToast(this, "Please type your note!")
            }
        }
    }

    // Setup recyclercview
    private fun initRecylerview() {
        rvNotes.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    // Setup noteViewModel
    private fun setViewModel() {
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.setNotes(this)
    }

    // If database has a note, observe will work
    private fun observeViewModel() {
        noteViewModel.notes.observe(this, Observer {
            it?.let {
                myList = it as ArrayList<Note>
                myAdapter.updateList(it)
                showSatateOfNoListItemText(true)
                showSatateOfRecyclerview(false)
            }
        })
    }

    // If list is not empty txtNoNote state is invisible
    private fun showSatateOfNoListItemText(invisible: Boolean) {
        if (invisible)
            txtNoNote.visibility = View.GONE
        else
            txtNoNote.visibility = View.VISIBLE
    }

    // If list is empty rvNotes state is invisible
    private fun showSatateOfRecyclerview(invisible: Boolean) {
        if (invisible)
            noteInclude.visibility = View.GONE
        else
            noteInclude.visibility = View.VISIBLE
    }

    // Insert note using coroutine scope
    private fun insertData(noteText: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var note = Note(noteText)
            var itemId = DatabaseHelper(this@MainActivity).dao().insert(note)

            launch(Dispatchers.Main) {
                updateRecyclerview(note)
            }
        }
    }

    private fun updateRecyclerview(note: Note) {
        myList.add(note)
        myAdapter.updateList(myList)
    }
}