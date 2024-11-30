package com.example.kotlintddsample.pay

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(PayController::class)
class PayControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockkBean private val payService: PayService
) : DescribeSpec({

    val mapper = jacksonObjectMapper()

    describe("POST /api/pay") {
        context("orderId와 totalPrice를 요청으로 보냈을 때") {
            it("201응답과 결제가 완료된다.") {
                val payRequest = PayRequest(1L, 1000L)

                every { payService.pay(match { it.orderId == 1L && it.totalPrice == 1000L }) } returns "결제가 완료되었습니다."

                mockMvc.post("/api/pay") {
                    contentType = MediaType.APPLICATION_JSON
                    content = mapper.writeValueAsString(payRequest)
                }.andExpect {
                    status { isCreated() }
                    content { string("결제가 완료되었습니다.") }
                }
            }
        }
    }

})
