package com.eylulcan.moviefragment.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentArtistBinding
import com.eylulcan.moviefragment.model.PeopleResult

class ArtistFragment : Fragment(), ArtistListener {

    private val artistViewModel: ArtistViewModel by viewModels()
    private lateinit var binding: FragmentArtistBinding
    private lateinit var artistAdapter: ArtistAdapter
    private var artistList: ArrayList<PeopleResult> = arrayListOf()
    private var lastLoadedPage: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_artist, container, false)
        binding = FragmentArtistBinding.bind(view)
        binding.artistRecyclerView.layoutManager = GridLayoutManager(context, 3)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artistAdapter = ArtistAdapter(this)
        binding.artistRecyclerView.adapter = artistAdapter
        observeViewModel()
        artistViewModel.getPopularPeople()
        setupUI()
    }

    private fun observeViewModel() {
        artistViewModel.popularPeople.observe(viewLifecycleOwner, { popularPeopleList ->
            artistList.addAll(popularPeopleList.results ?: arrayListOf())
            artistAdapter.peopleResult = artistList
            artistAdapter.notifyDataSetChanged()
        })
    }

    override fun onArtistClicked(id: Int) {
        val artistIdBundle = bundleOf(getString(R.string.artistId) to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_artistDetailFragment, artistIdBundle, null, null
        )
    }

    private fun setupUI() {
        binding.artistRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    lastLoadedPage += 1
                    artistViewModel.getPopularPeople(pageNo = lastLoadedPage)
                }
            }
        })
    }
}