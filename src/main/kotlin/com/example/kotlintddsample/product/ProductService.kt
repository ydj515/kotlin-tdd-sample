package com.example.kotlintddsample.product

import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun getProducts(): List<Product> {
        return productRepository.findAll()
    }
}