package com.example.dailynote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "note")
    var note: String
) {
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0
}