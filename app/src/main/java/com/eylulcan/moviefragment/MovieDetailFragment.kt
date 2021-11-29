package com.eylulcan.moviefragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.eylulcan.moviefragment.databinding.FragmentMovieDetailBinding
import com.google.firebase.auth.FirebaseAuth

class MovieDetailFragment : Fragment() {

    companion object {
        private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185"
    }

    private lateinit var fragmentBinding: FragmentMovieDetailBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedResultMovieDataArgument = arguments?.get(getString(R.string.movie)) as ResultMovie
        fragmentBinding = FragmentMovieDetailBinding.bind(view)
        Glide.with(this).load(setImageUrl(selectedResultMovieDataArgument.posterPath))
            .into(fragmentBinding.movieImage)
        fragmentBinding.movieName.text = selectedResultMovieDataArgument.title
        fragmentBinding.movieInfo.text = selectedResultMovieDataArgument.overview
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout ) {
            auth.signOut()
            Toast.makeText(context, R.string.logged_out_movie_detail, Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_movieDetailFragment_to_loginFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setImageUrl(poster_path: String?): String {
        return BASE_IMAGE_URL.plus(poster_path)
    }
}