package com.example.submission1.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.submission1.data.source.local.entity.MoviesEntity
import com.example.submission1.data.source.local.entity.TvshowsEntity
import com.example.submission1.data.source.local.room.FilmDao

class LocalDataSource private constructor(private val mFilmDao: FilmDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(filmDao: FilmDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(filmDao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MoviesEntity> = mFilmDao.getMovies()

    fun getMoviesFavorite(fav : Boolean) : DataSource.Factory<Int, MoviesEntity> = mFilmDao.getMoviesFavorite(fav)

    fun getDetailMovies(moviesId: String): LiveData<MoviesEntity> = mFilmDao.getMoviesById(moviesId)

    fun insertMovies(movies: List<MoviesEntity>) = mFilmDao.insertMovies(movies)

    fun getAllTvshow(): DataSource.Factory<Int, TvshowsEntity> = mFilmDao.getTvshow()

    fun getTvshowFavorite(fav : Boolean) : DataSource.Factory<Int, TvshowsEntity> = mFilmDao.getTvshowFavorite(fav)

    fun getDetailTvshow(tvshowId: String): LiveData<TvshowsEntity> = mFilmDao.getTvshowById(tvshowId)

    fun insertTvshow(tvshow: List<TvshowsEntity>) = mFilmDao.insertTvshow(tvshow)

    fun setMoviesFavorite(movies: MoviesEntity, newState: Boolean) {
        movies.favorite = newState
        mFilmDao.updateMovies(movies)
    }

    fun setTvshowFavorite(tvshow: TvshowsEntity, newState: Boolean) {
        tvshow.favorite = newState
        mFilmDao.updateTvshow(tvshow)
    }
}