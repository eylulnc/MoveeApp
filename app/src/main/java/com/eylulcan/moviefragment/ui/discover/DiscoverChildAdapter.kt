package com.eylulcan.moviefragment.ui.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.eylulcan.moviefragment.databinding.DiscoverChildRecyclerRowBinding
import com.eylulcan.moviefragment.model.ResultMovie
import com.eylulcan.moviefragment.util.Utils
import me.samlss.broccoli.Broccoli
import java.util.*
import javax.inject.Inject

class DiscoverChildAdapter @Inject constructor(private val glide: RequestManager) :
    RecyclerView.Adapter<DiscoverChildAdapter.ViewHolder>() {

    private var childOnItemClickListener: ((id: Int) -> Unit)? = null
    private val mViewPlaceholderManager: HashMap<View, Broccoli> = HashMap<View, Broccoli>()
    private val mTaskManager: HashMap<View, Runnable> = HashMap<View, Runnable>()

    private val broccoli = Broccoli()
    private val placeholderNeeded = arrayListOf<View>()

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
        holder.itemView.postDelayed(task, 2000)
    }


    fun setImageUrl(poster_path: String?): String =
        Utils.BASE_IMAGE_URL_185.plus(poster_path)

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

    private val diffUtil = object : DiffUtil.ItemCallback<ResultMovie>() {
        override fun areItemsTheSame(
            oldItem: ResultMovie,
            newItem: ResultMovie
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ResultMovie,
            newItem: ResultMovie
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var movieResults: List<ResultMovie>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        childOnItemClickListener = listener
    }
}