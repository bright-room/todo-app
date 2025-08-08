package net.brightroom.schemas.transaction

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

inline fun <T> transaction(
    db: R2dbcDatabase,
    readOnly: Boolean = true,
    crossinline block: suspend () -> T,
): T =
    runBlocking {
        suspendTransaction(context = Dispatchers.IO, readOnly = readOnly, db = db) {
            addLogger(StdOutSqlLogger)
            block()
        }
    }
