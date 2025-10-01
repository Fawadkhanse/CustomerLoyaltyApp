package org.example.project.data.mockresponses

import org.example.project.domain.models.auth.resetpassword.ChangePasswordResponse
import org.example.project.domain.models.profile.GetProfileResponse
import org.example.project.domain.models.profile.UpdateProfileResponse

val updateProfileResponse = UpdateProfileResponse(
    message = "Profile updated successfully.",
    user = UpdateProfileResponse.UserProfile(
        id = "12345",
        email = "testuser@example.com",
        name = "Test User",
        phone = "+1234567890",
        profileImage = "https://example.com/profile/testuser.png",
        role = "customer"
    )
)

val getProfileResponse = GetProfileResponse(
    id = "12345",
    email = "testuser@example.com",
    name = "Test User",
    role = "customer",
    phone = "+1234567890",
    profileImage = "https://example.com/profile/testuser.png",
    tc = true,
    createdAt = "2024-01-01T10:00:00Z",
    updatedAt = "2024-06-01T12:00:00Z"
)
val changePasswordResponse= ChangePasswordResponse(message = "usu")