package org.example.project.presentation.ui.auth


import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

actual class SettingsFactory {
    actual fun createSettings(): Settings {
        val userDefaults = NSUserDefaults.standardUserDefaults()
        return NSUserDefaultsSettings(userDefaults)
    }
}
