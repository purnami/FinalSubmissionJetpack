package com.example.submission1.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.submission1.data.source.local.entity.MoviesEntity
import com.example.submission1.data.source.FilmRepository
import com.example.submission1.utils.DataFilms
import com.example.submission1.vo.Resource
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMoviesViewModelTest {
    private lateinit var viewModel: DetailMoviesViewModel
    private val dataFilms=DataFilms.generateDataMovies()[0]
    private val moviesId=dataFilms.filmId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<MoviesEntity>>

    @Before
    fun setUp(){
        viewModel=DetailMoviesViewModel(filmRepository)
        viewModel.setSelectedMovies(moviesId!!)
    }

    @Test
    fun getMovies() {
        val dataMovies = Resource.success(DataFilms.generateDataMovies()[0])
        val movies = MutableLiveData<Resource<MoviesEntity>>()
        movies.value = dataMovies

        `when`(filmRepository.getDetailMovies(moviesId)).thenReturn(movies)
        viewModel.detailMovies.observeForever(observer)
        verify(observer).onChanged(dataMovies)
    }
}