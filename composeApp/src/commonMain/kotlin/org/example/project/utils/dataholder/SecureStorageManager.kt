import org.example.project.domain.models.auth.login.UserDataResponse
import org.example.project.domain.models.auth.login.UserLoginResponse
import org.example.project.domain.models.auth.register.TokenResponse
import org.example.project.utils.dataholder.SettingsStorage

class SecureStorage(private val storage: SettingsStorage) {

    companion object {
        // Storage Keys
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
        const val KEY_USER_DATA = "user_data"
        const val KEY_LOGIN_RESPONSE = "login_response"
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_USER_ROLE = "user_role"
        const val KEY_USER_ID = "user_id"
        const val KEY_USER_NAME = "user_name"
        const val KEY_USER_EMAIL = "user_email"

        // Preferences
        const val KEY_THEME_MODE = "theme_mode"
        const val KEY_LANGUAGE = "language"
        const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
        const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }

    // ========== Auth Methods ==========

    fun saveLoginResponse(response: UserLoginResponse) {
        storage.saveObject(KEY_LOGIN_RESPONSE, response)

        response.token?.let { saveTokens(it) }

        response.user?.let { user ->
            storage.saveObject(KEY_USER_DATA, user)
            user.role?.let { storage.saveString(KEY_USER_ROLE, it) }
            user.id?.let { storage.saveString(KEY_USER_ID, it) }
            user.name?.let { storage.saveString(KEY_USER_NAME, it) }
            user.email?.let { storage.saveString(KEY_USER_EMAIL, it) }
        }

        storage.saveBoolean(KEY_IS_LOGGED_IN, true)
    }

    fun getLoginResponse(): UserLoginResponse? {
        return storage.getObject<UserLoginResponse>(KEY_LOGIN_RESPONSE)
    }

    fun saveTokens(tokens: TokenResponse) {
        tokens.access?.let { storage.saveString(KEY_ACCESS_TOKEN, it) }
        tokens.refresh?.let { storage.saveString(KEY_REFRESH_TOKEN, it) }
    }

    fun getAccessToken(): String? {
        val token = storage.getString(KEY_ACCESS_TOKEN)
        return if (token.isEmpty()) null else token
    }

    fun getRefreshToken(): String? {
        val token = storage.getString(KEY_REFRESH_TOKEN)
        return if (token.isEmpty()) null else token
    }

    fun getUserData(): UserDataResponse? {
        return storage.getObject<UserDataResponse>(KEY_USER_DATA)
    }

    fun isLoggedIn(): Boolean {
        return storage.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserRole(): String? {
        val role = storage.getString(KEY_USER_ROLE)
        return if (role.isEmpty()) null else role
    }

    fun getUserId(): String? {
        val id = storage.getString(KEY_USER_ID)
        return if (id.isEmpty()) null else id
    }

    fun getUserName(): String {
        return storage.getString(KEY_USER_NAME, "")
    }

    fun getUserEmail(): String {
        return storage.getString(KEY_USER_EMAIL, "")
    }

    fun clearAuthData() {
        storage.remove(KEY_LOGIN_RESPONSE)
        storage.remove(KEY_ACCESS_TOKEN)
        storage.remove(KEY_REFRESH_TOKEN)
        storage.remove(KEY_USER_DATA)
        storage.remove(KEY_USER_ROLE)
        storage.remove(KEY_USER_ID)
        storage.remove(KEY_USER_NAME)
        storage.remove(KEY_USER_EMAIL)
        storage.saveBoolean(KEY_IS_LOGGED_IN, false)
    }

    // ========== Preferences ==========

    fun setThemeMode(mode: String) {
        storage.saveString(KEY_THEME_MODE, mode)
    }

    fun getThemeMode(): String {
        return storage.getString(KEY_THEME_MODE, "system")
    }

    fun setLanguage(language: String) {
        storage.saveString(KEY_LANGUAGE, language)
    }

    fun getLanguage(): String {
        return storage.getString(KEY_LANGUAGE, "en")
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        storage.saveBoolean(KEY_NOTIFICATIONS_ENABLED, enabled)
    }

    fun isNotificationsEnabled(): Boolean {
        return storage.getBoolean(KEY_NOTIFICATIONS_ENABLED, true)
    }

    fun setOnboardingCompleted(completed: Boolean) {
        storage.saveBoolean(KEY_ONBOARDING_COMPLETED, completed)
    }

    fun isOnboardingCompleted(): Boolean {
        return storage.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    // ========== Clear All ==========

    fun clearAll() {
        storage.clear()
    }
}
