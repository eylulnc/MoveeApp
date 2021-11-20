package com.eylulcan.moviefragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eylulcan.moviefragment.Movie
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentMovieDetailBinding
import com.google.firebase.auth.FirebaseAuth

class MovieDetailFragment : Fragment() {
    private lateinit var fragmentBinding: FragmentMovieDetailBinding
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedMovieDataArgument = arguments?.get(getString(R.string.movie)) as Movie
        fragmentBinding = FragmentMovieDetailBinding.bind(view)
        fragmentBinding.movieImage.setImageResource(selectedMovieDataArgument.image)
        fragmentBinding.movieName.text = selectedMovieDataArgument.name
        fragmentBinding.movieInfo.text = selectedMovieDataArgument.info
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        auth.signOut()
        Toast.makeText(context, "Logged Out", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_movieDetailFragment_to_loginFragment)
        return super.onOptionsItemSelected(item)
    }
}