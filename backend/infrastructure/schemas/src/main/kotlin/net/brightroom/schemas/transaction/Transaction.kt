package net.brightroom.schemas.transaction

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

suspend inline fun <T> transaction(
    db: R2dbcDatabase,
    readOnly: Boolean = false,
    crossinline block: suspend () -> T,
): T =
    withContext(Dispatchers.IO) {
        suspendTransaction(readOnly = readOnly, db = db) {
            addLogger(StdOutSqlLogger)
            block()
        }
    }

suspend inline fun <T> transactionAsync(
    db: R2dbcDatabase,
    readOnly: Boolean = false,
    crossinline block: suspend () -> T,
): Deferred<T> =
    coroutineScope {
        async(Dispatchers.IO) {
            suspendTransaction(readOnly = readOnly, db = db) {
                addLogger(StdOutSqlLogger)
                block()
            }
        }
    }
