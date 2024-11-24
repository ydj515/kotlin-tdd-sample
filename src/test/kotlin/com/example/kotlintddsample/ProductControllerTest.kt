package com.example.kotlintddsample

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(ProductController::class)
class ProductControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockkBean private val productService: ProductService
) : DescribeSpec({

    beforeEach {
        every { productService.getProducts() } returns listOf(
            Product(1, "Americano", 3000),
            Product(2, "Latte", 4000)
        )
    }

    describe("GET /api/coffees") {
        it("should return list of coffee menus") {
            mockMvc.get("/api/product")
                .andExpect {
                    status { isOk() }
                    jsonPath("$[0].menuId") { value(1) }
                    jsonPath("$[0].name") { value("Americano") }
                    jsonPath("$[0].price") { value(3000) }
                }
        }
    }
})