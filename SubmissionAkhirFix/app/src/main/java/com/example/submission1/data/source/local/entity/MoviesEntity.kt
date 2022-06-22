package com.example.submission1.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviesentities")
data class MoviesEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "filmId")
        var filmId: String,

        @ColumnInfo(name = "title")
        var title: String,

        @ColumnInfo(name = "description")
        var description: String,

        @ColumnInfo(name = "releaseDate")
        var releaseDate: String,

        @ColumnInfo(name = "category")
        var category: String,

        @ColumnInfo(name = "duration")
        var duration: String,

        @ColumnInfo(name = "favorite")
        var favorite: Boolean = false,

        @ColumnInfo(name = "imagePath")
        var imagePath: String
)