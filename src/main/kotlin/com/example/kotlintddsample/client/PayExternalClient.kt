package com.example.kotlintddsample.client

import com.example.kotlintddsample.pay.PaymentInfo
import org.springframework.stereotype.Component

@Component
class PayExternalClient {
    fun send(paymentInfo: PaymentInfo): Boolean {
        println("지불 정보를 보냈습니다.")
        return true
    }
}