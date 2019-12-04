package vn.loitp.app.activity.demo.architecturecomponent.room

import androidx.room.Entity

@Entity(tableName = "word_table")
data class Word(val word: String)
