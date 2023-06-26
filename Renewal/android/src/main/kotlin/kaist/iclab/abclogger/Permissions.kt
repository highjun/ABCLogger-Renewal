package kaist.iclab.abclogger

import android.app.AppOpsManager
import android.content.Context
import android.os.Build

fun checkPermission(context: Context):Boolean{
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