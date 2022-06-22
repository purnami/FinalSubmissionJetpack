package com.example.submission1.ui.favorite.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.submission1.data.source.FilmRepository
import com.example.submission1.data.source.local.entity.MoviesEntity
import com.example.submission1.vo.Resource

class FavMoviesViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getFavMovies(): LiveData<Resource<PagedList<MoviesEntity>>> = filmRepository.getMoviesFavorite()
}