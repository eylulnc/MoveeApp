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
import com.eylulcan.moviefragment.databinding.FragmentMovieDetailBinding
import com.eylulcan.moviefragment.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashFragment : Fragment() {

    private lateinit var auth: FirebaseAuth


  private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_splash, container, false)
        // or update fragment action - app:popUpTo="@id/splashFragment" app:popUpToInclusive="true"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        auth = Firebase.auth
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        val currentUser = auth.currentUser

        if(currentUser != null){
            Handler(Looper.myLooper()!!).postDelayed({findNavController()
                .navigate(R.id.action_splashFragment_to_movieListFragment, null,
                    NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build())
            },1500)
        } else {
            val extras = FragmentNavigatorExtras(binding.splashFragmentLogo to getString(R.string.login_transition))

                Handler(Looper.myLooper()!!).postDelayed({findNavController()
                    .navigate(R.id.action_splashFragment_to_loginFragment, null,
                        NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build(),extras )

            },1500)
        }

//binding.splashFragmentLogo.transitionName = binding.splashFragmentLogo.id.toString()
    }
}