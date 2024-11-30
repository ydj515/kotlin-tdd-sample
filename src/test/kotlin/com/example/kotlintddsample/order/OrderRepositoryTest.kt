package com.example.kotlintddsample.order

import com.example.kotlintddsample.user.User
import com.example.kotlintddsample.user.UserRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class OrderRepositoryTest(
    @Autowired private val orderRepository: OrderRepository,
    @Autowired private val userRepository: UserRepository
) : DescribeSpec({

    describe("orderRepository") {
        context("save()을 호출하면") {
            it("데이터베이스에서 order를 저장한다.") {
                // given
                val user = User(1L, "testUser", 1000)
                val savedUser = userRepository.save(user)

                val order = Order(1L, 1L, savedUser)

                orderRepository.save(order)

                // when
                val result = orderRepository.findAllWithUser()

                // then
                result shouldHaveSize 1
                result[0].id shouldBe 1L
                result[0].productId shouldBe 1L
                result[0].user.username shouldBe "testUser"
            }
        }

    }
})