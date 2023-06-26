package com.iclab.abclogger.data.auth

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthRepositoryImpl:AuthRepository {
    private val auth by lazy{
        FirebaseAuth.getInstance()
    }
    private val clientID: String = "834886098208-mnta7gn29ttd24nrimusr4ufe37k9hu2.apps.googleusercontent.com"
    override fun checkLogin():String? {
        return auth.currentUser?.email
    }

    override fun getGoogleClientForLogin(context: Context):GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientID)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, googleSignInOption)
    }

    override fun loginWithFirebase(
        account: GoogleSignInAccount?,
        onComplete: (Task<AuthResult>) ->  Unit
        ){
        account?.let{
            val credential= GoogleAuthProvider.getCredential(it.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener {
                onComplete(it)
            }
        }


    }

}