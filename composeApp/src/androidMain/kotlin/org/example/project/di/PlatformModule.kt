package org.example.project.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single<Settings> {
        SharedPreferencesSettings(
            delegate = androidContext().getSharedPreferences(
                "app_settings",
                Context.MODE_PRIVATE
            )
        )
    }
}
