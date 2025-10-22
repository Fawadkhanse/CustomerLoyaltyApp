package org.example.project.presentation.ui.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * Android-specific DataStore implementation
 * Place this file in: androidMain/kotlin/com/bi/digitalbanking/data/preferences/
 */

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

private lateinit var appContext: Context

/**
 * Initialize Android DataStore with application context
 * Call this from your Application class or MainActivity
 */
fun initDataStore(context: Context) {
    appContext = context.applicationContext
}

actual fun createDataStore(): DataStore<Preferences> {
    if (!::appContext.isInitialized) {
        throw IllegalStateException(
            "DataStore not initialized. Call initDataStore(context) in your Application class."
        )
    }
    return appContext.dataStore
}
