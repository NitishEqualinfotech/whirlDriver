package com.app.whirl.activities.wallet

data class AccountDetailsResponse(
    val `data`: List<Data>,
    val driver_amount: String,
    val error: Int,
    val msg: String,
    val pay_amount: String,
    val pending_amount: String,
    val service: String,
    val success: String,
    val total_amount: String
)
{
    data class Data(
        val ac_number: String,
        val bank_address: String,
        val bank_name: String,
        val created_date: String,
        val driver_id: String,
        val holder_address: String,
        val id: String,
        val name: String,
        val ro_number: String
    )
}