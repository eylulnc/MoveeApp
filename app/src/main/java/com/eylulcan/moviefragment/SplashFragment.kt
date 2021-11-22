package com.eylulcan.moviefragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.eylulcan.moviefragment.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashFragment : Fragment() {

    private val auth: FirebaseAuth = Firebase.auth
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        val currentUser = auth.currentUser
        currentUser?.let {
            navigateToMovieList()
        } ?: run {
            navigateToLogin()
        }
    }

    private fun navigateToMovieList(){
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController()
                .navigate(
                    R.id.action_splashFragment_to_movieListFragment, null,
                    NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build()
                )
        }, 1500)
    }

    private fun navigateToLogin(){
        val extras =
            FragmentNavigatorExtras(binding.splashFragmentLogo to getString(R.string.login_transition))
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController()
                .navigate(
                    R.id.action_splashFragment_to_loginFragment, null,
                    NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build(), extras
                )
        }, 1500)
    }
}