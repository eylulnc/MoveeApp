package com.eylulcan.moviefragment.ui.genres.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.ItemListener
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentGenreMovieListBinding
import com.eylulcan.moviefragment.model.ResultMovie

class GenreMovieListFragment : Fragment(), ItemListener {

    private lateinit var binding: FragmentGenreMovieListBinding
    private lateinit var genreMovieListAdapter: GenreMovieListAdapter
    private val genresListViewModel: GenreMovieListViewModel by viewModels()
    private var movieList: ArrayList<ResultMovie> = arrayListOf()
    private var selectedGenreId: Int = 0
    private var enableToRequest: Boolean = false
    private var moviesInAPage: List<ResultMovie>? = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_genre_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGenreMovieListBinding.bind(view)
        selectedGenreId = arguments?.get(getString(R.string.genreId)) as Int
        genresListViewModel.getMovieListByGenre(
            genreId = selectedGenreId,
            genresListViewModel.lastLoadedPage
        )
        genresListViewModel.lastLoadedPage++
        observeViewModel()
        setUI()
    }

    private fun observeViewModel() {
        genresListViewModel.movies.observe(viewLifecycleOwner, { movie ->
            moviesInAPage =
                if (movie.results?.let { moviesInAPage?.containsAll(it) } == true) {
                    emptyList()
                } else {
                    movie.results
                }
            movieList.addAll(moviesInAPage ?: arrayListOf())
            genreMovieListAdapter.movieResult = movieList
            enableToRequest = true
            genreMovieListAdapter.notifyDataSetChanged()
        })
    }

    override fun onItemClicked(id: Int) {
        val movieIdBundle = bundleOf(getString(R.string.movieId) to id)
        findNavController().navigate(
            R.id.action_genreMovieListFragment_to_movieDetailFragment,
            movieIdBundle,
            null,
            null
        )
    }

    private fun setUI() {
        binding.genreMovieListRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        genreMovieListAdapter = GenreMovieListAdapter(this)
        binding.genreMovieListRecyclerView.adapter = genreMovieListAdapter

        binding.genreMovieListRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager =
                    binding.genreMovieListRecyclerView.layoutManager as LinearLayoutManager
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisiblePosition == movieList.size - 1 && enableToRequest) {
                    genresListViewModel.getMovieListByGenre(
                        genreId = selectedGenreId,
                        pageNo = genresListViewModel.lastLoadedPage
                    )
                    genresListViewModel.lastLoadedPage++
                    enableToRequest = false
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        genresListViewModel.movies.removeObservers(this)
    }

}