package com.iclab.abclogger.data.container

import com.iclab.abclogger.data.auth.AuthRepository

interface AppContainer {
    val authRepository: AuthRepository
//    val uploadRepository: UploadRepository
//    val loggingRepository: LoggingRepository
//    val databaseRepository: DatabaseRepository
}