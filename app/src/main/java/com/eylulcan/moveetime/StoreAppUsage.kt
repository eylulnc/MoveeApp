package com.eylulcan.moveetime

import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Process
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val APP_USAGE = "App Usage"

@HiltWorker
class StoreAppUsage @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParams: WorkerParameters) : Worker(context,
    workerParams
){
    private var auth:FirebaseAuth = FirebaseAuth.getInstance()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun doWork(): Result {
        getAppUsage(context)
        return Result.success()
    }

    private fun getAppUsage(context:Context) {
        if (checkUsageStatsPermission(context)) {
            val cal = Calendar.getInstance()
            val offset: Int = cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)
            val sinceMidnight: Long = (cal.timeInMillis + offset) %
                    (24 * 60 * 60 * 1000)
            val endMillis = Date().time

            val usageStatsManager =
                context.getSystemService(AppCompatActivity.USAGE_STATS_SERVICE) as UsageStatsManager
            val lUsageStatsMap =
                usageStatsManager.queryAndAggregateUsageStats(sinceMidnight, endMillis)
            val totalTimeUsageInMillis = lUsageStatsMap[context.packageName]!!.totalTimeInForeground

            val totalMin = TimeUnit.MILLISECONDS.toMinutes(totalTimeUsageInMillis)

            val hours: Int = totalMin.div(60).toInt()
            val minutes: Int = totalMin.mod(60)
            val appUsageString = context.getString(R.string.duration, hours, minutes)
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss a", Locale.ENGLISH)
            val currentDate = sdf.format(Date())
            updateAppUsage(appUsageString,currentDate)

        } else {
            val accessIntent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            accessIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(accessIntent)
        }
    }

    private fun checkUsageStatsPermission(context:Context) : Boolean {
        val appOpsManager: AppOpsManager = context.getSystemService(AppCompatActivity.APP_OPS_SERVICE) as AppOpsManager
        val mode : Int = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.packageName)
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun updateAppUsage(appUsage: String, date : String) {

        val appUsageMap = hashMapOf<String, String>()
        appUsageMap[date] = appUsage

        val ref = auth.currentUser?.uid?.let {
            firestore.collection(APP_USAGE).document(it)
        }
        ref?.set(appUsageMap, SetOptions.merge())?.addOnSuccessListener {
            println("Success")
        }?.addOnFailureListener {
            println("Fail")
        }
    }
}