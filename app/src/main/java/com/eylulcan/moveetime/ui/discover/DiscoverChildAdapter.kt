package com.eylulcan.moveetime.ui.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moveetime.databinding.DiscoverChildRecyclerRowBinding
import com.eylulcan.moveetime.domain.entity.ResultMovieEntity
import com.eylulcan.moveetime.domain.util.Utils
import me.samlss.broccoli.Broccoli
import javax.inject.Inject

class DiscoverChildAdapter @Inject constructor(private val glide: RequestManager) :
    RecyclerView.Adapter<DiscoverChildAdapter.ViewHolder>() {

    private val mViewPlaceholderManager: HashMap<View, Broccoli> = HashMap()
    private val mTaskManager: HashMap<View, Runnable> = HashMap()
    private val broccoli = Broccoli()
    private val placeholderNeeded = arrayListOf<View>()
    private var childOnItemClickListener: ((id: Int) -> Unit)? = null

    class ViewHolder(val binding: DiscoverChildRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DiscoverChildRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieResults.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var broccoli = mViewPlaceholderManager[holder.itemView]
        if (broccoli == null) {
            broccoli = Broccoli()
            mViewPlaceholderManager[holder.itemView] = broccoli
        }
        setPlaceholders(holder)
        val child = movieResults[position]
        glide.load(setImageUrl(child.posterPath))
            .into(holder.binding.movieListRecyclerViewImage)
        holder.binding.movieListRecyclerViewName.text = child.title
        holder.itemView.setOnClickListener {
            child.id?.let { id -> childOnItemClickListener?.let { it1 -> it1(id) } }
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


    private fun setPlaceholders(holder: ViewHolder) {
        placeholderNeeded.addAll(
            arrayListOf(
                holder.binding.discoverRowTemplate
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

    fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_185.plus(poster_path)


    private val diffUtil = object : DiffUtil.ItemCallback<ResultMovieEntity>() {
        override fun areItemsTheSame(
            oldItem: ResultMovieEntity,
            newItem: ResultMovieEntity
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ResultMovieEntity,
            newItem: ResultMovieEntity
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var movieResults: List<ResultMovieEntity>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        childOnItemClickListener = listener
    }
}
