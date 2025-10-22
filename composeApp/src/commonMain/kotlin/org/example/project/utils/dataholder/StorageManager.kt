package org.example.project.utils.dataholder

import kotlinx.serialization.SerializationException
import org.example.project.utils.jsonInstance

/**
 * Generic storage manager using PreferenceStorage
 */
class StorageManager( val storage: PreferenceStorage) {

    /**
     * Save any serializable object as JSON string
     */
     inline fun <reified T> saveObject(key: String, value: T) {
        try {
            val jsonString = jsonInstance.encodeToString(value)
            storage.saveString(key, jsonString)
        } catch (e: SerializationException) {
            println("Error saving object: ${e.message}")
        }
    }

    /**
     * Retrieve serializable object from JSON string
     */
    inline fun <reified T> getObject(key: String, defaultValue: T? = null): T? {
        return try {
            val jsonString = storage.getString(key)
            if (jsonString.isEmpty()) {
                defaultValue
            } else {
                jsonInstance.decodeFromString<T>(jsonString)
            }
        } catch (e: Exception) {
            println("Error retrieving object: ${e.message}")
            defaultValue
        }
    }

    // Convenience methods
    fun saveString(key: String, value: String) = storage.saveString(key, value)
    fun getString(key: String, defaultValue: String = "") = storage.getString(key, defaultValue)

    fun saveInt(key: String, value: Int) = storage.saveInt(key, value)
    fun getInt(key: String, defaultValue: Int = 0) = storage.getInt(key, defaultValue)

    fun saveBoolean(key: String, value: Boolean) = storage.saveBoolean(key, value)
    fun getBoolean(key: String, defaultValue: Boolean = false) = storage.getBoolean(key, defaultValue)

    fun saveLong(key: String, value: Long) = storage.saveLong(key, value)
    fun getLong(key: String, defaultValue: Long = 0L) = storage.getLong(key, defaultValue)

    fun saveFloat(key: String, value: Float) = storage.saveFloat(key, value)
    fun getFloat(key: String, defaultValue: Float = 0f) = storage.getFloat(key, defaultValue)

    fun remove(key: String) = storage.remove(key)
    fun clear() = storage.clear()
    fun contains(key: String) = storage.contains(key)
}