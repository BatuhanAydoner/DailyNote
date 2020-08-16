package com.example.dailynote.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                if (myList.size > 0 ) {
                    CoroutineScope(Dispatchers.IO).launch {
                        var dao = DatabaseHelper(this@MainActivity).dao()
                        dao.delete()
                        withContext(Dispatchers.Main) {
                            myList.clear()
                            myAdapter.updateList(myList)
                        }
                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // etNote
    private fun buttonEvents() {
        btnCheck.setOnClickListener {
            if (etNote.text.toString().isNotEmpty()) {
                insertData(etNote.text.toString())
                etNote.text.clear()
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
        // noteViewModel.setNotes(this)
    }

    // If database has a note, observe will work
    private fun observeViewModel() {
        /*noteViewModel.notes.observe(this, Observer {
            it?.let {
                myList = it as ArrayList<Note>
                myAdapter.updateList(it)
                showStateOfNoListItemText(true)
                showSatateOfRecyclerview(false)
            }
        })*/
        DatabaseHelper(this).dao().query().observe(this, Observer {
            it?.let {
                myList = it as ArrayList<Note>
                myAdapter.updateList(myList)
                showStateOfNoListItemText(true)
                showSatateOfRecyclerview(false)
            }
        })
    }

    // If list is not empty txtNoNote state is invisible
    private fun showStateOfNoListItemText(invisible: Boolean) {
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
            var note = Note(noteText, 0)
            var itemId = DatabaseHelper(this@MainActivity).dao().insert(note)
        }
    }
}