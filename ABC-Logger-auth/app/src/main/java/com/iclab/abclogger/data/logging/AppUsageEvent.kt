package com.iclab.abclogger.data.logging

import androidx.room.Entity


@Entity(tableName="appUsageEvents")
data class AppUsageEvent(
    val queriedAt: Long,
    val timestamp: Long,
    val packageName: String,
    val eventType: Int,
    val className: String?
)
