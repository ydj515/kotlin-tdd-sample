package com.example.kotlintddsample.product

import jakarta.persistence.*

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String,
    var price: Int
)