package com.example.kotlintddsample.product

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val coffeeService: ProductService
) {

    @GetMapping("")
    fun getProducts(): List<Product> {
        return coffeeService.getProducts()
    }
}