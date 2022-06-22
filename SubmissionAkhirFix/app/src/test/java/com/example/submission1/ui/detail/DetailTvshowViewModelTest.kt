package com.example.submission1.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.submission1.data.source.local.entity.TvshowsEntity
import com.example.submission1.data.source.FilmRepository
import com.example.submission1.data.source.local.entity.MoviesEntity
import com.example.submission1.utils.DataFilms
import com.example.submission1.vo.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvshowViewModelTest{
    private lateinit var viewModel: DetailTvshowViewModel
    private val dataFilms= DataFilms.generateDataTvshows()[0]
    private val tvshowId=dataFilms.filmId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvshowsEntity>>

    @Before
    fun setUp(){
        viewModel= DetailTvshowViewModel(filmRepository)
        viewModel.setSelectedTvshow(tvshowId!!)
    }

    @Test
    fun getTvshow() {
        val dataTvshow = Resource.success(DataFilms.generateDataTvshows()[0])
        val tvshow = MutableLiveData<Resource<TvshowsEntity>>()
        tvshow.value = dataTvshow

        `when`(filmRepository.getDetailTvshow(tvshowId)).thenReturn(tvshow)
        viewModel.detailTvshow.observeForever(observer)
        verify(observer).onChanged(dataTvshow)
    }
}