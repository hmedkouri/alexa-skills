package io.anaxo.alexa.napster.db

import org.mapdb.DB
import org.mapdb.DBMaker


class DbFactories private constructor() {

    fun file(fileName: String): DbFactory {
        return FileDbFactory(fileName)
    }

    fun mem(): DbFactory {
        return MemDbFactory()
    }

    private class FileDbFactory(private val fileName: String) : DbFactory {

        override fun createDb(): DB {
            return DbFactory.applyCommonOptions(DBMaker.fileDB(fileName))
        }
    }

    private class MemDbFactory : DbFactory {

        override fun createDb(): DB {
            return DbFactory.applyCommonOptions(DBMaker.memoryDB())
        }
    }
}
