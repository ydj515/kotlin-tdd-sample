package com.example.kotlintddsample.order

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(OrderController::class)
class OrderControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockkBean private val orderService: OrderService
) : DescribeSpec({

    val mapper = jacksonObjectMapper()

    describe("POST /api/orders") {
        context("username과 product Id를 요청으로 보냈을 때") {
            it("201 응답과 새 주문이 생성된다.") {
                val orderRequest = Order(1L, 1L, "testUser")

                // MockK의 every 설정에서 예상한 Order 객체와 실제 요청에 전달된 Order 객체가 동일해야함.
                // MockK는 객체를 비교할 때 기본적으로 참조 비교를 수행하므로, 두 객체가 다른 메모리 주소를 가지면 실패한다.
                // Order 객체의 특정 필드 값만 비교하도록 match를 사용합니다.
                // 이 방법은 객체가 아닌 필드 값에 따라 매칭하기 때문에 객체 생성 방식과 상관없이 일치합니다.
                every { orderService.createOrder(match { it.id == 1L && it.productId == 1L && it.username == "testUser" }) } returns "주문이 완료되었습니다."

                mockMvc.post("/api/orders") {
                    contentType = MediaType.APPLICATION_JSON
                    content = mapper.writeValueAsString(orderRequest)
                }.andExpect {
                    status { isCreated() }
                    content { string("주문이 완료되었습니다.") }
                }
            }
        }
    }
})
