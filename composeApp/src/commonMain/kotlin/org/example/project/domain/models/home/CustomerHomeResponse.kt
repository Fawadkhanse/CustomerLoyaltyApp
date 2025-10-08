package org.example.project.domain.models.home


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerHomeResponse(
    @SerialName("success")
    val success: Boolean? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: CustomerHomeData? = null
)

@Serializable
data class CustomerHomeData(
    @SerialName("user")
    val user: HomeUser? = null,
    @SerialName("promotions")
    val promotions: List<HomePromotion>? = null,
    @SerialName("available_coupons")
    val availableCoupons: List<HomeAvailableCoupon>? = null,
    @SerialName("recent_activity")
    val recentActivity: List<HomeActivity>? = null
){

    @Serializable
    data class HomeUser(
        @SerialName("id")
        val id: String? = null,
        @SerialName("user")
        val name: String? = null,
        @SerialName("total_points")
        val points: Int? = null,
        @SerialName("tier")
        val tier: String? = null
    )

    @Serializable
    data class HomePromotion(
        @SerialName("id")
        val id: String? = null,
        @SerialName("title")
        val title: String? = null,
        @SerialName("image_url")
        val imageUrl: String? = null,
        @SerialName("expiry_date")
        val expiryDate: String? = null
    )

    @Serializable
    data class HomeAvailableCoupon(
        @SerialName("id")
        val id: String? = null,
        @SerialName("title")
        val title: String? = null,
        @SerialName("description")
        val description: String? = null,
        @SerialName("points_required")
        val pointsRequired: Int? = null,
        @SerialName("expiry_date")
        val expiryDate: String? = null
    )

    @Serializable
    data class HomeActivity(
        @SerialName("id")
        val id: String? = null,
        @SerialName("type")
        val type: String? = null, // "earned" or "redeemed"
        @SerialName("description")
        val description: String? = null,
        @SerialName("points")
        val points: Int? = null,
        @SerialName("date")
        val date: String? = null
    )
}
