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
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentArtistBinding
import com.eylulcan.moviefragment.model.PeopleResult
import com.eylulcan.moviefragment.util.Utils

private const val SPAN_COUNT_PHONE = 3
private const val SPAN_COUNT_TABLET = 4

class ArtistFragment : Fragment(), ItemListener {

    private val artistViewModel: ArtistViewModel by viewModels()
    private lateinit var binding: FragmentArtistBinding
    private lateinit var artistAdapter: ArtistAdapter
    private var artistList: ArrayList<PeopleResult> = arrayListOf()
    private var enableToRequest: Boolean = false
    private var moviesInAPage: List<PeopleResult>? = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_artist, container, false)
        binding = FragmentArtistBinding.bind(view)
        if(Utils.isTablet(requireContext())) {
            binding.artistRecyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT_TABLET)
        } else {
            binding.artistRecyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT_PHONE)
        }

        artistAdapter = ArtistAdapter(this)
        binding.artistRecyclerView.adapter = artistAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        artistViewModel.getPopularPeople(artistViewModel.lastLoadedPage)
        artistViewModel.lastLoadedPage++
        setupUI()
    }

    private fun observeViewModel() {
        artistViewModel.popularPeople.observe(this, { popularPeopleList ->
            moviesInAPage =
                if (popularPeopleList.results?.let { moviesInAPage?.containsAll(it) } == true) {
                    emptyList()
                } else {
                    popularPeopleList.results
                }
            artistList.addAll(moviesInAPage ?: arrayListOf())
            artistAdapter.peopleResult = artistList
            enableToRequest = true
            artistAdapter.notifyDataSetChanged()
        })
    }

    override fun onStop() {
        super.onStop()
        artistViewModel.popularPeople.removeObservers(this)
    }

    private fun setupUI() {
        binding.artistRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.artistRecyclerView.layoutManager as GridLayoutManager
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisiblePosition == artistList.size - 1 && enableToRequest) {
                    artistViewModel.getPopularPeople(artistViewModel.lastLoadedPage)
                    artistViewModel.lastLoadedPage++
                    enableToRequest = false
                }
            }
        })
    }

    override fun onItemClicked(id: Int) {
        val artistIdBundle = bundleOf(getString(R.string.artistId) to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_artistDetailFragment, artistIdBundle, null, null
        )
    }

}