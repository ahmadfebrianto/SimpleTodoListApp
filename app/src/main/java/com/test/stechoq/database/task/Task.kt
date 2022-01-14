package com.test.stechoq.database.task

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey
    val id: Int,

    @NonNull
    @ColumnInfo(name = "task_name")
    val name: String,

    @NonNull
    @ColumnInfo(name = "task_description")
    val description: String
)