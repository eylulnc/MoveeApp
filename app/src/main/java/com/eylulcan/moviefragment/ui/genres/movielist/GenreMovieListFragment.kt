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
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentGenreMovieListBinding
import com.eylulcan.moviefragment.domain.entity.ResultMovieEntity
import com.eylulcan.moviefragment.ui.ItemListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GenreMovieListFragment : Fragment(), ItemListener {

    private var _binding: FragmentGenreMovieListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var genreMovieListAdapter: GenreMovieListAdapter
    private val genresListViewModel: GenreMovieListViewModel by viewModels()
    private var movieList: ArrayList<ResultMovieEntity> = arrayListOf()
    private var selectedGenreId: Int = 0
    private var enableToRequest: Boolean = false
    private var moviesInAPage: List<ResultMovieEntity>? = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedGenreId = arguments?.get(getString(R.string.genreId)) as Int
        genresListViewModel.getMovieListByGenre(
            genreId = selectedGenreId,
            genresListViewModel.lastLoadedPage
        )
        genresListViewModel.lastLoadedPage.inc()
        observeViewModel()
        setUI()
    }

    private fun observeViewModel() {
        genresListViewModel.movies.observe(viewLifecycleOwner) { movie ->
            moviesInAPage =
                if (movie.results.let { moviesInAPage?.containsAll(it) } == true) {
                    emptyList()
                } else {
                    movie.results
                }
            movieList.addAll(moviesInAPage ?: arrayListOf())
            genreMovieListAdapter.movieResult = movieList
            enableToRequest = true
            genreMovieListAdapter.notifyDataSetChanged()
        }
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
            binding.genreMovieListRecyclerView.adapter = genreMovieListAdapter
            genreMovieListAdapter.setOnItemClickListener { id ->
                onItemClicked(id)
            }

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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.genreMovieListRecyclerView.adapter = null
        genresListViewModel.movies.removeObservers(this)
        _binding = null
    }

}