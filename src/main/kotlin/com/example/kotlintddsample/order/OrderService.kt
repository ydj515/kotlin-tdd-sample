package com.example.kotlintddsample.order

import org.springframework.stereotype.Service

@Service
class OrderService {
    fun createOrder(order: Order): String {
        return "주문이 완료되었습니다."
    }
}