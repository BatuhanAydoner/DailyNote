package com.example.dailynote.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynote.R
import com.example.dailynote.model.Note
import kotlinx.android.synthetic.main.rv_item.view.*

class NoteAdapter(var noteList: ArrayList<Note>): RecyclerView.Adapter<NoteAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.txtNote.text = noteList[position].note
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun updateList(list: List<Note>) {
        noteList.clear()
        noteList.addAll(list)
        notifyDataSetChanged()
    }
}