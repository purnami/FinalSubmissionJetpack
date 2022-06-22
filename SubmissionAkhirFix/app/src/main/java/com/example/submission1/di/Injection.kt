package com.example.submission1.di

import android.content.Context
import com.example.submission1.data.source.FilmRepository
import com.example.submission1.data.source.local.LocalDataSource
import com.example.submission1.data.source.local.room.FilmDatabase
import com.example.submission1.data.source.remote.RemoteDataSource
import com.example.submission1.utils.AppExecutors
import com.example.submission1.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): FilmRepository {

        val database = FilmDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.filmDao())
        val appExecutors = AppExecutors()

        return FilmRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}