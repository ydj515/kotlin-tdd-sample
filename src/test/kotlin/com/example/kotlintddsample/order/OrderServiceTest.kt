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
        val order = Order(1L, 1L, "testUser")

        `when`("createOrder 메서드가 호출되면") {
            every { orderRepository.save(order) } returns order

            val result = orderService.createOrder(order)

            Then("결과로 '주문이 완료되었습니다.' 메시지가 반환된다.") {
                result shouldBe "주문이 완료되었습니다."
            }
        }
    }

    // TODO : 예외 사항에 대해서 추가
})