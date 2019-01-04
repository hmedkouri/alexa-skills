package io.anaxo.alexa.napster.db

import org.mapdb.DB
import org.mapdb.DBMaker


interface DbFactory {

    fun createDb(): DB

    companion object {
        fun applyCommonOptions(maker: DBMaker.Maker): DB {
            return maker
                    .transactionEnable()
                    .closeOnJvmShutdown()
                    .make()
        }
    }
}
