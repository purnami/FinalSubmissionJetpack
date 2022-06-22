package com.example.submission1.ui.tvshow

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission1.R
import com.example.submission1.data.source.local.entity.TvshowsEntity
import com.example.submission1.databinding.ItemsTvshowBinding
import com.example.submission1.ui.detail.DetailTvshowActivity
import com.example.submission1.ui.favorite.tvshow.FavTvshowAdapter

class TvshowAdapter : PagedListAdapter<TvshowsEntity, TvshowAdapter.TvshowViewHolder>(DIFF_CALLBACK) {
        companion object {
            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvshowsEntity>() {
                override fun areItemsTheSame(oldItem: TvshowsEntity, newItem: TvshowsEntity): Boolean {
                    return oldItem.filmId == newItem.filmId
                }

                override fun areContentsTheSame(oldItem:TvshowsEntity, newItem: TvshowsEntity): Boolean {
                    return oldItem == newItem
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvshowViewHolder {
        val itemsTvshowBinding = ItemsTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvshowViewHolder(itemsTvshowBinding)
    }

    override fun onBindViewHolder(holder: TvshowViewHolder, position: Int) {
        val tvshow = getItem(position)
        if (tvshow != null) {
            holder.bind(tvshow)
        }
    }

    class TvshowViewHolder(private val binding:ItemsTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvshow: TvshowsEntity) {
            with(binding) {
                tvItemTitle.text = tvshow.title
                tvItemDesc.text = tvshow.description
                Glide.with(itemView.context)
                        .load(tvshow.imagePath)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error))
                        .into(imgPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvshowActivity::class.java)
                    intent.putExtra(DetailTvshowActivity.EXTRA_TVSHOW, tvshow.filmId)
                    itemView.context.startActivity(intent)
                }

                imgShare.setOnClickListener {
                    val mimeType = "text/plain"
                    ShareCompat.IntentBuilder
                            .from(itemView.context as Activity)
                            .setType(mimeType)
                            .setChooserTitle("Share This TV Show Now")
                            .setText(tvshow.title)
                            .startChooser()
                }
            }
        }

    }
}

