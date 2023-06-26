package com.iclab.abclogger.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="appUsageEvents")
data class AppUsageEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long,
    val packageID: String,
    val className: String?,
    val eventType: Int,
//    val category: String,
){
    override fun toString(): String {
        return "timestamp: " + timestamp.toString() +" " + packageID
    }
}
