package org.example.project.domain.models.auth.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.models.OutletResponse

@Serializable
data class UserDataResponse(
    @SerialName("id")
    val id: String? = null,
    @SerialName("email")
    var email: String? = null,
    @SerialName("name")
    var name: String? = null,
    @SerialName("role")
    val role: String? = null,
    @SerialName("phone")
    var phone: String? = null,
    @SerialName("profile_image")
    var profileImage: String? = null,
    @SerialName("unique_qr_id")
    val uniqueQrId: String? = null,
    @SerialName("country")
    var country: String?=null,
    @SerialName("state")
    var state: String?=null,
    @SerialName("address")
    var address: String?=null,
    @SerialName("region")
    var region: String?=null,
    @SerialName("postalcode")
    var postalCode: String?=null,
    @SerialName("outlet_details")
    val outletDetails: List<OutletResponse>? = null

)
