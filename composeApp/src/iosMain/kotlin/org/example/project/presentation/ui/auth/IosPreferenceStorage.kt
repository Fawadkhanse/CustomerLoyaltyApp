package org.example.project.presentation.ui.auth

import org.example.project.utils.dataholder.PreferenceStorage
import platform.Foundation.NSBundle
import platform.Foundation.NSUserDefaults

class IosPreferenceStorage : PreferenceStorage {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun saveString(key: String, value: String) {
        userDefaults.setObject(value, key)
        userDefaults.synchronize()
    }

    override fun getString(key: String, defaultValue: String): String {
        return userDefaults.stringForKey(key) ?: defaultValue
    }

    override fun saveInt(key: String, value: Int) {
        userDefaults.setInteger(value.toLong(), key)
        userDefaults.synchronize()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.integerForKey(key).toInt()
        } else {
            defaultValue
        }
    }

    override fun saveBoolean(key: String, value: Boolean) {
        userDefaults.setBool(value, key)
        userDefaults.synchronize()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.boolForKey(key)
        } else {
            defaultValue
        }
    }

    override fun saveLong(key: String, value: Long) {
        userDefaults.setInteger(value, key)
        userDefaults.synchronize()
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.integerForKey(key)
        } else {
            defaultValue
        }
    }

    override fun saveFloat(key: String, value: Float) {
        userDefaults.setFloat(value, key)
        userDefaults.synchronize()
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.floatForKey(key)
        } else {
            defaultValue
        }
    }

    override fun remove(key: String) {
        userDefaults.removeObjectForKey(key)
        userDefaults.synchronize()
    }

    override fun clear() {
        val domain = NSBundle.mainBundle.bundleIdentifier
        if (domain != null) {
            userDefaults.removePersistentDomainForName(domain)
        }
        userDefaults.synchronize()
    }

    override fun contains(key: String): Boolean {
        return userDefaults.objectForKey(key) != null
    }
}
