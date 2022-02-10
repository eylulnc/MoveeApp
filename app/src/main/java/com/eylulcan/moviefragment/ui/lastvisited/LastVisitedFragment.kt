package com.eylulcan.moviefragment.ui.lastvisited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentLastVisitedBinding
import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.domain.entity.ResultData
import com.eylulcan.moviefragment.ui.ItemListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LastVisitedFragment @Inject constructor() : Fragment(), ItemListener {

    @Inject
    lateinit var lastVisitedAdapter: LastVisitedAdapter
    private lateinit var binding: FragmentLastVisitedBinding
    private val lastVisitedViewModel: LastVisitedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_last_visited, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLastVisitedBinding.bind(view)
        observeViewModel()
        lastVisitedViewModel.readFromDB()
    }

    override fun onItemClicked(id: Int) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to id)
        findNavController().navigate(
            R.id.action_lastVisitedFragment_to_movieDetailFragment,
            movieDataBundle, null, null
        )
    }

    private fun observeViewModel() {
        lastVisitedViewModel.readData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultData.Success -> {
                    result.data?.let { setUI(it) }
                }
                else -> {}
            }
        }
    }

    private fun setUI(movieList: ArrayList<MovieDao>) {
        binding.lastVisitedRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = lastVisitedAdapter
            lastVisitedAdapter.movieList = movieList
            lastVisitedAdapter.setOnItemClickListener {
                onItemClicked(it)
            }
        }
    }

}


