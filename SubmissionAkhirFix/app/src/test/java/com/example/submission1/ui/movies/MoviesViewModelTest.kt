package com.example.submission1.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.submission1.data.source.local.entity.MoviesEntity
import com.example.submission1.data.source.FilmRepository
import com.example.submission1.vo.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {
    private lateinit var viewModel: MoviesViewModel

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
        viewModel = MoviesViewModel(filmRepository)
    }

    @Test
    fun getMovies() {
        val dataMovies = Resource.success(pagedList)
        `when`(dataMovies.data?.size).thenReturn(15)
        val movies = MutableLiveData<Resource<PagedList<MoviesEntity>>>()
        movies.value = dataMovies

        `when`(filmRepository.getAllMovies()).thenReturn(movies)
        val moviesEntities = viewModel.getMovies().value?.data

        verify(filmRepository).getAllMovies()
        assertNotNull(moviesEntities)
        assertEquals(15, moviesEntities?.size)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dataMovies)
    }

}