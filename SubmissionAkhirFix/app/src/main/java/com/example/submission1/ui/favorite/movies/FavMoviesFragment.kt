package com.example.submission1.ui.favorite.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.submission1.databinding.FragmentFavMoviesBinding
import com.example.submission1.viewmodel.ViewModelFactory
import com.example.submission1.vo.Status

class FavMoviesFragment : Fragment() {

    private var _fragmentFavMoviesBinding: FragmentFavMoviesBinding? = null
    private val binding get() = _fragmentFavMoviesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _fragmentFavMoviesBinding = FragmentFavMoviesBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavMoviesViewModel::class.java]

            val moviesAdapter = FavMoviesAdapter()

            viewModel.getFavMovies().observe(viewLifecycleOwner, Observer{ movies ->
                if (movies != null) {
                    when (movies.status) {
                        Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding?.progressBar?.visibility = View.GONE
                            moviesAdapter.submitList(movies.data)
                        }
                        Status.ERROR -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(binding?.rvFavmovies) {
                this?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = moviesAdapter
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentFavMoviesBinding = null
    }
}