package com.example.submission1.ui.favorite.tvshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.submission1.databinding.FragmentFavTvshowBinding
import com.example.submission1.viewmodel.ViewModelFactory
import com.example.submission1.vo.Status

class FavTvshowFragment : Fragment() {

    private var _fragmentFavTvshowBinding: FragmentFavTvshowBinding? = null
    private val binding get() = _fragmentFavTvshowBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _fragmentFavTvshowBinding = FragmentFavTvshowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavTvshowViewModel::class.java]

            val tvshowAdapter = FavTvshowAdapter()

            viewModel.getFavTvshow().observe(viewLifecycleOwner, Observer{ tvshow ->
                if (tvshow != null) {
                    when (tvshow.status) {
                        Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding?.progressBar?.visibility = View.GONE
                            tvshowAdapter.submitList(tvshow.data)
                            tvshowAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(binding?.rvFavtvshow) {
                this?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = tvshowAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentFavTvshowBinding = null
    }
}