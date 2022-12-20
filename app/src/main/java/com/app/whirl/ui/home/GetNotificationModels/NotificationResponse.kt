package com.app.whirl.ui.home.GetNotificationModels

data class NotificationResponse(
    val error: Int,
    val msg: String,
    val service: String,
    val success: String
)