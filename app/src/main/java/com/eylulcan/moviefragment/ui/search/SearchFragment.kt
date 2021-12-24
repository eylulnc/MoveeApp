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
    private lateinit var searchAdapter: SearchAdapter
    private var searchResultList: ArrayList<SearchResult> = arrayListOf()
    private var searchItemsInAPage: List<SearchResult>? = emptyList()

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
            searchItemsInAPage =
                if (results.searchResults?.let { searchItemsInAPage?.containsAll(it) } == true) {
                    emptyList()
                } else {
                    results.searchResults
                }
            searchResultList.addAll(searchItemsInAPage ?: arrayListOf())
            searchAdapter.searchResult = searchResultList
            searchAdapter.notifyDataSetChanged()
        })
    }

    override fun onMovieClicked(id: Int, image: ImageView) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to id)
        val extras = FragmentNavigatorExtras(image to getString(R.string.list_to_detail_transition))
        image.transitionName = image.id.toString()
        this.parentFragment?.findNavController()?.navigate(
            R.id.action_searchFragment_to_movieDetailFragment,
            movieDataBundle, null, extras
        )
    }

    override fun onPersonClicked(id: Int) {
        val artistIdBundle = bundleOf(getString(R.string.artistId) to id)
        findNavController()?.navigate(
            R.id.action_searchFragment_to_artistDetailFragment, artistIdBundle, null, null
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
                searchAdapter.notifyItemRangeRemoved(0,size)
                searchViewModel.lastLoadedPage = 1
                println("encenc setup UI ${searchViewModel.lastLoadedPage}")
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
                    println("encenc onscroll UI ${searchViewModel.lastLoadedPage}")
                    searchViewModel.getSearchResult(searchQuery)
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        searchViewModel.result.removeObservers(this)
    }
}
