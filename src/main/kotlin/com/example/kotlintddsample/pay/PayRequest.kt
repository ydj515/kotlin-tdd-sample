package com.example.kotlintddsample.pay

data class PayRequest(
    val orderId: Long,
    val totalPrice: Long,
)