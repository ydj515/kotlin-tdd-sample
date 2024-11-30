package com.example.kotlintddsample.order

import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {
    fun createOrder(orderRequest: OrderRequest): String {
        val order = orderRequest.toOrder()
        orderRepository.save(order)
        return "주문이 완료되었습니다."
    }
}