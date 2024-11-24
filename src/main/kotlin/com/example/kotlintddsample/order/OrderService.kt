package com.example.kotlintddsample.order

import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {
    fun createOrder(order: Order): String {
        orderRepository.save(order)
        return "주문이 완료되었습니다."
    }
}