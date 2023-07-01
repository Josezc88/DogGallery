package com.example.doggallery.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Dogs")
class DogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("image") val image: String = ""
)