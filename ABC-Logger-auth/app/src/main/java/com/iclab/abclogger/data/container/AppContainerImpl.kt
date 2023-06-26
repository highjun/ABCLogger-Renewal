package com.iclab.abclogger.data.container

import android.content.Context
import com.iclab.abclogger.data.auth.AuthRepositoryImpl

class AppContainerImpl(private val context: Context):AppContainer {
    override val authRepository = AuthRepositoryImpl()
    //    override val uploadRepository: UploadRepository
//        get() = TODO("Not yet implemented")
//    override val loggingRepository: LoggingRepository
//        get() = TODO("Not yet implemented")
//    override val databaseRepository: DatabaseRepository
//        get() = TODO("Not yet implemented")
}