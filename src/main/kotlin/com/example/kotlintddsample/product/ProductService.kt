package com.example.kotlintddsample.product

import org.springframework.stereotype.Service

@Service
class ProductService(

) {
    fun getProducts(): List<Product> {
        return listOf(
            Product(1, "Americano", 3000),
            Product(2, "Latte", 4000)
        )
    }
}