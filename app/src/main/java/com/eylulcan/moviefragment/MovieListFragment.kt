package com.eylulcan.moviefragment

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylulcan.moviefragment.databinding.FragmentMovieListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MovieListFragment : Fragment(), MovieListener {

    private val movieList: ArrayList<Movie> = arrayListOf()
    private lateinit var fragmentBinding: FragmentMovieListBinding
    private lateinit var movieAdapter1:MovieAdapter
    private lateinit var movieAdapter2:MovieAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentMovieListBinding.bind(view)
        fragmentBinding.movieListRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        fragmentBinding.movieListRecyclerView2.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        movieListGenerator()
        movieAdapter1 = MovieAdapter(movieList, this)
        movieAdapter2 = MovieAdapter(arrayListOf(movieList.first(),movieList.last()), this)
        fragmentBinding.movieListRecyclerView.adapter = movieAdapter1
        fragmentBinding.movieListRecyclerView2.adapter = movieAdapter2
        postponeEnterTransition()
        fragmentBinding.movieListRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun movieListGenerator() {
       movieList.clear()
        val avengers = Movie(
            getString(R.string.avengers),
            R.drawable.avengers,
            getString(R.string.avengers_info)
        )
        val interstellar = Movie(
            getString(R.string.interstellar),
            R.drawable.interstellar,
            getString(R.string.interstellar_info)
        )
        val harrypotter = Movie(
            getString(R.string.harry_potter),
            R.drawable.harrypotter,
            getString(R.string.harry_potter_info)
        )
        val titanic = Movie(
            getString(R.string.titanic),
            R.drawable.titanic,
            getString(R.string.titanic_info)
        )
        movieList.add(avengers)
        movieList.add(interstellar)
        movieList.add(harrypotter)
        movieList.add(titanic)
    }

    override fun onMovieClicked(position: Int, image: ImageView) {
        val movie: Movie = movieList[position]
        val movieDataBundle = bundleOf((getString(R.string.movie)) to movie)
        val extras = FragmentNavigatorExtras(image to getString(R.string.list_to_detail_transition))
        image.transitionName = image.id.toString()
        //movieAdapter.updateMovieList(movieList)
        findNavController().navigate(R.id.action_movieListFragment_to_movieDetailFragment, movieDataBundle,null, extras)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        auth.signOut()
        Toast.makeText(context, "Logged Out", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_movieListFragment_to_loginFragment)
        return super.onOptionsItemSelected(item)
    }

}