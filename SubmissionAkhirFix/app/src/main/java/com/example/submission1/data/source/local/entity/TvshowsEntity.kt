package com.example.submission1.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshowentities")
data class TvshowsEntity (
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "filmId")
        var filmId: String,

        @ColumnInfo(name = "title")
        var title: String,

        @ColumnInfo(name = "description")
        var description: String,

        @ColumnInfo(name = "releaseYear")
        var releaseYear: String,

        @ColumnInfo(name = "category")
        var category: String,

        @ColumnInfo(name = "favorite")
        var favorite: Boolean = false,

        @ColumnInfo(name = "imagePath")
        var imagePath: String
)