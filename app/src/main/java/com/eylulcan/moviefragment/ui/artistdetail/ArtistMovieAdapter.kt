package com.eylulcan.moviefragment.ui.artistdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.ArtistMovieFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.domain.entity.CastEntity
import com.eylulcan.moviefragment.domain.util.Utils
import me.samlss.broccoli.Broccoli
import javax.inject.Inject

class ArtistMovieAdapter @Inject constructor(private var glide: RequestManager) :
    RecyclerView.Adapter<ArtistMovieAdapter.ViewHolder>() {

    private val mViewPlaceholderManager: HashMap<View, Broccoli> = HashMap()
    private val mTaskManager: HashMap<View, Runnable> = HashMap()
    private var onItemClickListener: ((id: Int) -> Unit)? = null
    private val broccoli = Broccoli()
    private val placeholderNeeded = arrayListOf<View>()
    private lateinit var view: View

    class ViewHolder(val binding: ArtistMovieFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ArtistMovieFragmentRecyclerRowBinding>(
            inflater,
            R.layout.artist_movie_fragment_recycler_row,
            parent,
            false
        )
        view = inflater.inflate(R.layout.artist_movie_fragment_recycler_row, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var broccoli = mViewPlaceholderManager[holder.itemView]
        if (broccoli == null) {
            broccoli = Broccoli()
            mViewPlaceholderManager[holder.itemView] = broccoli
        }
        setPlaceholders(holder)
        val movie = artistMovieCredits[position]
        holder.binding.movieCredits = movie
        glide.load(setImageUrl(movie.posterPath))
            .into(holder.binding.movieImage)
        holder.itemView.setOnClickListener {
            movie.id.let { id -> onItemClickListener?.let { it1 -> it1(id) } }
        }
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

    override fun getItemCount(): Int = artistMovieCredits.size

    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        onItemClickListener = listener
    }

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_ORIGINAL.plus(poster_path)

    private fun setPlaceholders(holder: ViewHolder) {
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

    private val diffUtil = object : DiffUtil.ItemCallback<CastEntity>() {
        override fun areItemsTheSame(
            oldItem: CastEntity,
            newItem: CastEntity
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: CastEntity,
            newItem: CastEntity
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var artistMovieCredits: List<CastEntity>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

}