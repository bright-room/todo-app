package net.brightroom.schemas.transaction

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransactionAsync

suspend inline fun <T> transaction(
    db: R2dbcDatabase,
    readOnly: Boolean = false,
    crossinline block: suspend () -> T,
): T =
    suspendTransaction(context = Dispatchers.IO, readOnly = readOnly, db = db) {
        addLogger(StdOutSqlLogger)
        block()
    }

suspend inline fun <T> transactionAsync(
    db: R2dbcDatabase,
    readOnly: Boolean = false,
    crossinline block: suspend () -> T,
): Deferred<T> =
    suspendTransactionAsync(context = Dispatchers.IO, readOnly = readOnly, db = db) {
        addLogger(StdOutSqlLogger)
        block()
    }
