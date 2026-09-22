package com.example.appbiblioteca.model

data class Livro(
    val id: Long = 0,
    val titulo: String,
    val autor: String,
    val ano: Int
)