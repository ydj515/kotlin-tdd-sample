package com.example.kotlintddsample.order

import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var productId: Long,
    var username: String,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus = OrderStatus.PAYABLE
)

enum class OrderStatus {
    PAYABLE
}