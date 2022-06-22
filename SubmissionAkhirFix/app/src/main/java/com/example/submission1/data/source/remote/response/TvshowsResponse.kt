package com.example.submission1.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvshowsResponse(
        var filmId: String,
        var title: String,
        var description: String,
        var releaseYear: String,
        var category: String,
        var imagePath: String
): Parcelable