package com.eylulcan.moviefragment.ui.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.databinding.DiscoverChildRecyclerRowBinding
import com.eylulcan.moviefragment.model.ResultMovie
import com.eylulcan.moviefragment.util.Utils
import me.samlss.broccoli.Broccoli
import java.util.HashMap

class DiscoverChildAdapter(private val children : List<ResultMovie>, private val listener: MovieListener)
    : RecyclerView.Adapter<DiscoverChildAdapter.ViewHolder>(){

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
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var broccoli = mViewPlaceholderManager[holder.itemView]
        if (broccoli == null) {
            broccoli = Broccoli()
            mViewPlaceholderManager[holder.itemView] = broccoli
        }
        setPlaceholders(holder)
        var task: Runnable? = mTaskManager[holder.itemView]
        if (task == null){
            task = Runnable {
                run{
                    removePlaceholders()
                    val child = children[position]
                    setPlaceholders(holder)
                    Glide.with(holder.binding.movieListRecyclerViewImage).load(setImageUrl(child.posterPath))
                        .into(holder.binding.movieListRecyclerViewImage)
                    holder.binding.movieListRecyclerViewName.text = child.title
                    holder.itemView.setOnClickListener {
                        listener.onMovieClicked(child)
                    }
                }
            }
            mTaskManager[holder.itemView] = task
        } else{
            holder.itemView.removeCallbacks(task)
        }
        holder.itemView.postDelayed(task, 1000)
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

}