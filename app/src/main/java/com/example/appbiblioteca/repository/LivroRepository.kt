package com.example.appbiblioteca.repository

import android.content.ContentValues
import com.example.appbiblioteca.database.DatabaseHelper
import com.seunome.biblioteca.database.DatabaseHelper
import com.seunome.biblioteca.model.Livro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LivroRepository(private val dbHelper: DatabaseHelper) {

    suspend fun inserir(livro: Livro) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put(DatabaseHelper.COL_TITULO, livro.titulo)
            put(DatabaseHelper.COL_AUTOR, livro.autor)
            put(DatabaseHelper.COL_ANO, livro.ano)
        }
        db.insert(DatabaseHelper.TABLE_LIVROS, null, valores)
    }
}