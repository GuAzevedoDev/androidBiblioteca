package com.example.appbiblioteca.repository

import com.example.appbiblioteca.database.LivroDao
import com.example.appbiblioteca.database.LivroEntity
import com.example.appbiblioteca.model.Livro
import com.example.appbiblioteca.network.BookDoc
import com.example.appbiblioteca.network.OpenLibraryService
import com.example.appbiblioteca.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LivroRepository(
    private val dao: LivroDao,
    private val api: OpenLibraryService = RetrofitClient.api
) {
    // Observar: a tela se inscreve aqui uma única vez
    fun observeLivros(): Flow<List<Livro>> =
        dao.observeAll().map { entidades -> entidades.map { it.toDomain() } }

    // Buscar e Armazenar: só alimenta o Room, não devolve nada direto pra tela
    suspend fun buscarEArmazenar(query: String) {
        val resposta = api.buscarLivros(query)
        dao.insertAll(resposta.docs.map { it.toEntity() })
    }
}

private fun LivroEntity.toDomain() = Livro(id = id, titulo = titulo, autor = autor, ano = ano)

private fun BookDoc.toEntity() = LivroEntity(
    titulo = title,
    autor = authorName?.firstOrNull() ?: "Autor desconhecido",
    ano = firstPublishYear ?: 0
)
