package org.example.project.utils.dataholder

import com.russhwolf.settings.Settings
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonArray

class SettingsStorage(private val settings: Settings) {

    // ========== Generic Methods ==========

    fun saveString(key: String, value: String) {
        settings[key] = value
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return settings.getString(key, defaultValue)
    }

    fun saveInt(key: String, value: Int) {
        settings[key] = value
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return settings.getInt(key, defaultValue)
    }

    fun saveBoolean(key: String, value: Boolean) {
        settings[key] = value
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return settings.getBoolean(key, defaultValue)
    }

    fun saveLong(key: String, value: Long) {
        settings[key] = value
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return settings.getLong(key, defaultValue)
    }

    fun saveFloat(key: String, value: Float) {
        settings[key] = value
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return settings.getFloat(key, defaultValue)
    }

    fun saveDouble(key: String, value: Double) {
        settings[key] = value
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return settings.getDouble(key, defaultValue)
    }

    // ========== Serializable Objects ==========

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> saveObject(key: String, value: T) {
        settings.encodeValue(JsonArray.Companion.serializer(), key, value)
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> getObject(key: String, defaultValue: T? = null): T? {
        return try {
            settings.decodeValue(JsonArray.Companion.serializer(), key, defaultValue)
        } catch (e: Exception) {
            println("Error getting object: ${e.message}")
            defaultValue
        }
    }

    // ========== Utility Methods ==========

    fun remove(key: String) {
        settings.remove(key)
    }

    fun clear() {
        settings.clear()
    }

    fun contains(key: String): Boolean {
        return settings.hasKey(key)
    }

    fun getAllKeys(): Set<String> {
        return settings.keys
    }
}