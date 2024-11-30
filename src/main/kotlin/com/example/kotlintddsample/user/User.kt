package com.example.kotlintddsample.user

import com.example.kotlintddsample.order.Order
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var username: String,
    var balance: Long,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orders: List<Order> = mutableListOf()

) {
    fun deductBalance(amount: Long) {
        if (!hasEnoughBalance(amount)) {
            throw IllegalArgumentException("can not deduct balance for $amount")
        }
        this.balance -= amount
    }

    fun hasEnoughBalance(amount: Long): Boolean {
        return this.balance >= amount
    }
}