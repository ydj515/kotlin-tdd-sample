package com.example.kotlintddsample.user

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class UserRepositoryTest(
    @Autowired val productRepository: UserRepository
) : DescribeSpec({

    describe("UserRepository") {
        context("findByUsername()을 호출하면") {
            it("데이터베이스에서 username으로 user list를 리턴한다.") {
                // given
                val user1 = User(1L, "testA", 100L)
                val user2 = User(2L, "testB", 100L)
                productRepository.saveAll(listOf(user1, user2))

                // when
                val result: User = productRepository.findByUsername("testA").orElseThrow()

                // then
                result.id shouldBe 1L
                result.username shouldBe "testA"
                result.balance shouldBe 100L
            }
        }

    }
})