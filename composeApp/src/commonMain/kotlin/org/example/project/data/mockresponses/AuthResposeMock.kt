package org.example.project.data.mockresponses

import org.example.project.domain.models.auth.login.UserDataResponse
import org.example.project.domain.models.auth.login.UserLoginResponse
import org.example.project.domain.models.auth.register.TokenResponse
import org.example.project.domain.models.auth.register.UserRegistrationResponse
import org.example.project.domain.models.auth.resetpassword.ForgotPasswordResponse
import org.example.project.domain.models.auth.resetpassword.ResetPasswordResponse


val userLoginResponse = UserLoginResponse(
    token = TokenResponse(refresh = "habitant", access = "felis"),
    message = "success",
    user = UserDataResponse(
        id = "est",
        email = "shelia.nixon@example.com",
        name = "Garth Lewis",
        role = "merchant",
        phone = "(395) 884-9407", profileImage = "scripta"
    )
)
val userRegisterResponse = UserRegistrationResponse(
    token = TokenResponse(refresh = "habitant", access = "felis"),
    message = "success",
    user = UserDataResponse(
        id = "est",
        email = "shelia.nixon@example.com",
        name = "Garth Lewis",
        role = "customer",
        phone = "(395) 884-9407", profileImage = "velit",
        
    )
)
val  forgotPasswordResponse= ForgotPasswordResponse(
    message = "voluptatum",
    uid = "proin",
    token = "nascetur",
    resetLink = "disputationi"
)
val resetPasswordResponse= ResetPasswordResponse(message = "imperdiet")