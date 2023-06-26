package com.iclab.abclogger.ui

import android.content.Context
import android.provider.Contacts.Intents.UI
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.iclab.abclogger.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ABCViewModel(val authRepository: AuthRepository):ViewModel() {

    private val TAG = "ABCViewModel"

    private val _uiState = MutableStateFlow(UIState())
    val uiState:StateFlow<UIState>
        get() = _uiState.asStateFlow()

    init {
        authRepository.checkLogin()?.let{
            _uiState.value = UIState(
                isLoggedIn = true,
                email = it
            )
        }
    }

    fun login(account: GoogleSignInAccount, onLoginCompleted: () -> Unit) {
        authRepository.loginWithFirebase(account){
            if(it.isSuccessful){
                _uiState.value = UIState(
                    isLoggedIn = true,
                    email = account.email?: "EMAIL UNKNOWN"
                )
                onLoginCompleted()
            }else{
                Log.e(TAG, it.result.toString())
            }
        }
    }

}