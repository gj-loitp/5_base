package vn.loitp.up.a.demo.architectureComponent.room.md

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = Word.TABLE_WORD)
class Word {
    companion object {
        const val DB_NAME_WORD = "database_word"
        const val TABLE_WORD = "table_word"
        const val COL_ID = "id"
        const val COL_WORD = "word"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COL_ID)
    var id: Long = 0

    @ColumnInfo(name = COL_WORD)
    var word: String? = null
}
