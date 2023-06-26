package com.iclab.abclogger.data.logging

import androidx.room.Entity

@Entity(tableName="notificationEvents")
data class NotificationEvent(
    val timestamp: Long,


)