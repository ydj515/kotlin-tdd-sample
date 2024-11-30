package com.example.kotlintddsample.order

data class OrderRequest(
    val productId: Long,
    val username: String,
)

fun OrderRequest.toOrder(): Order {
    return Order(
        productId = productId,
        username = username,
    )
}
