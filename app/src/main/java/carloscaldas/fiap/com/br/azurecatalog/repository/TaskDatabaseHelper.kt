package carloscaldas.fiap.com.br.azurecatalog.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import carloscaldas.fiap.com.br.azurecatalog.contants.DataBaseConstants

class TaskDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ) {

    companion object {
        private val DATABASE_VERSION: Int = 1
        private val DATABASE_NAME: String = "taks.db"
    }
    // SQLite
    // INTEGER, REAL, TEXT, BLOB

    private val createTableUser = """ CREATE TABLE ${DataBaseConstants.USER.TABLE_NAME} (
        ${DataBaseConstants.USER.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DataBaseConstants.USER.COLUMNS.NAME} TEXT,
        ${DataBaseConstants.USER.COLUMNS.EMAIL} TEXT,
        ${DataBaseConstants.USER.COLUMNS.PASSWORD} TEXT
        );"""

    private val deleteTableUser = "drop table if exists ${DataBaseConstants.USER.TABLE_NAME}"

    override fun onCreate(sqlLite: SQLiteDatabase) {
        sqlLite.execSQL(createTableUser)  //execSQL(createTableUser)
    }

    override fun onUpgrade(sqlLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Remocao
        sqlLite.execSQL(deleteTableUser)

        // Criacao
        sqlLite.execSQL(createTableUser)

        when (oldVersion){
            1 -> {
            // atualizacao da 1 para 2
            // atualizacao da 2 para 3 - 3 para 4
            // da 4 ára a 5
            }
        }

    }
}