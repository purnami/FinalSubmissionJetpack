package com.example.submission1.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission1.R
import com.example.submission1.databinding.ActivityDetailMoviesBinding
import com.example.submission1.viewmodel.ViewModelFactory
import com.example.submission1.vo.Status

class DetailMoviesActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIES = "extra_movies"
    }

    private var _activityDetailMoviesBinding: ActivityDetailMoviesBinding? = null

    private val mainBinding get() = _activityDetailMoviesBinding

    private lateinit var viewModel: DetailMoviesViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailMoviesBinding = ActivityDetailMoviesBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailMoviesViewModel::class.java]
        val extras = intent.extras
        if (extras != null) {
            val moviesId = extras.getString(EXTRA_MOVIES)
            if (moviesId != null) {
                viewModel.setSelectedMovies(moviesId)

                viewModel.detailMovies.observe(this, Observer{movies->
                    if (movies != null) {
                        when (movies.status) {
                            Status.LOADING -> mainBinding?.progressBar?.visibility = View.VISIBLE
                            Status.SUCCESS -> if (movies.data != null) {
                                mainBinding?.progressBar?.visibility = View.GONE
                                mainBinding?.detailMovies?.visibility = View.VISIBLE

                                mainBinding?.textTitle?.text = movies.data.title
                                mainBinding?.textDescription?.text = movies.data.description
                                mainBinding?.textReleaseDate?.text = movies.data.releaseDate
                                mainBinding?.textCategory?.text=movies.data.category
                                mainBinding?.textDuration?.text=movies.data.duration
                                Glide.with(this)
                                    .load(movies.data.imagePath)
                                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                                        .error(R.drawable.ic_error))
                                    .into(mainBinding?.imagePoster!!)
                            }
                            Status.ERROR -> {
                                mainBinding?.progressBar?.visibility = View.GONE
                                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })

            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.detailMovies.observe(this, Observer{ movies ->
            if (movies != null) {
                when (movies.status) {
                    Status.LOADING -> mainBinding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCESS -> if (movies.data != null) {
                        mainBinding?.progressBar?.visibility = View.GONE
                        val state = movies.data.favorite
                        setFavState(state)
                    }
                    Status.ERROR -> {
                        mainBinding?.progressBar?.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_fav) {
            viewModel.setFav()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_fav)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_heart_white)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_heart_regular_white)
        }
    }
}