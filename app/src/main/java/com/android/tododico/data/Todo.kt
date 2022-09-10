package com.android.tododico.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(

    val title : String,
    val description : String,
    var isDone : Boolean = false,

    @PrimaryKey(autoGenerate = true)
    val id : Long? = null

)
