package org.example.project.utils.dataholder

import org.example.project.domain.models.OutletResponse
import org.example.project.domain.models.auth.login.About
import org.example.project.domain.models.auth.login.Faq
import org.example.project.domain.models.auth.login.UserDataResponse
import org.example.project.domain.models.auth.login.UserLoginResponse
import org.example.project.domain.models.auth.register.TokenResponse
import org.example.project.presentation.ui.profile.FAQ

object AuthData {
    var userName = ""
    var userId = ""
    var tokens: TokenResponse? = null
    var UserData: UserDataResponse? = null
    var outletDetails: OutletResponse?=null
    var userRole = ""
    var userPoint= ""
    var about : About?=null
    var faqs :List<Faq>?=null


    fun setAuthData(response: UserLoginResponse) {
        UserData = response.user
        tokens = response.token
        userName = response.user?.name ?: ""
        userId = response.user?.id ?: ""
        userRole = response?.user?.role ?: "customer"
        outletDetails = response.user?.outletDetails?.takeIf { it.isNotEmpty() }?.first()
        about = response.about
        faqs = response.faqs

    }
    fun setPoint (string: String){
        userPoint = string
    }


    fun clearAuthData(){
        userName = ""
        tokens = null
        UserData = null
        userId = ""
        userRole = ""
    }

    fun isMerchant(): Boolean = userRole.equals("merchant", ignoreCase = true)
    fun isCustomer(): Boolean = userRole.equals("customer", ignoreCase = true)
}
