package org.example.project.domain.models.home


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerHomeResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String?,
    @SerialName("data")
    val data: CustomerHomeData
)

@Serializable
data class CustomerHomeData(
    @SerialName("user")
    val user: HomeUser?,
    @SerialName("promotions")
    val promotions: List<HomePromotion>?,
    @SerialName("available_coupons")
    val availableCoupons: List<HomeAvailableCoupon>?,
    @SerialName("recent_activity")
    val recentActivity: List<HomeActivity>?
){

    @Serializable
    data class HomeUser(
        @SerialName("name")
        val name: String?,
        @SerialName("points")
        val points: Int?,
        @SerialName("tier")
        val tier: String?
    )

    @Serializable
    data class HomePromotion(
        @SerialName("id")
        val id: String?,
        @SerialName("title")
        val title: String?,
        @SerialName("image_url")
        val imageUrl: String?,
        @SerialName("expiry_date")
        val expiryDate: String?
    )

    @Serializable
    data class HomeAvailableCoupon(
        @SerialName("id")
        val id: String?,
        @SerialName("title")
        val title: String?,
        @SerialName("description")
        val description: String?,
        @SerialName("points_required")
        val pointsRequired: Int?,
        @SerialName("expiry_date")
        val expiryDate: String?
    )

    @Serializable
    data class HomeActivity(
        @SerialName("id")
        val id: String?,
        @SerialName("type")
        val type: String?, // "earned" or "redeemed"
        @SerialName("description")
        val description: String?,
        @SerialName("points")
        val points: Int?,
        @SerialName("date")
        val date: String?
    )
}
