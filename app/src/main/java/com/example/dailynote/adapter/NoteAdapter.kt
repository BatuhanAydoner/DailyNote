package com.example.dailynote.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import com.example.dailynote.R
import com.example.dailynote.database.DatabaseHelper
import com.example.dailynote.model.Note
import kotlinx.android.synthetic.main.rv_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteAdapter(var noteList: ArrayList<Note>): RecyclerView.Adapter<NoteAdapter.MyHolder>() {

    private var myScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Log.e("myApp", noteList[position].note)
        holder.itemView.txtNote.text = noteList[position].note
        holder.itemView.txtId.text = noteList[position].noteId.toString()
        checkDone(holder.itemView.cbDone, noteList[position].done)
        holder.itemView.cbDone.setOnCheckedChangeListener { buttonView, isChecked ->
            var done = 0
            Log.e("myApp", "" + isChecked)
            if (isChecked)
                done = 1
            else
                done = 0
            updateRoom(buttonView.context, isChecked, position)
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    // Update list
    fun updateList(list: List<Note>) {
        noteList.clear()
        noteList.addAll(list)
        notifyDataSetChanged()
    }

    // Check that note is done
    private fun checkDone(cb: CheckBox, done: Int) {
        if (done == 0) {
           cb.isChecked = false
        }else
           cb.isChecked = true
    }

    // Update room when checkbox is checked
    private fun updateRoom(context: Context, isChecked: Boolean, position: Int) {
        var dao = DatabaseHelper(context).dao()
        var note = noteList[position]
        if (isChecked) {
            note.done = 1
        }else {
            note.done = 0
        }
        Log.e("myApp", "size ${noteList.size}")
        myScope.launch {
            Log.e("myApp", "Inside launch")
            var row = dao.update(note)
            Log.e("myApp", "row $row")
        }
    }
}