package com.example.submission1.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.submission1.data.source.local.entity.MoviesEntity
import com.example.submission1.data.source.local.entity.TvshowsEntity

@Dao
interface FilmDao {
    @Query("SELECT * FROM moviesentities")
    fun getMovies(): DataSource.Factory<Int, MoviesEntity>

    @Transaction
    @Query("SELECT * FROM moviesentities WHERE filmId = :moviesId")
    fun getMoviesById(moviesId: String): LiveData<MoviesEntity>

    @Transaction
    @Query("SELECT * FROM moviesentities WHERE favorite = :fav")
    fun getMoviesFavorite(fav: Boolean): DataSource.Factory<Int, MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MoviesEntity>)

    @Update
    fun updateMovies(movies: MoviesEntity)

    @Query("SELECT * FROM tvshowentities")
    fun getTvshow(): DataSource.Factory<Int, TvshowsEntity>

    @Transaction
    @Query("SELECT * FROM tvshowentities WHERE filmId = :tvshowId")
    fun getTvshowById(tvshowId: String): LiveData<TvshowsEntity>

    @Transaction
    @Query("SELECT * FROM tvshowentities WHERE favorite = :fav")
    fun getTvshowFavorite(fav: Boolean): DataSource.Factory<Int, TvshowsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvshow(tvshow: List<TvshowsEntity>)

    @Update
    fun updateTvshow(tvshow: TvshowsEntity)

}