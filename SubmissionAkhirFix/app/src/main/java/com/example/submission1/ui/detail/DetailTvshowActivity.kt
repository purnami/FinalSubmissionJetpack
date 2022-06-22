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
import com.example.submission1.databinding.ActivityDetailTvshowBinding
import com.example.submission1.viewmodel.ViewModelFactory
import com.example.submission1.vo.Status

class DetailTvshowActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    private var _activityDetailTvshowBinding: ActivityDetailTvshowBinding? = null

    private val mainBinding get() = _activityDetailTvshowBinding

    private lateinit var viewModel: DetailTvshowViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailTvshowBinding = ActivityDetailTvshowBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTvshowViewModel::class.java]
        val extras = intent.extras
        if (extras != null) {
            val tvshowId = extras.getString(EXTRA_TVSHOW)
            if (tvshowId != null) {
                viewModel.setSelectedTvshow(tvshowId)

                viewModel.detailTvshow.observe(this, Observer{tvshow->
                    if (tvshow != null) {
                        when (tvshow.status) {
                            Status.LOADING -> mainBinding?.progressBar?.visibility = View.VISIBLE
                            Status.SUCCESS -> if (tvshow.data != null) {
                                mainBinding?.progressBar?.visibility = View.GONE
                                mainBinding?.detailTvshow?.visibility = View.VISIBLE

                                mainBinding?.textTitle?.text = tvshow.data.title
                                mainBinding?.textDescription?.text = tvshow.data.description
                                mainBinding?.textReleaseYear?.text = tvshow.data.releaseYear
                                mainBinding?.textCategory?.text=tvshow.data.category

                                Glide.with(this)
                                    .load(tvshow.data.imagePath)
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
        viewModel.detailTvshow.observe(this, Observer{ movies ->
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