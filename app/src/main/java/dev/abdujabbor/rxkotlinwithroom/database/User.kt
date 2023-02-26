package dev.abdujabbor.rxkotlinwithroom.database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo
    var name: String? = null

    @ColumnInfo
    var number: String? = null


}