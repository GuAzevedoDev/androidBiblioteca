package com.example.appbiblioteca.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbiblioteca.database.`DatabaseHelper.kt`
import com.example.appbiblioteca.model.`Livro.kt`
import com.example.appbiblioteca.repository.LivroRepository
import kotlinx.coroutines.launch

sealed interface BibliotecaUiState {
    object Loading : BibliotecaUiState
    data class Success(val livros: List<`Livro.kt`>) : BibliotecaUiState
    data class Error(val message: String) : BibliotecaUiState
}

class BibliotecaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LivroRepository(`DatabaseHelper.kt`(application))

    var uiState: BibliotecaUiState by mutableStateOf(BibliotecaUiState.Loading)
        private set

    init { carregarLivros() }

    fun carregarLivros() {
        viewModelScope.launch {
            uiState = BibliotecaUiState.Loading
            uiState = try {
                BibliotecaUiState.Success(repository.listarTodos())
            } catch (e: Exception) {
                BibliotecaUiState.Error(e.message ?: "Erro ao acessar o banco de dados.")
            }
        }
    }

    fun salvarLivro(titulo: String, autor: String, ano: Int) {
        viewModelScope.launch {
            repository.inserir(`Livro.kt`(titulo = titulo, autor = autor, ano = ano))
            carregarLivros()
        }
    }
}