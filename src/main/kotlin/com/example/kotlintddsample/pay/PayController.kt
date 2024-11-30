package com.example.kotlintddsample.pay

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pay")
class PayController(
    val payService: PayService
) {
    @PostMapping("")
    fun pay(@RequestBody payRequest: PayRequest): ResponseEntity<String> {
        val result = payService.pay(payRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }
}