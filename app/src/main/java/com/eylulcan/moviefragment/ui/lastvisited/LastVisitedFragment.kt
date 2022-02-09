package com.eylulcan.moviefragment.ui.lastvisited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.FragmentLastVisitedBinding
import com.eylulcan.moviefragment.domain.daoEntity.MovieDao
import com.eylulcan.moviefragment.ui.ItemListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LastVisitedFragment @Inject constructor() : Fragment(), ItemListener {

    @Inject
    lateinit var lastVisitedAdapter: LastVisitedAdapter
    private lateinit var binding: FragmentLastVisitedBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var movieList: ArrayList<MovieDao> = arrayListOf()
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_last_visited, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLastVisitedBinding.bind(view)
        getDataFromFirestore()
    }

    override fun onItemClicked(id: Int) {
        val movieDataBundle = bundleOf((getString(R.string.movieId)) to id)
        findNavController().navigate(
            R.id.action_lastVisitedFragment_to_movieDetailFragment,
            movieDataBundle, null, null
        )
    }

    private fun getDataFromFirestore() {
        movieList.clear()
        var movie: MovieDao
        val docRef = auth.currentUser?.uid?.let {
            fireStore.collection(getString(R.string.lastVisited))
                .document(it)
        }
        docRef?.get()?.addOnSuccessListener { doc ->
            val data = doc.data
            data?.forEach { map ->
                val movieMap = map.value as HashMap<String, *>
                val id = movieMap[getString(R.string.id)].toString()
                val title = movieMap[getString(R.string.title)] as String
                val posterPath = movieMap[getString(R.string.posterPath)] as String
                movie = MovieDao(id = id, title = title, posterPath = posterPath)
                movieList.add(movie)
            }
            setUI()
        }
    }

    private fun setUI() {
        binding.lastVisitedRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = lastVisitedAdapter
            lastVisitedAdapter.movieList = movieList
            lastVisitedAdapter.setOnItemClickListener {
                onItemClicked(it)
            }
        }
    }

}


