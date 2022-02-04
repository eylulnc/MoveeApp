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
import com.eylulcan.moviefragment.ui.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentArtistBinding
import com.eylulcan.moviefragment.domain.entity.ArtistResultEntity
import com.eylulcan.moviefragment.domain.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val SPAN_COUNT_PHONE = 3
private const val SPAN_COUNT_TABLET = 4

@AndroidEntryPoint
class ArtistFragment @Inject constructor(private val artistAdapter: ArtistAdapter): Fragment(),
    ItemListener {

    private val artistViewModel: ArtistViewModel by viewModels()
    private lateinit var binding: FragmentArtistBinding
    private var artistList: ArrayList<ArtistResultEntity> = arrayListOf()
    private var enableToRequest: Boolean = false
    private var moviesInAPage: List<ArtistResultEntity>? = emptyList()

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
        binding.artistRecyclerView.adapter = artistAdapter
        artistAdapter.setOnItemClickListener { id -> onItemClicked(id) }
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
        artistViewModel.artistEntity.observe(viewLifecycleOwner) { popularPeopleList ->
            moviesInAPage =
                if (popularPeopleList.results.let { moviesInAPage?.containsAll(it) } == true) {
                    emptyList()
                } else {
                    popularPeopleList.results
                }
            artistList.addAll(moviesInAPage ?: arrayListOf())
            artistAdapter.artistResult = artistList
            enableToRequest = true
            artistAdapter.notifyDataSetChanged()
        }
    }

    override fun onStop() {
        super.onStop()
        artistViewModel.artistEntity.removeObservers(this)
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