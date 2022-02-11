package com.eylulcan.moviefragment.ui.moviedetail.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentMoreBinding
import com.eylulcan.moviefragment.ui.ItemListener
import com.eylulcan.moviefragment.ui.moviedetail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoreFragment @Inject constructor() : Fragment(), ItemListener {

    @Inject
    lateinit var moreAdapter: MoreAdapter
    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoreBinding.bind(view)
        binding.moreRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        observeViewModel()
    }

    private fun observeViewModel() {
        detailViewModel.more.observe(viewLifecycleOwner) { movie ->
            if (movie.results.isEmpty()) {
                binding.moreRecyclerView.isVisible = false
                binding.textView.isVisible = true

            } else {
                binding.moreRecyclerView.adapter = moreAdapter
                moreAdapter.setOnItemClickListener {
                    onItemClicked(it)
                }
                moreAdapter.movies = movie.results
            }

        }
    }

    override fun onItemClicked(id: Int) {
        val movieIdBundle = bundleOf(getString(R.string.movieId) to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_movieDetailFragment_self, movieIdBundle, null, null
        )
    }

}