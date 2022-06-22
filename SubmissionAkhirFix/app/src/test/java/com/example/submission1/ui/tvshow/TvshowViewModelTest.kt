package com.example.submission1.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.submission1.data.source.local.entity.TvshowsEntity
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
class TvshowViewModelTest {
    private lateinit var viewModel: TvshowViewModel

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
        viewModel=TvshowViewModel(filmRepository)
    }

    @Test
    fun getTvshow() {
        val dataTvshow =  Resource.success(pagedList)
        `when`(dataTvshow.data?.size).thenReturn(15)
        val tvshow = MutableLiveData<Resource<PagedList<TvshowsEntity>>>()
        tvshow.value = dataTvshow

        `when`(filmRepository.getAllTvshow()).thenReturn(tvshow)
        val tvshowsEntities=viewModel.getTvshow().value?.data
        verify(filmRepository).getAllTvshow()
        assertNotNull(tvshowsEntities)
        assertEquals(15, tvshowsEntities?.size)

        viewModel.getTvshow().observeForever(observer)
        verify(observer).onChanged(dataTvshow)
    }
}