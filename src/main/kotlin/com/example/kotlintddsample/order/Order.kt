package com.example.kotlintddsample.order

import com.example.kotlintddsample.user.User
import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var productId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus = OrderStatus.PAYABLE
)

enum class OrderStatus {
    PAYABLE, ORDERED
}