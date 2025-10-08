package org.example.project.utils.dataholder

import org.example.project.domain.models.auth.login.UserDataResponse
import org.example.project.domain.models.auth.register.TokenResponse

object AuthData {
    var userName = ""
    var userId = ""
    var tokens: TokenResponse? = null
    var UserData: UserDataResponse? = null
    var userRole = ""
    fun setAuthData(authResponse: UserDataResponse?, token: TokenResponse?) {
        UserData = authResponse
        tokens = token
        userName = authResponse?.name ?: ""
        userId = authResponse?.id ?: ""
        userRole = authResponse?.role ?: "customer"
    }
    fun clearAuthData(){
        userName = ""
        tokens = null
        UserData = null
        userId = ""
        userRole = "" //
    }
    fun isMerchant(): Boolean = userRole.equals("merchant", ignoreCase = true)
    fun isCustomer(): Boolean = userRole.equals("customer", ignoreCase = true)
}
