package com.example.submission1.ui.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.submission1.databinding.FragmentTvshowBinding
import com.example.submission1.ui.favorite.FavoriteActivity
import com.example.submission1.viewmodel.ViewModelFactory
import com.example.submission1.vo.Status

class TvshowFragment : Fragment() {
    private var _fragmentTvshowBinding: FragmentTvshowBinding? = null
    private val binding get() = _fragmentTvshowBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _fragmentTvshowBinding = FragmentTvshowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvshowViewModel::class.java]

            val tvshowAdapter = TvshowAdapter()

            viewModel.getTvshow().observe(viewLifecycleOwner, Observer{ tvshow ->
                if (tvshow != null) {
                    when (tvshow.status) {
                        Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding?.progressBar?.visibility = View.GONE
                            tvshowAdapter.submitList(tvshow.data)
                        }
                        Status.ERROR -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(binding?.rvTvshow) {
                this?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = tvshowAdapter
            }

            binding?.favBtn2?.setOnClickListener {
                val intent = Intent(context, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentTvshowBinding = null
    }
}