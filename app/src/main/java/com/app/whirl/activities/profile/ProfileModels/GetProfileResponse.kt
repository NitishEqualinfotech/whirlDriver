package com.app.whirl.activities.profile.ProfileModels

data class GetProfileResponse(
    val `data`: Data,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)
{
    data class Data(
        val active_status: String,
        val driving_license: String,
        val email: String,
        val fname: String,
        val id: String,
        val lname: String,
        val mobile: String,
        val pan_card: String,
        val profile_photo: String,
        val reistration_certificate: String,
        val social: String,
        val step: String,
        val tag_no: String,
        val vehcile_insurence: String,
        val vehicle_name: String,
        val vehicle_permit: String,
        val vehicle_type: String
    )
}