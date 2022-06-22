package com.example.submission1.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.submission1.data.source.local.entity.TvshowsEntity
import com.example.submission1.data.source.FilmRepository
import com.example.submission1.vo.Resource

class DetailTvshowViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    val tvshowId = MutableLiveData<String>()

    fun setSelectedTvshow(tvshowId: String) {
        this.tvshowId.value = tvshowId
    }

    var detailTvshow: LiveData<Resource<TvshowsEntity>> = Transformations.switchMap(tvshowId) { mTvshowId ->
        filmRepository.getDetailTvshow(mTvshowId)
    }

    fun setFav() {
        val detailTvshowResource = detailTvshow.value
        if (detailTvshowResource != null) {
            val tvshowEntity = detailTvshowResource.data
            val newState = !tvshowEntity!!.favorite
            filmRepository.setTvshowFavorite(tvshowEntity, newState)
        }
    }
}
