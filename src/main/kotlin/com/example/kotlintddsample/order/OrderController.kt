package com.example.kotlintddsample.order

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping("")
    fun createOrder(@RequestBody orderRequest: OrderRequest): ResponseEntity<String> {
        val result = orderService.createOrder(orderRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }
}