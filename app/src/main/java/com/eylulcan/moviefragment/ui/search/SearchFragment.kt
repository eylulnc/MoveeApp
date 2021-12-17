package com.eylulcan.moviefragment.ui.search

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentSearchBinding
import com.eylulcan.moviefragment.model.SearchResult

private const val SPAN_COUNT = 3

class SearchFragment : Fragment(), SearchListener {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private var searchQuery: String = ""
    private var lastLoadedPage: Int = 1
    private lateinit var searchAdapter: SearchAdapter
    private var searchResultList: ArrayList<SearchResult> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        binding.searchRecyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        searchAdapter = SearchAdapter(this)
        binding.searchRecyclerView.adapter = searchAdapter
        observeViewModel()
        setupUI()
    }

    private fun observeViewModel() {
        searchViewModel.result.observe(viewLifecycleOwner, { results ->
            searchResultList.addAll(results.searchResults ?: arrayListOf())
            searchAdapter.searchResult = searchResultList
            searchAdapter.notifyDataSetChanged()
        })
    }

    override fun onMovieClicked(id: Int, image: ImageView) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to id)
        val extras = FragmentNavigatorExtras(image to getString(R.string.list_to_detail_transition))
        image.transitionName = image.id.toString()
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_movieDetailFragment,
            movieDataBundle, null, extras
        )
    }

    override fun onPersonClicked(id: Int) {
        val artistIdBundle = bundleOf(getString(R.string.artistId) to id)
        this.parentFragment?.parentFragment?.findNavController()?.navigate(
            R.id.action_dashboardFragment_to_artistDetailFragment, artistIdBundle, null, null
        )
    }

    private fun setupUI() {
        binding.searchBar.isSubmitButtonEnabled = true
        binding.searchBar.isFocusable = true
        binding.searchBar.isIconified = false
        binding.searchBar.requestFocusFromTouch()
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQuery = binding.searchBar.query.toString()
                searchViewModel.getSearchResult(searchQuery)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery = binding.searchBar.query.toString()
                return false
            }
        })
        binding.searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    lastLoadedPage += 1
                    searchViewModel.getSearchResult(query = searchQuery, pageNo = lastLoadedPage)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        lastLoadedPage = 1
        val size = searchResultList.size
        searchResultList.clear()
        searchAdapter.notifyItemRangeRemoved(0, size)
    }
}
