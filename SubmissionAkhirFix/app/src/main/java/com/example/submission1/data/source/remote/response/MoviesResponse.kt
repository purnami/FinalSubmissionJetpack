package com.example.submission1.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesResponse(
        var filmId: String,
        var title: String,
        var description: String,
        var releaseDate: String,
        var category: String,
        var duration: String,
        var imagePath: String
): Parcelable