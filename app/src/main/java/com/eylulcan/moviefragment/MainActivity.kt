package com.eylulcan.moviefragment

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.eylulcan.moviefragment.databinding.ActivityMainBinding
import com.eylulcan.moviefragment.ui.MovieFragmentFactory
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

private const val CHANNEL_ID = "com.eylulcan.moviefragment"
private const val NOTIFICATION_ID = 101

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: MovieFragmentFactory
    @Inject
    lateinit var auth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    private var firstTimeOpened: Boolean? = null
    private lateinit var navGraph: NavGraph
    private lateinit var navController: NavController
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashScreen)
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = this.getSharedPreferences(
            getString(R.string.app_package_name),
            Context.MODE_PRIVATE
        )
        createNotificationChannel()
        scheduleNotification()
        setupUI()
    }

    private fun setupUI() {
        val navHostFragment =
            binding.fragmentContainerView.getFragment<Fragment>() as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        navGraph = graphInflater.inflate(R.navigation.navigation_graph)
        navController = navHostFragment.navController
        val currentUser = auth.currentUser
        currentUser?.let {
            navGraph.setStartDestination(R.id.dashboardFragment)
        } ?: run {
            firstTimeOpened = sharedPreferences.getBoolean(getString(R.string.isFirst), true)
            if (firstTimeOpened == true) {
                sharedPreferences.edit().putBoolean(getString(R.string.isFirst), false).apply()
                navGraph.setStartDestination(R.id.onBoardViewPagerFragment)
            } else {
                navGraph.setStartDestination(R.id.loginFragment)
            }
            navController.graph = navGraph
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                CHANNEL_ID, getString(R.string.title),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = getString(R.string.description)

                notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    private fun getTime(): Long {
        val now = Calendar.getInstance()
        val time = now.clone() as Calendar
        time.add(Calendar.MINUTE, 2)
        return time.timeInMillis
    }

}