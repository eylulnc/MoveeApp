package com.eylulcan.moviefragment.ui.moviedetail.popup

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.databinding.MovieDetailPopupRatingScreenBinding
import dagger.hilt.android.AndroidEntryPoint

private const val MUL_TWO = 2
private const val CHANNEL_ID = "com.eylulcan.moviefragment"
private const val NOTIFICATION_ID = 101

@AndroidEntryPoint
class CustomPopUpDialogFragment : DialogFragment() {

    private var movieID: Int = -1
    private var sessionID: String = ""
    private var _binding: MovieDetailPopupRatingScreenBinding? = null
    private val binding get() = _binding!!
    private val popUpViewModel: PopUpViewModel by viewModels()
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailPopupRatingScreenBinding.inflate(inflater, container, false)
        observeViewModel()
        val sharedPref =
            activity?.getSharedPreferences(
                getString(R.string.app_package_name),
                Context.MODE_PRIVATE
            )
        sessionID = sharedPref?.getString(getString(R.string.sessionId), null).toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.rateButton.setOnClickListener {
            val rate = binding.popUpRatingBar.rating.times(MUL_TWO).toDouble()
            popUpViewModel.postMovieRating(movieID = movieID, rate = rate, sessionId = sessionID)
            dismiss()
            Toast.makeText(context, getString(R.string.ratingSend), Toast.LENGTH_LONG).show()
            buildLocalNotification()
        }
    }

    fun setMovieID(id: Int) {
        this.movieID = id
    }

    private fun observeViewModel() {
        popUpViewModel.responseRating.observe(viewLifecycleOwner) {

        }
    }

    private fun buildLocalNotification() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(CHANNEL_ID,getString(R.string.title),
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = getString(R.string.description)
            }
            notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(requireActivity(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_videocam)
            .setContentTitle(getString(R.string.fyi))
            .setContentText(getString(R.string.ratingSend))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(requireActivity())) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

}