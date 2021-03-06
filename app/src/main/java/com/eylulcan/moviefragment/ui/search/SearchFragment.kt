package com.eylulcan.moviefragment.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentSearchBinding
import com.eylulcan.moviefragment.domain.entity.SearchResultEntity
import com.eylulcan.moviefragment.domain.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val SPAN_COUNT_PHONE = 3
private const val SPAN_COUNT_TABLET = 4

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchListener {

    @Inject
    lateinit var searchAdapter: SearchAdapter
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    private var searchQuery: String = ""
    private var searchResultList: ArrayList<SearchResultEntity> = arrayListOf()
    private var searchItemsInAPage: List<SearchResultEntity>? = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Utils.isTablet(requireContext().applicationContext)) {
            binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext().applicationContext, SPAN_COUNT_TABLET)
        } else {
            binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext().applicationContext, SPAN_COUNT_PHONE)
        }
        binding.searchRecyclerView.adapter = searchAdapter
        searchAdapter.setOnItemClickListener(this)
        observeViewModel()
        setupUI()
    }

    private fun observeViewModel() {
        searchViewModel.results.observe(viewLifecycleOwner) { results ->
            searchItemsInAPage =
                if (results.searchResults.let { searchItemsInAPage?.containsAll(it) } == true) {
                    emptyList()
                } else {
                    results.searchResults
                }
            searchResultList.addAll(searchItemsInAPage ?: arrayListOf())
            searchAdapter.searchResult = searchResultList
            searchAdapter.notifyDataSetChanged()
        }
    }

    override fun onMovieClicked(id: Int) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_movieDetailFragment,
            movieDataBundle, null, null
        )
    }

    override fun onPersonClicked(id: Int) {
        val artistIdBundle = bundleOf(getString(R.string.artistId) to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_artistDetailFragment, artistIdBundle, null, null
        )
    }

    private fun setupUI() {
        binding.searchBar.isFocusable = true
        binding.searchBar.isIconified = false
        binding.searchBar.requestFocusFromTouch()
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQuery = binding.searchBar.query.toString()
                val size = searchResultList.size
                searchResultList.clear()
                searchAdapter.notifyItemRangeRemoved(0, size)
                searchViewModel.lastLoadedPage = 1
                searchViewModel.getSearchResult(searchQuery)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery = binding.searchBar.query.toString()
                searchViewModel.lastLoadedPage = 1
                return false
            }
        })
        binding.searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.searchRecyclerView.layoutManager as GridLayoutManager
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisiblePosition == searchResultList.size - 1) {
                    searchViewModel.lastLoadedPage++
                    searchViewModel.getSearchResult(searchQuery)
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        searchViewModel.results.removeObservers(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
