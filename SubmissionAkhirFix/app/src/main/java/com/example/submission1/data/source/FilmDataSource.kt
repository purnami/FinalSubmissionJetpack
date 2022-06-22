package com.example.submission1.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.submission1.data.source.local.entity.MoviesEntity
import com.example.submission1.data.source.local.entity.TvshowsEntity
import com.example.submission1.vo.Resource

interface FilmDataSource {
    fun getAllMovies(): LiveData<Resource<PagedList<MoviesEntity>>>
    fun getAllTvshow(): LiveData<Resource<PagedList<TvshowsEntity>>>
    fun getDetailMovies(moviesId: String): LiveData<Resource<MoviesEntity>>
    fun getDetailTvshow(tvshowId: String): LiveData<Resource<TvshowsEntity>>

    fun getMoviesFavorite():LiveData<Resource<PagedList<MoviesEntity>>>
    fun getTvshowFavorite(): LiveData<Resource<PagedList<TvshowsEntity>>>

    fun setMoviesFavorite(movies: MoviesEntity, state: Boolean)
    fun setTvshowFavorite(tvshow: TvshowsEntity, state: Boolean)
}