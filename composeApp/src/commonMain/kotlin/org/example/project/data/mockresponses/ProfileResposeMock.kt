package org.example.project.data.mockresponses

import org.example.project.domain.models.TransactionResponse
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
        role = "customer",
        tc = true,
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


// Mock data for testing
 fun getMockTransactionsList(): List<TransactionResponse> {
    return listOf(
        TransactionResponse(
            id = "98468baa-a3ea-4d66-b28f-88aecaf16e29",
            user = "50ec61ca-9964-4e0a-8160-46e26dd61f13",
            merchant = "37314e7f-9c1d-4515-bdf4-0752c8318c2b",
            outlet = "ebd8c449-f9b6-4766-aac2-faf7445ab1b2",
            coupon = "fe7007fc-116f-4189-9767-e02a8d8435a8",
            points = 50,
            createdAt = "2025-09-20T10:49:59.810557Z"
        ),
        TransactionResponse(
            id = "12345678-1234-1234-1234-123456789012",
            user = "50ec61ca-9964-4e0a-8160-46e26dd61f13",
            merchant = "37314e7f-9c1d-4515-bdf4-0752c8318c2b",
            outlet = "ebd8c449-f9b6-4766-aac2-faf7445ab1b2",
            coupon = null,
            points = 100,
            createdAt = "2025-09-21T15:30:00.810557Z"
        )
    )
}

 fun getMockTransaction(): TransactionResponse {
    return TransactionResponse(
        id = "98468baa-a3ea-4d66-b28f-88aecaf16e29",
        user = "50ec61ca-9964-4e0a-8160-46e26dd61f13",
        merchant = "37314e7f-9c1d-4515-bdf4-0752c8318c2b",
        outlet = "ebd8c449-f9b6-4766-aac2-faf7445ab1b2",
        coupon = "fe7007fc-116f-4189-9767-e02a8d8435a8",
        points = 50,
        createdAt = "2025-09-20T10:49:59.810557Z"
    )
}