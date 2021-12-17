package com.eylulcan.moviefragment.ui.genres.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentMoreBinding
import com.eylulcan.moviefragment.model.ResultMovie
import com.eylulcan.moviefragment.ui.genres.GenresViewModel
import com.eylulcan.moviefragment.ui.moviedetail.MovieDetailListener

class GenreMovieListFragment : Fragment(), MovieDetailListener {

    private lateinit var binding: FragmentMoreBinding
    private lateinit var genreMovieListAdapter: GenreMovieListAdapter
    private val genresViewModel: GenresViewModel by activityViewModels()
    private var lastLoadedPage: Int = 1
    private var movieList: ArrayList<ResultMovie> = arrayListOf()
    private var selectedGenreId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoreBinding.bind(view)
        selectedGenreId = arguments?.get("genreId") as Int
        genresViewModel.getMovieListByGenre(genreId = selectedGenreId)
        observeViewModel()
        setUI()
    }

    private fun observeViewModel() {
        genresViewModel.movies.observe(viewLifecycleOwner, { movie ->
            movieList.addAll(movie.results ?: arrayListOf())
            genreMovieListAdapter.movieResult = movieList
            genreMovieListAdapter.notifyDataSetChanged()
        })
    }

    override fun onMovieClicked(id: Int) {
        val movieIdBundle = bundleOf(getString(R.string.movieId) to id)
        findNavController().navigate(
            R.id.action_genreMovieListFragment_to_movieDetailFragment,
            movieIdBundle,
            null,
            null
        )
    }

    private fun setUI(){
        binding.moreRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        genreMovieListAdapter = GenreMovieListAdapter( this)
        binding.moreRecyclerView.adapter = genreMovieListAdapter

        binding.moreRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    lastLoadedPage += 1
                    genresViewModel.getMovieListByGenre(genreId = selectedGenreId, pageNo = lastLoadedPage)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        lastLoadedPage = 1
        val size = movieList.size
        movieList.clear()
        genreMovieListAdapter.notifyItemRangeRemoved(0, size)
    }

}