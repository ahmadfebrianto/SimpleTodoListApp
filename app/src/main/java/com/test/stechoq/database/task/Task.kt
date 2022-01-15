package com.test.stechoq.database.task

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @NonNull
    @ColumnInfo(name = "task_name")
    var name: String,

    @NonNull
    @ColumnInfo(name = "task_description")
    var description: String
)