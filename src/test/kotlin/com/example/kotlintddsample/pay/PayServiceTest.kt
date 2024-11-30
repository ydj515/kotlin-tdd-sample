package com.example.kotlintddsample.pay

import com.example.kotlintddsample.client.PayExternalClient
import com.example.kotlintddsample.order.Order
import com.example.kotlintddsample.order.OrderRepository
import com.example.kotlintddsample.order.OrderStatus
import com.example.kotlintddsample.user.User
import com.example.kotlintddsample.user.UserRepository
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class PayServiceTest(
    @MockkBean private val orderRepository: OrderRepository,
    @MockkBean private val userRepository: UserRepository,
    @MockkBean private val payExternalClient: PayExternalClient,
    @Autowired private val payService: PayService

) : BehaviorSpec({

    given("결제 가능한 주문이 있을 때") {
        val user = User(1L, "testUser", 1000L)
        val order = Order(1L, 1L, user, OrderStatus.PAYABLE)
        val updatedOrder = Order(1L, 1L, user, OrderStatus.PAID)
        val payRequest = PayRequest(1L, 500L)
        val paymentInfo = PaymentInfo(user.username, order.productId, payRequest.totalPrice)

        every { orderRepository.findById(1L) } returns Optional.of(order)
        every { userRepository.save(user) } returns user
        every { payExternalClient.send(match { it.username == paymentInfo.username && it.productId == paymentInfo.productId && it.amount == paymentInfo.amount }) } returns true
        every { orderRepository.save(order) } returns updatedOrder

        `when`("pay 메서드를 호출하면") {
            val result = payService.pay(payRequest)

            Then("결제가 성공하고 주문 상태가 PAID로 변경된다.") {
                result shouldBe "결제가 완료되었습니다."
                order.status shouldBe OrderStatus.PAID
                user.balance shouldBe 500L
            }
        }
    }

    given("잔고가 부족한 사용자일 때") {
        val user = User(1L, "testUser", 100L)
        val order = Order(1L, 1L, user, OrderStatus.PAYABLE)
        val payRequest = PayRequest(1L, 500L)
        val paymentInfo = PaymentInfo(user.username, order.productId, payRequest.totalPrice)

        every { orderRepository.findById(1L) } returns Optional.of(order)
        every { payExternalClient.send(paymentInfo) } returns true

        `when`("pay 메서드를 호출하면") {
            val exception = shouldThrow<IllegalArgumentException> {
                payService.pay(payRequest)
            }

            Then("잔고 부족 예외가 발생한다.") {
                exception.message shouldBe "can not deduct balance for ${payRequest.totalPrice}"
            }
        }
    }

})
