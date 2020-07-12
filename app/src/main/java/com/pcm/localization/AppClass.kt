package com.pcm.localization

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.pcm.localization.utils.LocaleManager

class AppClass : Application() {

    companion object {
        private lateinit var instance: AppClass

        fun getInstance(): AppClass {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleManager.getInstance().setLocale(base!!))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.getInstance().setLocale(this)
    }

}