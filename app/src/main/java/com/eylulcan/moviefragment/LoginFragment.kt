package com.eylulcan.moviefragment

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.eylulcan.moviefragment.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: FragmentLoginBinding
    private var email:String? = null
    private var password:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.signInButton.setOnClickListener { signIn() }
        binding.signUpButton.setOnClickListener { signUp() }

    }

    private fun signIn(){
        email = binding.userEmail.text.toString()
        password = binding.userPassword.text.toString()
        if(!email.equals("") && !password.equals("")) {
            auth.signInWithEmailAndPassword(email!!, password!!)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.action_loginFragment_to_movieListFragment,
                        null, NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build())
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(context,"No empty field allowed please fill all the areas" , Toast.LENGTH_LONG).show()
        }

    }

    private fun signUp(){
        email = binding.userEmail.text.toString()
        password = binding.userPassword.text.toString()
        if(!email.equals("") && !password.equals("")) {
            auth.createUserWithEmailAndPassword(email!!, password!!)
                .addOnSuccessListener {
                    val userMap = hashMapOf<String,Any>()
                    userMap["User Email"] = email!!
                    firestore.collection("Users").add(userMap).addOnSuccessListener {
                        findNavController().navigate(R.id.action_loginFragment_to_movieListFragment,
                            null, NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build())
                    }
                    Toast.makeText(context,"User Created You Automatically Logged In" , Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(context,"No empty field allowed please fill all the areas" , Toast.LENGTH_LONG).show()
        }
    }
}
