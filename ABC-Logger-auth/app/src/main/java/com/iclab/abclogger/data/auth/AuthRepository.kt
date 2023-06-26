package com.iclab.abclogger.data.auth

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthRepository {

    fun loginWithFirebase(
        account: GoogleSignInAccount?,
        onComplete: (Task<AuthResult>) ->  Unit
    )

    fun getGoogleClientForLogin(context: Context):GoogleSignInClient

    fun checkLogin():String?

}