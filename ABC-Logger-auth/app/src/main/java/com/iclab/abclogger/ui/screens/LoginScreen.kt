package com.iclab.abclogger.ui.screens

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.iclab.abclogger.data.auth.AuthResultContract
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    getGoogleSignInClient: (context: Context) -> GoogleSignInClient,
    login: (account: GoogleSignInAccount) -> Unit){

    val context = LocalContext.current
    val authResultLauncher =
        rememberLauncherForActivityResult(
            contract = AuthResultContract(getGoogleSignInClient(context))
        ){
            it?.result?.let{
                login(it)
            }
        }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            authResultLauncher.launch(1)
        }) {
            Text(text = "Google Login!")
        }
    }
}