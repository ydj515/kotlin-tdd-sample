package com.example.kotlintddsample.order

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(OrderController::class)
class OrderControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockkBean private val orderService: OrderService
) : DescribeSpec({

    describe("POST /api/orders") {
        context("username과 product Id를 요청으로 보냈을 때") {
            it("201 응답과 새주문이 생성된다.") {
                mockMvc.post("/api/orders")
                    .andExpect {
                        status { isCreated() }
                    }
            }
        }
    }
})