package com.example.kotlintddsample.order

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
class OrderServiceTest(
    @MockkBean private val orderRepository: OrderRepository,
    @MockkBean private val userRepository: UserRepository,
    @Autowired private val orderService: OrderService
) : BehaviorSpec({

    given("유효한 Order 객체가 전달될 때") {
        val orderRequest = OrderRequest(1L, "testUser")
        val user = User(1L, "testUser", 1000L)
        val savedOrder = Order(1L, orderRequest.productId, user, OrderStatus.PAYABLE)

        `when`("createOrder 메서드가 호출되면") {
            every {
                userRepository.findByUsername(orderRequest.username)
            } returns Optional.of(user)

            every {
                orderRepository.save(match {
                    it.productId == orderRequest.productId && it.user == user && it.status == OrderStatus.PAYABLE
                })
            } returns savedOrder

            val result = orderService.createOrder(orderRequest)

            Then("결과로 '주문이 완료되었습니다.' 메시지가 반환된다.") {
                result shouldBe "주문이 완료되었습니다."
            }
        }
    }

    given("유효하지 않은 사용자 이름이 전달될 때") {
        val orderRequest = OrderRequest(1L, "nonExistingUser")

        `when`("createOrder 메서드가 호출되면") {
            every {
                userRepository.findByUsername(orderRequest.username)
            } returns Optional.empty()

            Then("IllegalArgumentException 예외가 발생한다.") {
                shouldThrow<IllegalArgumentException> {
                    orderService.createOrder(orderRequest)
                }
            }
        }
    }
})
