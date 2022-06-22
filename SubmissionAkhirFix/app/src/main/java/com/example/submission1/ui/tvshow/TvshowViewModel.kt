package com.example.submission1.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.submission1.data.source.local.entity.TvshowsEntity
import com.example.submission1.data.source.FilmRepository
import com.example.submission1.vo.Resource

class TvshowViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getTvshow(): LiveData<Resource<PagedList<TvshowsEntity>>> = filmRepository.getAllTvshow()
}
