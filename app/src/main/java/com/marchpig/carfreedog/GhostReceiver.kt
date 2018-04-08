package com.marchpig.carfreedog

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

class GhostReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_POWER_CONNECTED -> {
                try {
                    context.packageManager.getPackageInfo(
                            "com.samsung.android.sm",
                            PackageManager.GET_META_DATA
                    )
                    val intent = Intent(context, GhostActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (ex: PackageManager.NameNotFoundException) {
                }
            }
        }
    }
}
