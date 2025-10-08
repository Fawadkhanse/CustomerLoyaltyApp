package org.example.project.utils.dataholder


object TokenManager {
    private var accessToken: String? = null

    fun setAccessToken(token: String?) {
        accessToken = token
    }

    fun getAccessToken(): String? = accessToken
}
