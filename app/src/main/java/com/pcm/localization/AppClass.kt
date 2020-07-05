package com.pcm.localization

import android.app.Application

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

}