package com.example.submission1.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission1.data.source.FilmRepository
import com.example.submission1.di.Injection
import com.example.submission1.ui.detail.DetailMoviesViewModel
import com.example.submission1.ui.detail.DetailTvshowViewModel
import com.example.submission1.ui.favorite.movies.FavMoviesViewModel
import com.example.submission1.ui.favorite.tvshow.FavTvshowViewModel
import com.example.submission1.ui.movies.MoviesViewModel
import com.example.submission1.ui.tvshow.TvshowViewModel

class ViewModelFactory private constructor(private val mFilmRepository: FilmRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideRepository(context))
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                return MoviesViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(TvshowViewModel::class.java) -> {
                return TvshowViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(DetailMoviesViewModel::class.java) -> {
                return DetailMoviesViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(DetailTvshowViewModel::class.java) -> {
                return DetailTvshowViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(FavMoviesViewModel::class.java) -> {
                return FavMoviesViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(FavTvshowViewModel::class.java) -> {
                return FavTvshowViewModel(mFilmRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}
