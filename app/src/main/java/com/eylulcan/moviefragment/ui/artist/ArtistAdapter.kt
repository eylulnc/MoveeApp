package com.eylulcan.moviefragment.ui.artist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.ArtistFragmentRecyclerRowBinding
import com.eylulcan.moviefragment.model.PeopleResult
import com.eylulcan.moviefragment.model.ProfileImage
import com.eylulcan.moviefragment.util.Utils
import me.samlss.broccoli.Broccoli
import java.util.HashMap
import javax.inject.Inject

class ArtistAdapter @Inject constructor() :
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

        peopleResult[position].let { artist ->
            holder.binding.artistRecyclerPersonName.text = artist.name
            Glide.with(holder.binding.root).load(setImageUrl(artist.profilePath))
                .placeholder(R.color.grey_light)
                .into(holder.binding.artistRecyclerRowImage)
            holder.binding.artistRecyclerPersonName.text = artist.name
        }
        holder.itemView.setOnClickListener {
            peopleResult[position].id?.let { artistId ->
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

    override fun getItemCount(): Int = peopleResult.size

    private fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_185.plus(poster_path)

    private val diffUtil = object : DiffUtil.ItemCallback<PeopleResult>() {
        override fun areItemsTheSame(
            oldItem: PeopleResult,
            newItem: PeopleResult
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: PeopleResult,
            newItem: PeopleResult
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

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

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var peopleResult: List<PeopleResult>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        onItemClickListener = listener
    }

}