package com.example.kotlintddsample.pay

import com.example.kotlintddsample.client.PayExternalClient
import com.example.kotlintddsample.order.OrderRepository
import com.example.kotlintddsample.order.OrderStatus
import com.example.kotlintddsample.user.UserRepository
import org.springframework.stereotype.Service

@Service
class PayService(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val payExternalClient: PayExternalClient
) {

    fun pay(payRequest: PayRequest): String {
        val order = orderRepository.findById(payRequest.orderId).orElseThrow {
            IllegalArgumentException("Order not found for id: $payRequest.orderId")
        }

        if (order.status != OrderStatus.PAYABLE) {
            throw IllegalStateException("Order is not in payable status.")
        }

        val user = order.user
        user.deductBalance(payRequest.totalPrice)
        userRepository.save(user) // 명시적 save

        val paymentInfo = PaymentInfo(user.username, order.productId, payRequest.totalPrice)
        payExternalClient.send(paymentInfo)

        order.status = OrderStatus.PAID
        orderRepository.save(order) // 명시적 save

        return "결제가 완료되었습니다."
    }
}