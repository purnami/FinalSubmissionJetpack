package com.example.submission1.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.submission1.data.source.local.LocalDataSource
import com.example.submission1.data.source.local.entity.MoviesEntity
import com.example.submission1.data.source.local.entity.TvshowsEntity
import com.example.submission1.data.source.remote.ApiResponse
import com.example.submission1.data.source.remote.RemoteDataSource
import com.example.submission1.data.source.remote.response.MoviesResponse
import com.example.submission1.data.source.remote.response.TvshowsResponse
import com.example.submission1.utils.AppExecutors
import com.example.submission1.vo.Resource

class FakeFilmRepository constructor(private val remoteDataSource: RemoteDataSource,
                          private val localDataSource: LocalDataSource,
                          private val appExecutors: AppExecutors
) : FilmDataSource {

    override fun getAllMovies(): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object : NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MoviesResponse>>> =
                remoteDataSource.getAllMovies()

            public override fun saveCallResult(movieResponses: List<MoviesResponse>) {
                val movieList = ArrayList<MoviesEntity>()
                for (response in movieResponses) {
                    val movie = MoviesEntity(response.filmId,
                        response.title,
                        response.description,
                        response.releaseDate,
                        response.category,
                        response.duration,
                        false,
                        response.imagePath)

                    movieList.add(movie)
                }

                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()

    }

    override fun getAllTvshow(): LiveData<Resource<PagedList<TvshowsEntity>>> {
        return object : NetworkBoundResource<PagedList<TvshowsEntity>, List<TvshowsResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TvshowsEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvshow(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvshowsEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvshowsResponse>>> =
                remoteDataSource.getAllTvshow()

            public override fun saveCallResult(tvshowResponses: List<TvshowsResponse>) {
                val tvshowList = ArrayList<TvshowsEntity>()
                for (response in tvshowResponses) {
                    val tvshow = TvshowsEntity(response.filmId,
                        response.title,
                        response.description,
                        response.releaseYear,
                        response.category,
                        false,
                        response.imagePath)

                    tvshowList.add(tvshow)
                }

                localDataSource.insertTvshow(tvshowList)
            }
        }.asLiveData()
    }

    override fun getDetailMovies(moviesId: String): LiveData<Resource<MoviesEntity>> {
        return object : NetworkBoundResource<MoviesEntity, List<MoviesResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<MoviesEntity> =
                localDataSource.getDetailMovies(moviesId)

            override fun shouldFetch(data: MoviesEntity?): Boolean =
                data == null

            public override fun createCall(): LiveData<ApiResponse<List<MoviesResponse>>> =
                remoteDataSource.getAllMovies()

            public override fun saveCallResult(moviesResponses: List<MoviesResponse>) {}
        }.asLiveData()
    }

    override fun getDetailTvshow(tvshowId: String): LiveData<Resource<TvshowsEntity>> {
        return object : NetworkBoundResource<TvshowsEntity, List<TvshowsResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<TvshowsEntity> =
                localDataSource.getDetailTvshow(tvshowId)

            override fun shouldFetch(data: TvshowsEntity?): Boolean =
                data == null

            public override fun createCall(): LiveData<ApiResponse<List<TvshowsResponse>>> =
                remoteDataSource.getAllTvshow()

            public override fun saveCallResult(tvshowResponses: List<TvshowsResponse>) {}
        }.asLiveData()
    }

    override fun getMoviesFavorite():LiveData<Resource<PagedList<MoviesEntity>>> {
        return object : NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMoviesFavorite(true), config).build()
            }

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MoviesResponse>>> =
                remoteDataSource.getAllMovies()

            public override fun saveCallResult(movieResponses: List<MoviesResponse>) {}
        }.asLiveData()
    }

    override fun getTvshowFavorite(): LiveData<Resource<PagedList<TvshowsEntity>>> {
        return object : NetworkBoundResource<PagedList<TvshowsEntity>, List<TvshowsResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TvshowsEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvshowFavorite(true), config).build()
            }


            override fun shouldFetch(data: PagedList<TvshowsEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvshowsResponse>>> =
                remoteDataSource.getAllTvshow()

            public override fun saveCallResult(tvshowResponses: List<TvshowsResponse>) {}

        }.asLiveData()
    }

    override fun setMoviesFavorite(movies: MoviesEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setMoviesFavorite(movies, state) }
    override fun setTvshowFavorite(tvshow: TvshowsEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setTvshowFavorite(tvshow, state) }
}
