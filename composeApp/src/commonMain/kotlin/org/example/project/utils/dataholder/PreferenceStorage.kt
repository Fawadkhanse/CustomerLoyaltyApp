package org.example.project.utils.dataholder

interface PreferenceStorage {
    fun saveString(key: String, value: String)
    fun getString(key: String, defaultValue: String = ""): String

    fun saveInt(key: String, value: Int)
    fun getInt(key: String, defaultValue: Int = 0): Int

    fun saveBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean

    fun saveLong(key: String, value: Long)
    fun getLong(key: String, defaultValue: Long = 0L): Long

    fun saveFloat(key: String, value: Float)
    fun getFloat(key: String, defaultValue: Float = 0f): Float

    fun remove(key: String)
    fun clear()
    fun contains(key: String): Boolean
}
