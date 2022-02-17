package com.eylulcan.moviefragment.ui.artist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.databinding.ArtistFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.domain.entity.ArtistResultEntity
import com.eylulcan.moviefragment.domain.util.Utils
import me.samlss.broccoli.Broccoli
import java.util.*
import javax.inject.Inject

class ArtistAdapter @Inject constructor(private var glide: RequestManager) :
    RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

    private var onItemClickListener: ((id: Int) -> Unit)? = null
    private val mViewPlaceholderManager: HashMap<View, Broccoli> = HashMap<View, Broccoli>()
    private val mTaskManager: HashMap<View, Runnable> = HashMap<View, Runnable>()
    private val broccoli = Broccoli()
    private val placeholderNeeded = arrayListOf<View>()

    class ViewHolder(val binding: ArtistFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ArtistFragmentRecyclerRowBinding.inflate(
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

        artistResult[position].let { artist ->
            holder.binding.artistRecyclerPersonName.text = artist.name
            glide.load(setImageUrl(artist.profilePath)).into(holder.binding.artistRecyclerRowImage)
            holder.binding.artistRecyclerPersonName.text = artist.name
        }
        holder.itemView.setOnClickListener {
            artistResult[position].id?.let { artistId ->
                onItemClickListener?.let { it1 -> it1(artistId) }
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
            holder.itemView.postDelayed(task, 1000)
        }
    }

    override fun getItemCount(): Int = artistResult.size

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_ORIGINAL.plus(poster_path)

    private val diffUtil = object : DiffUtil.ItemCallback<ArtistResultEntity>() {
        override fun areItemsTheSame(
            oldItem: ArtistResultEntity,
            newItem: ArtistResultEntity
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ArtistResultEntity,
            newItem: ArtistResultEntity
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var artistResult: List<ArtistResultEntity>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    private fun setPlaceholders(holder: ViewHolder) {
        placeholderNeeded.addAll(
            arrayListOf(
                holder.binding.artistRowTemplate
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

    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        onItemClickListener = listener
    }

}