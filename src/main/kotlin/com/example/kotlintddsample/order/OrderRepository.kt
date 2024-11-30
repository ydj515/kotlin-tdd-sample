package com.example.kotlintddsample.order

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OrderRepository : JpaRepository<Order, Long> {
    @Query("select o from Order o left join fetch o.user")
    fun findAllWithUser(): List<Order>
}