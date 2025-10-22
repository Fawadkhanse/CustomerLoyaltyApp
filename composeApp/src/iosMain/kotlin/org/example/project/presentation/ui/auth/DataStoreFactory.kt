package org.example.project.presentation.ui.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

/**
 * iOS-specific DataStore implementation
 * Place this file in: iosMain/kotlin/com/bi/digitalbanking/data/preferences/
 */

@OptIn(ExperimentalForeignApi::class)
private fun getDataStoreFile(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory).path + "/app_preferences.preferences_pb"
}

actual fun createDataStore(): DataStore<Preferences> {
    return createDataStoreWithPath(
        producePath = { getDataStoreFile() }
    )
}

private fun createDataStoreWithPath(
    producePath: () -> String
): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    produceFile = { producePath() }
)
