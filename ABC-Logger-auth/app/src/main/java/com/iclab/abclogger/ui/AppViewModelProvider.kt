package com.iclab.abclogger.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.iclab.abclogger.ABCApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ABCViewModel(
                abcApplication().container.authRepository
            )
        }
    }
}


/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
//TODO: 이거 없으면 Application 객체의 Initialization이 안됨.
fun CreationExtras.abcApplication(): ABCApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ABCApplication)
