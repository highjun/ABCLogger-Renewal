package kaist.iclab.abclogger.data.app

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build


fun getApplication(packageManager: PackageManager, packageId: String):App{
    val packageInfo = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        packageManager.getPackageInfo(packageId,
            PackageManager.PackageInfoFlags.of(PackageManager.GET_META_DATA.toLong()))
    }else{
        @Suppress("DEPRECATION") packageManager.getPackageInfo(packageId, PackageManager.GET_META_DATA)
    }
    val applicationInfo = packageInfo.applicationInfo

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
        )
    }
}