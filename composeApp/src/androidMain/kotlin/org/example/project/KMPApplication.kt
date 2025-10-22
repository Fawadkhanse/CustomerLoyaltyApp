package org.example.project

import android.app.Application
import org.example.project.di.initKoin
import org.example.project.presentation.ui.auth.initDataStore

class KMPApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initDataStore(this)
    }
}