package kaist.iclab.abclogger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kaist.iclab.abclogger.data.app.App
import kaist.iclab.abclogger.data.app.AppDao
import kaist.iclab.abclogger.data.app.AppUsageEventDao
import kaist.iclab.abclogger.data.app.usage.AppUsageEvent


@Database(
    version = 2,
    entities = [
        AppUsageEvent::class,
        App::class
    ], exportSchema = false
)
abstract class ABCDatabase : RoomDatabase() {
    abstract fun appUsageEventDao(): AppUsageEventDao
    abstract fun appDao(): AppDao

//    companion object {
//        @Volatile
//        private var instance: ABCDatabase? = null
//
//        fun getDatabase(context: Context): ABCDatabase {
//            return instance ?: synchronized(this){
//                Room.databaseBuilder(context, ABCDatabase::class.java, "abc_database")
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also{ instance = it}
//            }
//        }
//
//    }

}