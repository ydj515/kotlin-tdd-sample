package com.example.kotlintddsample.product

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class ProductRepositoryTest(
    @Autowired val productRepository: ProductRepository
) : DescribeSpec({

    describe("ProductRepository") {
        context("findAll()을 호출하면") {
            it("데이터베이스에서 product list를 리턴한다.") {
                // given
                val coffee1 = Product(id = 1, name = "Americano", price = 3000)
                val coffee2 = Product(id = 2, name = "Latte", price = 4000)
                productRepository.saveAll(listOf(coffee1, coffee2))

                // when
                val result = productRepository.findAll()

                // then
                result shouldHaveSize 2
                result[0].name shouldBe "Americano"
                result[1].price shouldBe 4000
            }
        }

    }
})