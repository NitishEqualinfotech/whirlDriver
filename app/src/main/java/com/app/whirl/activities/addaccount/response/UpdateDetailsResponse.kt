package com.app.whirl.activities.addaccount.response

data class UpdateDetailsResponse(
    val `data`: Data,
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)
{
    data class Data(
        val ac_number: String,
        val bank_address: String,
        val bank_name: String,
        val driver_id: String,
        val holder_address: String,
        val name: String,
        val ro_number: String
    )
}