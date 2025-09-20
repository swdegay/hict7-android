package dev.sethdegay.hict7.core.database.util

import androidx.room.withTransaction
import dev.sethdegay.hict7.core.database.Hict7Database

class AppTransactionProvider internal constructor(private val database: Hict7Database) {
    suspend fun <R> runInTransaction(block: suspend () -> R): R {
        return database.withTransaction(block)
    }
}