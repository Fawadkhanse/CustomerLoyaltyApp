package org.example.project.utils.dataholder.storage


/**
 * Created by Ahmad Ijaz Bhatti on 23,Jan,2024
 * AIS company,
 * Islamabad, Pakistan.
 *
 * Converted to KMP by [Your Name]
 */

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Common DataStore Preferences for KMP
 * Works on both Android and iOS
 */
class DataStorePreferences(val dataStore: DataStore<Preferences>) {

    // Flow-based getters for reactive data
    fun getIntFlow(key: String): Flow<Int> = dataStore.data.map { preferences ->
        preferences[intPreferencesKey(key)] ?: 0
    }

    fun getLongFlow(key: String): Flow<Long> = dataStore.data.map { preferences ->
        preferences[longPreferencesKey(key)] ?: 0L
    }

    fun getFloatFlow(key: String): Flow<Float> = dataStore.data.map { preferences ->
        preferences[floatPreferencesKey(key)] ?: 0f
    }

    fun getDoubleFlow(key: String): Flow<Double> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(key)]?.toDoubleOrNull() ?: 0.0
    }

    fun getStringFlow(key: String): Flow<String> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(key)] ?: ""
    }

    fun getBooleanFlow(key: String): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey(key)] ?: false
    }

    // Suspend functions for one-time reads
    suspend fun getInt(key: String): Int {
        return dataStore.data.first()[intPreferencesKey(key)] ?: 0
    }

    suspend fun getLong(key: String): Long {
        return dataStore.data.first()[longPreferencesKey(key)] ?: 0L
    }

    suspend fun getFloat(key: String): Float {
        return dataStore.data.first()[floatPreferencesKey(key)] ?: 0f
    }

    suspend fun getDouble(key: String): Double {
        val value = dataStore.data.first()[stringPreferencesKey(key)] ?: "0.0"
        return value.toDoubleOrNull() ?: 0.0
    }

    suspend fun getString(key: String): String {
        return dataStore.data.first()[stringPreferencesKey(key)] ?: ""
    }

    suspend fun getBoolean(key: String): Boolean {
        return dataStore.data.first()[booleanPreferencesKey(key)] ?: false
    }

    // List getters
    suspend fun getListInt(key: String): List<Int> {
        val value = dataStore.data.first()[stringPreferencesKey(key)] ?: ""
        return if (value.isEmpty()) emptyList()
        else value.split(DELIMITER).mapNotNull { it.toIntOrNull() }
    }

    suspend fun getListLong(key: String): List<Long> {
        val value = dataStore.data.first()[stringPreferencesKey(key)] ?: ""
        return if (value.isEmpty()) emptyList()
        else value.split(DELIMITER).mapNotNull { it.toLongOrNull() }
    }

    suspend fun getListDouble(key: String): List<Double> {
        val value = dataStore.data.first()[stringPreferencesKey(key)] ?: ""
        return if (value.isEmpty()) emptyList()
        else value.split(DELIMITER).mapNotNull { it.toDoubleOrNull() }
    }

    suspend fun getListString(key: String): List<String> {
        val value = dataStore.data.first()[stringPreferencesKey(key)] ?: ""
        return if (value.isEmpty()) emptyList()
        else value.split(DELIMITER)
    }

    suspend fun getListBoolean(key: String): List<Boolean> {
        val value = dataStore.data.first()[stringPreferencesKey(key)] ?: ""
        return if (value.isEmpty()) emptyList()
        else value.split(DELIMITER).map { it == "true" }
    }

    // Object getter using kotlinx.serialization
    suspend inline fun <reified T> getObject(key: String): T? {
        val jsonString = dataStore.data.first()[stringPreferencesKey(key)] ?: return null
        return try {
            Json.decodeFromString<T>(jsonString)
        } catch (e: Exception) {
            null
        }
    }

    suspend inline fun <reified T> getListObject(key: String): List<T> {
        val jsonArrayString = dataStore.data.first()[stringPreferencesKey(key)] ?: return emptyList()
        return try {
            Json.decodeFromString<List<T>>(jsonArrayString)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Setters
    suspend fun putInt(key: String, value: Int) {
        checkForNullKey(key)
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    suspend fun putLong(key: String, value: Long) {
        checkForNullKey(key)
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(key)] = value
        }
    }

    suspend fun putFloat(key: String, value: Float) {
        checkForNullKey(key)
        dataStore.edit { preferences ->
            preferences[floatPreferencesKey(key)] = value
        }
    }

    suspend fun putDouble(key: String, value: Double) {
        checkForNullKey(key)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value.toString()
        }
    }

    suspend fun putString(key: String, value: String) {
        checkForNullKey(key)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    suspend fun putBoolean(key: String, value: Boolean) {
        checkForNullKey(key)
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    // List setters
    suspend fun putListInt(key: String, list: List<Int>) {
        checkForNullKey(key)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = list.joinToString(DELIMITER)
        }
    }

    suspend fun putListLong(key: String, list: List<Long>) {
        checkForNullKey(key)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = list.joinToString(DELIMITER)
        }
    }

    suspend fun putListDouble(key: String, list: List<Double>) {
        checkForNullKey(key)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = list.joinToString(DELIMITER)
        }
    }

    suspend fun putListString(key: String, list: List<String>) {
        checkForNullKey(key)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = list.joinToString(DELIMITER)
        }
    }

    suspend fun putListBoolean(key: String, list: List<Boolean>) {
        checkForNullKey(key)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = list.joinToString(DELIMITER) { it.toString() }
        }
    }

    // Object setter using kotlinx.serialization
    suspend inline fun <reified T> putObject(key: String, obj: T) {
        checkForNullKey(key)
        val jsonString = Json.encodeToString(obj)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = jsonString
        }
    }

    suspend inline fun <reified T> putListObject(key: String, list: List<T>) {
        checkForNullKey(key)
        val jsonString = Json.encodeToString(list)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = jsonString
        }
    }

    // Remove and Clear
    suspend fun remove(key: String) {
        dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
            preferences.remove(intPreferencesKey(key))
            preferences.remove(longPreferencesKey(key))
            preferences.remove(floatPreferencesKey(key))
            preferences.remove(booleanPreferencesKey(key))
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // Dialog helpers (from original code)
    suspend fun resetDialogShown() {
        dataStore.edit { preferences ->
            preferences[IS_DIALOG_SHOWN] = false
        }
    }

    suspend fun isDialogShown(): Boolean {
        return dataStore.data.first()[IS_DIALOG_SHOWN] ?: false
    }

    suspend fun setDialogShown() {
        dataStore.edit { preferences ->
            preferences[IS_DIALOG_SHOWN] = true
        }
    }

    fun checkForNullKey(key: String?) {
        if (key == null) {
            throw NullPointerException("Key cannot be null")
        }
    }

    companion object {
        private const val DELIMITER = "‚‗‚"
        private val IS_DIALOG_SHOWN = booleanPreferencesKey("is_dialog_shown")
    }
}