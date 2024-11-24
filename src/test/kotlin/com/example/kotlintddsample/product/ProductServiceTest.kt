package com.example.kotlintddsample.product

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ProductServiceTest(
    @MockkBean private val productRepository: ProductRepository,
    @Autowired private val productService: ProductService
) : BehaviorSpec({

    given("커피 메뉴가 데이터베이스에 저장되어 있을 때") {
        val coffeeList = listOf(
            Product(1, "Americano", 3000),
            Product(2, "Latte", 4000)
        )
        every { productRepository.findAll() } returns coffeeList

        `when`("getCoffees를 호출하면") {
            val result = productService.getProducts()

            then("저장된 모든 커피 메뉴가 반환된다") {
                result shouldHaveSize 2
                result[0].name shouldBe "Americano"
                result[1].price shouldBe 4000
            }
        }
    }

    given("데이터베이스에 커피 메뉴가 없을 때") {
        every { productRepository.findAll() } returns emptyList()

        `when`("getCoffees를 호출하면") {
            val result = productService.getProducts()

            then("빈 리스트가 반환된다") {
                result shouldBe emptyList()
            }
        }
    }
})