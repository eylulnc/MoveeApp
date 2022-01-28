package com.eylulcan.moviefragment.ui.discover

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.RequestManager
import javax.inject.Inject

private const val FRAGMENT_COUNT = 5

class SliderAdapter @Inject constructor(context: FragmentActivity, private val glide: RequestManager) :
    FragmentStateAdapter(context) {

    private val moviesList : ArrayList<Pair<Int,String>> = arrayListOf()

    override fun getItemCount(): Int {
        return FRAGMENT_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SliderFragment(moviesList[position], glide)
            1 -> SliderFragment(moviesList[position], glide)
            2 -> SliderFragment(moviesList[position], glide)
            3 -> SliderFragment(moviesList[position], glide)
            4 -> SliderFragment(moviesList[position], glide)
            else -> SliderFragment(moviesList[position], glide)
        }
    }

    fun updateList(moviesInfo: ArrayList<Pair<Int,String>>){
        moviesList.clear()
        moviesList.addAll(moviesInfo)
        notifyDataSetChanged()
    }

}