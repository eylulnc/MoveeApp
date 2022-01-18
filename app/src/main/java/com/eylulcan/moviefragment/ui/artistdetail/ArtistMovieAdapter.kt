package com.eylulcan.moviefragment.ui.artistdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.ArtistMovieFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.ArtistMovieCredits
import com.eylulcan.moviefragment.util.Utils
import me.samlss.broccoli.Broccoli
import java.util.HashMap

class ArtistMovieAdapter(
    private val movieCredits: ArtistMovieCredits,
    private val artistMovieClickListener: ItemListener
) :
    RecyclerView.Adapter<ArtistMovieAdapter.ViewHolder>() {

    private val mViewPlaceholderManager: HashMap<View, Broccoli> = HashMap<View, Broccoli>()
    private val mTaskManager: HashMap<View, Runnable> = HashMap<View, Runnable>()

    private val broccoli = Broccoli()
    private val placeholderNeeded = arrayListOf<View>()

    class ViewHolder(val binding: ArtistMovieFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ArtistMovieFragmentRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var broccoli = mViewPlaceholderManager[holder.itemView]
        if (broccoli == null) {
            broccoli = Broccoli()
            mViewPlaceholderManager[holder.itemView] = broccoli
        }
        setPlaceholders(holder)
        val movie = movieCredits.cast?.get(position)
        holder.binding.movieNameArtistMovie.text = movie?.title
        Glide.with(holder.binding.root).load(setImageUrl(movie?.posterPath))
            .placeholder(R.color.grey)
            .into(holder.binding.movieImage)
        holder.itemView.setOnClickListener {
            movie?.id?.let { id -> artistMovieClickListener.onItemClicked(id) }
            var task: Runnable? = mTaskManager[holder.itemView]
            if (task == null) {
                task = Runnable {
                    run {
                        removePlaceholders()
                    }
                }
                mTaskManager[holder.itemView] = task
            } else {
                holder.itemView.removeCallbacks(task)
            }
            holder.itemView.postDelayed(task, 2000)
        }
    }

    override fun getItemCount(): Int = movieCredits.cast?.size ?: 0

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_ORIGINAL.plus(poster_path)

    private fun setPlaceholders(holder:ViewHolder) {
        placeholderNeeded.addAll(
            arrayListOf(
                holder.binding.artistMovieRowTemplate
            )
        )
        Utils.addPlaceholders(broccoli = broccoli, placeholderNeeded)
    }

    private fun removePlaceholders() {
        placeholderNeeded.forEach { view ->
            view.apply {
                broccoli.clearPlaceholder(this)
                this.isVisible = false
            }
        }
    }


}