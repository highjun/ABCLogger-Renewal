package kaist.iclab.abclogger

import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings

fun isPermissionEnabled(context: Context, permission: String): Boolean {
    when (permission) {
        Manifest.permission.PACKAGE_USAGE_STATS -> {
            val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                appOpsManager.unsafeCheckOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), context.packageName
                )
            } else {
                appOpsManager.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), context.packageName
                )
            }

            return mode == AppOpsManager.MODE_ALLOWED
        }

        Manifest.permission.QUERY_ALL_PACKAGES -> {
            val packageManager: PackageManager = context.packageManager
            val packageName: String = context.packageName
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                return packageManager.checkPermission(
                    Manifest.permission.QUERY_ALL_PACKAGES,
                    packageName
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                // Not exist at all
                return true
            }
        }

        else -> {
            return true
        }
    }
}

fun enablePermission(context: Context, permission: String) {
    when (permission) {
        Manifest.permission.PACKAGE_USAGE_STATS -> {
            context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }

        Manifest.permission.QUERY_ALL_PACKAGES -> {
            return
        }

        else -> {
            return
        }
    }
}