package com.app.whirl.ui.home.OTPVerifyModels

data class OtpMatchResponse(
    val `data`: List<Data>,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
){

    data class Data(
        val amount: String,
        val cancel: String,
        val created_date: String,
        val cupon_code: String,
        val driver_id: String,
        val drop_address: String,
        val drop_latitude: String,
        val drop_longitude: String,
        val end_time: String,
        val id: String,
        val otp: String,
        val otp_status: String,
        val payment_status: String,
        val phone_no: String,
        val pickup_address: String,
        val pickup_latitude: String,
        val pickup_longitude: String,
        val rejected_trip: String,
        val start_time: String,
        val status: String,
        val total_distance: String,
        val total_time: String,
        val user_id: String,
        val wating_time: String
    )
}