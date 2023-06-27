package kaist.iclab.abclogger.data.app

import android.app.usage.UsageEvents
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import kaist.iclab.abclogger.data.app.entities.App
import kaist.iclab.abclogger.data.app.entities.AppUsageEvent
import java.io.ByteArrayOutputStream


fun getApplication(packageManager: PackageManager, packageId: String):App{
    val packageInfo = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        packageManager.getPackageInfo(packageId,
            PackageManager.PackageInfoFlags.of(PackageManager.GET_META_DATA.toLong()))
    }else{
        @Suppress("DEPRECATION") packageManager.getPackageInfo(packageId, PackageManager.GET_META_DATA)
    }
    val applicationInfo = packageInfo.applicationInfo

    val applicationIcon = packageManager.getApplicationIcon(applicationInfo).toByteArray()



    val isSystemApp = applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    val isUpdatedSystemApp = applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0

    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        App(
            packageId = packageId,
            name = applicationInfo.name,
            category = applicationInfo.category,
            isSystemApp = isSystemApp or isUpdatedSystemApp,
            deletedTimestamp = -1L,
            lastUpdatedTimestamp = packageInfo.lastUpdateTime,
            installedTimestamp = packageInfo.firstInstallTime,
            icon = applicationIcon
        )
    }else{
        App(
            packageId = packageId,
            name = applicationInfo.name,
            category = -1, //Oreo can not get category
            isSystemApp = isSystemApp or isUpdatedSystemApp,
            deletedTimestamp = -1L,
            lastUpdatedTimestamp = packageInfo.lastUpdateTime,
            installedTimestamp = packageInfo.firstInstallTime,
            icon = applicationIcon
        )
    }
}

fun UsageEvents.Event.toAppUsageEvent(queriedTimestamp: Long): AppUsageEvent {
    return AppUsageEvent(
        queriedTimestamp,
        this.timeStamp,
        this.packageName,
        this.eventType,
        this.className
    )
}

fun Int.mapAppUsageEventTypeToString():String {
    return when(this){
        UsageEvents.Event.ACTIVITY_RESUMED -> "ACTIVITY_RESUMED"
        UsageEvents.Event.ACTIVITY_STOPPED -> "ACTIVITY_STOPPED"
        UsageEvents.Event.ACTIVITY_PAUSED -> "ACTIVITY_PAUSED"
        UsageEvents.Event.FOREGROUND_SERVICE_STOP -> "FOREGROUND_SERVICE_STOP"
        UsageEvents.Event.FOREGROUND_SERVICE_START -> "FOREGROUND_SERVICE_START"
        UsageEvents.Event.USER_INTERACTION -> "USER_INTERACTION"
        UsageEvents.Event.STANDBY_BUCKET_CHANGED -> "STANDBY_BUCKET_CHANGED"
        UsageEvents.Event.SHORTCUT_INVOCATION -> "SHORTCUT_INVOCATION"
        UsageEvents.Event.SCREEN_NON_INTERACTIVE -> "SCREEN_NON_INTERACTIVE"
        UsageEvents.Event.SCREEN_INTERACTIVE -> "SCREEN_INTERACTIVE"
        UsageEvents.Event.KEYGUARD_SHOWN -> "KEYGUARD_SHOWN"
        UsageEvents.Event.KEYGUARD_HIDDEN -> "KEYGUARD_HIDDEN"
        UsageEvents.Event.DEVICE_SHUTDOWN -> "DEVICE_SHUTDOWN"
        UsageEvents.Event.DEVICE_STARTUP -> "DEVICE_STARTUP"
        UsageEvents.Event.CONFIGURATION_CHANGE -> "CONFIGURATION_CHANGE"
        UsageEvents.Event.NONE -> "NONE"
        else -> "UNKNOWN"
    }
}

fun Int.mapAppCategoryToString(): String{
    return when(this){
        ApplicationInfo.CATEGORY_ACCESSIBILITY -> "ACCESSIBILITY"
        ApplicationInfo.CATEGORY_MAPS -> "MAPS"
        ApplicationInfo.CATEGORY_AUDIO -> "AUDIO"
        ApplicationInfo.CATEGORY_GAME -> "GAME"
        ApplicationInfo.CATEGORY_IMAGE -> "IMAGE"
        ApplicationInfo.CATEGORY_NEWS -> "NEWS"
        ApplicationInfo.CATEGORY_PRODUCTIVITY -> "PRODUCTIVITY"
        ApplicationInfo.CATEGORY_SOCIAL -> "SOCIAL"
        ApplicationInfo.CATEGORY_VIDEO -> "VIDEO"
        ApplicationInfo.CATEGORY_UNDEFINED -> "UNDEFINED"
        else -> "UNKNOWN"
    }
}

fun AdaptiveIconDrawable.toByteArray():ByteArray{
    val width = this.intrinsicWidth
    val height = this.intrinsicHeight

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    this.setBounds(0, 0, canvas.width, canvas.height)
    this.draw(canvas)

    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

    return outputStream.toByteArray()
}

fun Drawable.toByteArray():ByteArray{
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        if(this is AdaptiveIconDrawable){
            val width = this.intrinsicWidth
            val height = this.intrinsicHeight

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            this.setBounds(0, 0, canvas.width, canvas.height)
            this.draw(canvas)

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            return outputStream.toByteArray()
        }else{
            val bitmap = (this as BitmapDrawable).bitmap
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return outputStream.toByteArray()
        }
    }else if(this is BitmapDrawable){
        val bitmap = this.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }else{
        Log.e(javaClass.name, this.javaClass.name)
        val bitmap = (this as BitmapDrawable).bitmap
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}

fun ByteArray.toDrawable(): Drawable {
    val bitmap = BitmapFactory.decodeByteArray(this, 0, this.size)
    return BitmapDrawable(null, bitmap)
}