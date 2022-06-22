package com.example.submission1.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.submission1.data.source.FilmRepository
import com.example.submission1.data.source.local.entity.TvshowsEntity
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
class FavTvshowViewModelTest {
    private lateinit var viewModel: FavTvshowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvshowsEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvshowsEntity>

    @Before
    fun setUp() {
        viewModel= FavTvshowViewModel(filmRepository)
    }

    @Test
    fun getTvshow() {
        val dataTvshow =  Resource.success(pagedList)
        Mockito.`when`(dataTvshow.data?.size).thenReturn(15)
        val tvshow = MutableLiveData<Resource<PagedList<TvshowsEntity>>>()
        tvshow.value = dataTvshow

        Mockito.`when`(filmRepository.getTvshowFavorite()).thenReturn(tvshow)
        val tvshowsEntities=viewModel.getFavTvshow().value?.data
        Mockito.verify(filmRepository).getTvshowFavorite()
        assertNotNull(tvshowsEntities)
        assertEquals(15, tvshowsEntities?.size)

        viewModel.getFavTvshow().observeForever(observer)
        Mockito.verify(observer).onChanged(dataTvshow)
    }
    @Test
    fun getFavTvshow() {
    }
}