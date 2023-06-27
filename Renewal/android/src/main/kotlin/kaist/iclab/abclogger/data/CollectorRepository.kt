package kaist.iclab.abclogger.data

import android.app.AppOpsManager
import android.content.Context
import android.os.Build
import android.util.Log

class CollectorRepository(
    val collectors:List<Collector>,
    val context: Context
) {
    fun requiredPermissions(): Set<String> {
        return collectors.map{
            it.requiredPreferences
        }.flatten().toSet()
    }

    fun checkPermission():Boolean{
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

    fun start(){
//        val permissions = requiredPermissions()

        collectors.onEach {
            it.startLogging()
        }
        Log.d(javaClass.name, "Start Logging")
    }
    fun stop(){
        collectors.onEach {
            it.stopLogging()
        }
        Log.d(javaClass.name, "Stop Logging")
    }
}