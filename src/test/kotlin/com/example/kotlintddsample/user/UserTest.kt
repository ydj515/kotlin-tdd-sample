package com.example.kotlintddsample.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserTest : BehaviorSpec({

    given("사용자가 잔액을 차감할 때") {
        val user = User(id = 1L, username = "testUser", balance = 1000L)

        `when`("차감할 금액이 주어지면") {
            user.deductBalance(300L)

            Then("잔액이 차감되어야 한다.") {
                user.balance shouldBe 700L
            }
        }

        `when`("차감할 금액이 잔액보다 많을 때") {
            Then("IllegalArgumentException 예외가 발생해야 한다.") {
                shouldThrow<IllegalArgumentException> {
                    user.deductBalance(1200L)
                }
            }
        }
    }

    given("사용자가 잔액이 충분한지 확인할 때") {
        val user = User(id = 1L, username = "testUser", balance = 1000L)

        `when`("잔액이 충분하면") {
            val result = user.hasEnoughBalance(500L)

            Then("true를 반환해야 한다.") {
                result shouldBe true
            }
        }

        `when`("잔액이 부족하면") {
            val result = user.hasEnoughBalance(1500L)

            Then("false를 반환해야 한다.") {
                result shouldBe false
            }
        }
    }
})