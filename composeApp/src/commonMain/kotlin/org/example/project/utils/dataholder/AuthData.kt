package org.example.project.utils.dataholder

import org.example.project.domain.models.auth.login.UserDataResponse
import org.example.project.domain.models.auth.register.TokenResponse

object AuthData {
    var userName = ""
    var userId = ""
    var tokens: TokenResponse? = null
    var UserData: UserDataResponse? = null
    fun setAuthData(authResponse: UserDataResponse?, token: TokenResponse?) {
        UserData = authResponse
        tokens = token
        userName = authResponse?.name ?: ""
        userId = authResponse?.id ?: ""
    }
    fun clearAuthData(){
        userName=""
        tokens = null
        UserData= null
        userId =""
    }
}
