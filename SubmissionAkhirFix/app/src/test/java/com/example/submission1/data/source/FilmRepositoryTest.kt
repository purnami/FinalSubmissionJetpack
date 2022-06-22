package com.example.submission1.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.submission1.data.source.local.LocalDataSource
import com.example.submission1.data.source.local.entity.MoviesEntity
import com.example.submission1.data.source.local.entity.TvshowsEntity
import com.example.submission1.data.source.remote.RemoteDataSource
import com.example.submission1.utils.AppExecutors
import com.example.submission1.utils.DataFilms
import com.example.submission1.utils.LiveDataTestUtil
import com.example.submission1.utils.PagedListUtil
import com.example.submission1.vo.Resource
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.junit.Rule
import org.mockito.Mockito.*


class FilmRepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)

    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val filmRepository = FakeFilmRepository(remote, local, appExecutors)

    private val movieResponses = DataFilms.generateRemoteDataMovies()
    private val movieId = movieResponses[0].filmId
    private val tvshowResponses = DataFilms.generateRemoteDataTvshows()
    private val tvshowId = tvshowResponses[0].filmId

    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        filmRepository.getAllMovies()

        val moviesEntities = Resource.success(PagedListUtil.mockPagedList(DataFilms.generateDataMovies()))
        verify(local).getAllMovies()
        assertNotNull(moviesEntities.data)
        assertEquals(movieResponses.size.toLong(), moviesEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvshow() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvshowsEntity>
        `when`(local.getAllTvshow()).thenReturn(dataSourceFactory)
        filmRepository.getAllTvshow()

        val tvshowEntities = Resource.success(PagedListUtil.mockPagedList(DataFilms.generateDataMovies()))

        verify(local).getAllTvshow()
        assertNotNull(tvshowEntities.data)
        assertEquals(tvshowResponses.size.toLong(), tvshowEntities.data?.size?.toLong())
    }

    @Test
    fun getDetailMovies() {
        val dataMovies = MutableLiveData<MoviesEntity>()
        dataMovies.value = DataFilms.generateDataMovies()[0]
        Mockito.`when`(local.getDetailMovies(movieId)).thenReturn(dataMovies)

        val moviesEntities = LiveDataTestUtil.getValue(filmRepository.getDetailMovies(movieId))
        verify(local).getDetailMovies(movieId)
        assertNotNull(moviesEntities)
        assertNotNull(moviesEntities.data?.title)
        assertEquals(movieResponses[0].title, moviesEntities.data?.title)
    }

    @Test
    fun getDetailTvshow() {
        val dataTvshow = MutableLiveData<TvshowsEntity>()
        dataTvshow.value = DataFilms.generateDataTvshows()[0]
        Mockito.`when`(local.getDetailTvshow(tvshowId)).thenReturn(dataTvshow)

        val tvshowEntities = LiveDataTestUtil.getValue(filmRepository.getDetailTvshow(tvshowId))
        verify(local).getDetailTvshow(tvshowId)
        assertNotNull(tvshowEntities.data)
        assertNotNull(tvshowEntities.data?.title)
        assertEquals(tvshowResponses[0].title, tvshowEntities.data?.title)
    }

    @Test
    fun getMoviesFavorite(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        `when`(local.getMoviesFavorite(true)).thenReturn(dataSourceFactory)
        filmRepository.getMoviesFavorite()

        val moviesEntities = Resource.success(PagedListUtil.mockPagedList(DataFilms.generateDataMovies()))

        verify(local).getMoviesFavorite(true)
        assertNotNull(moviesEntities.data)
        assertEquals(movieResponses.size.toLong(), moviesEntities.data?.size?.toLong())
    }

    @Test
    fun getTvshowFavorite(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvshowsEntity>
        `when`(local.getTvshowFavorite(true)).thenReturn(dataSourceFactory)
        filmRepository.getTvshowFavorite()

        val tvshowEntities = Resource.success(PagedListUtil.mockPagedList(DataFilms.generateDataMovies()))

        verify(local).getTvshowFavorite(true)
        assertNotNull(tvshowEntities.data)
        assertEquals(tvshowResponses.size.toLong(), tvshowEntities.data?.size?.toLong())
    }
}