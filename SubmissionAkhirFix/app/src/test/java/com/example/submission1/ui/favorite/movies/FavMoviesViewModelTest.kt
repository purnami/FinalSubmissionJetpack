package com.example.submission1.ui.favorite.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.submission1.data.source.FilmRepository
import com.example.submission1.data.source.local.entity.MoviesEntity
import com.example.submission1.vo.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavMoviesViewModelTest {

    private lateinit var viewModel: FavMoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MoviesEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MoviesEntity>

    @Before
    fun setUp(){
        viewModel = FavMoviesViewModel(filmRepository)
    }

    @Test
    fun getFavMovies() {
        val dataMovies = Resource.success(pagedList)
        Mockito.`when`(dataMovies.data?.size).thenReturn(15)
        val movies = MutableLiveData<Resource<PagedList<MoviesEntity>>>()
        movies.value = dataMovies

        Mockito.`when`(filmRepository.getMoviesFavorite()).thenReturn(movies)
        val moviesEntities = viewModel.getFavMovies().value?.data

        Mockito.verify(filmRepository).getMoviesFavorite()
        assertNotNull(moviesEntities)
        assertEquals(15, moviesEntities?.size)

        viewModel.getFavMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dataMovies)
    }

}