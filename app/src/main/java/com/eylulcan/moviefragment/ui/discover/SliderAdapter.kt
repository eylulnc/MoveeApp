package com.eylulcan.moviefragment.ui.discover

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import javax.inject.Inject

private const val FRAGMENT_COUNT = 5

class SliderAdapter @Inject constructor(  context: FragmentActivity) :
    FragmentStateAdapter(context) {

    private val moviesList : ArrayList<Pair<Int,String>> = arrayListOf()

    override fun getItemCount(): Int {
        return FRAGMENT_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SliderFragment(moviesList[position])
            1 -> SliderFragment(moviesList[position])
            2 -> SliderFragment(moviesList[position])
            3 -> SliderFragment(moviesList[position])
            4 -> SliderFragment(moviesList[position])
            else -> SliderFragment(moviesList[position])
        }
    }

    fun updateList(moviesInfo: ArrayList<Pair<Int,String>>){
        moviesList.clear()
        moviesList.addAll(moviesInfo)
        notifyDataSetChanged()
    }

}