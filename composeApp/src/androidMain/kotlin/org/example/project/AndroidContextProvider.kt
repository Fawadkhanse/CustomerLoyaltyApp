package org.example.project

import android.content.Context

object AndroidContextProvider {
    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun getContext(): Context {
        return appContext ?: throw IllegalStateException("Context not initialized")
    }
}