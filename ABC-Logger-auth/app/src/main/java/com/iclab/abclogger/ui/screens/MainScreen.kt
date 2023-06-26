package com.iclab.abclogger.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import android.app.usage.UsageStatsManager

@Composable
fun MainScreen(email: String){
    Column {
        Text(text = email)
    }
}