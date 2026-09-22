package com.example.appbiblioteca.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_LIVROS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITULO TEXT NOT NULL,
                $COL_AUTOR TEXT NOT NULL,
                $COL_ANO INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LIVROS")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "biblioteca.db"
        const val DATABASE_VERSION = 1
        const val TABLE_LIVROS = "livros"
        const val COL_ID = "id"
        const val COL_TITULO = "titulo"
        const val COL_AUTOR = "autor"
        const val COL_ANO = "ano"
    }
}