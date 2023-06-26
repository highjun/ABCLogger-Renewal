package com.iclab.abclogger.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.iclab.abclogger.ABCLoggerApplication
//
object ABCViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ABCViewModel(
                ABCLoggerApplication().container.appUsageRepository,
                ABCLoggerApplication().container.alarmManagerRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 * TODO:이거 없으면 Application 객체의 Container가 Initialize 안되었다는 Error가 등장함...
 */
fun CreationExtras.ABCLoggerApplication(): ABCLoggerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ABCLoggerApplication)