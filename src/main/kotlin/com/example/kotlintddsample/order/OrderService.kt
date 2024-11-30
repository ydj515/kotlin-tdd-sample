package com.example.kotlintddsample.order

import com.example.kotlintddsample.user.UserRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
) {
    fun createOrder(orderRequest: OrderRequest): String {
        val user = userRepository.findByUsername(orderRequest.username)
            .orElseThrow { IllegalArgumentException("User not found with username: ${orderRequest.username}") }

        val order = Order(
            productId = orderRequest.productId,
            user = user
        )

        orderRepository.save(order)
        return "주문이 완료되었습니다."
    }
}