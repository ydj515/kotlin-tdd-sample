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
    fun createOrder(@RequestBody order: Order): ResponseEntity<String> {
        val result = orderService.createOrder(order)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }
}