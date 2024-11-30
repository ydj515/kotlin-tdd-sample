package com.example.kotlintddsample.order

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderServiceTest(
    @MockkBean private val orderRepository: OrderRepository,
    @Autowired private val orderService: OrderService
) : BehaviorSpec({

    given("유효한 Order 객체가 전달될 때") {
        val orderRequest = OrderRequest(1L, "testUser")
        val savedOrder = Order(1L, orderRequest.productId, orderRequest.username, OrderStatus.PAYABLE)

        `when`("createOrder 메서드가 호출되면") {
            every {
                orderRepository.save(match {
                    it.productId == orderRequest.productId && it.username == orderRequest.username && it.status == OrderStatus.PAYABLE
                })
            } returns savedOrder

            val result = orderService.createOrder(orderRequest)

            Then("결과로 '주문이 완료되었습니다.' 메시지가 반환된다.") {
                result shouldBe "주문이 완료되었습니다."
            }
        }
    }

    // TODO : 예외 사항에 대해서 추가
})