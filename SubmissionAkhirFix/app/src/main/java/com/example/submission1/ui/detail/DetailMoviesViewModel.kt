package com.example.submission1.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.submission1.data.source.local.entity.MoviesEntity
import com.example.submission1.data.source.FilmRepository
import com.example.submission1.vo.Resource

class DetailMoviesViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    val moviesId = MutableLiveData<String>()

    fun setSelectedMovies(moviesId: String) {
        this.moviesId.value = moviesId
    }

    var detailMovies: LiveData<Resource<MoviesEntity>> = Transformations.switchMap(moviesId) { mMoviesId ->
        filmRepository.getDetailMovies(mMoviesId)
    }

    fun setFav() {
        val detailMoviesResource = detailMovies.value
        if (detailMoviesResource != null) {
            val moviesEntity = detailMoviesResource.data
            val newState = !moviesEntity!!.favorite
            filmRepository.setMoviesFavorite(moviesEntity, newState)
        }
    }
}
