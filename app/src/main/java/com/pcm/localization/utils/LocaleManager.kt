package com.pcm.localization.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.LocaleList
import androidx.annotation.RequiresApi
import java.util.*

class LocaleManager {
    private val preferenceName = "sp_localization"
    private var sharedPreferences: SharedPreferences? = null

    companion object {
        private const val LANGUAGE = "com.pcm.localization.LANGUAGE"
        private const val DEFAULT_LANGUAGE = "en"
        private var instance: LocaleManager? = null
        fun getInstance(): LocaleManager {
            if (instance == null) {
                instance =
                    LocaleManager()
            }
            return instance as LocaleManager
        }
    }

    fun setLocale(c: Context): Context {
        sharedPreferences = c.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        return updateResources(c, language)
    }

    var language: String
        get() = sharedPreferences?.getString(LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
        set(languageCode) {
            sharedPreferences?.edit()?.putString(LANGUAGE, languageCode)?.apply()
        }

    private fun updateResources(
        context: Context,
        language: String
    ): Context {
        var contextNew = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = contextNew.resources
        val config =
            Configuration(res.configuration)

        when {
            isAtLeastVersion(VERSION_CODES.N) -> {
                setLocaleForApi24(config, locale)
                contextNew = contextNew.createConfigurationContext(config)
            }
            isAtLeastVersion(VERSION_CODES.JELLY_BEAN_MR1) -> {
                config.setLocale(locale)
                contextNew = contextNew.createConfigurationContext(config)
            }
            else -> {
                config.locale = locale
                res.updateConfiguration(config, res.displayMetrics)
            }
        }
        return contextNew
    }

    @RequiresApi(api = VERSION_CODES.N)
    private fun setLocaleForApi24(
        config: Configuration,
        target: Locale
    ) {
        val set: MutableSet<Locale> =
            LinkedHashSet()
        // bring the target locale to the front of the list
        set.add(target)
        val all = LocaleList.getDefault()
        for (i in 0 until all.size()) {
            // append other locales supported by the user
            set.add(all[i])
        }
        val locales = set.toTypedArray()
        config.setLocales(LocaleList(*locales))
    }

    fun getLocale(res: Resources): Locale {
        val config = res.configuration
        return if (isAtLeastVersion(VERSION_CODES.N)) config.locales[0] else config.locale
    }

    private fun isAtLeastVersion(version: Int): Boolean {
        return Build.VERSION.SDK_INT >= version
    }
}