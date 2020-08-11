package com.example.dailynote.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "note")
    var note: Note
) {

}