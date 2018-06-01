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

    private val createTablePriority = """ CREATE TABLE ${DataBaseConstants.PRIORITY.TABLE_NAME} (
        ${DataBaseConstants.PRIORITY.COLUMNS.ID} INTEGER PRIMARY KEY,
        ${DataBaseConstants.PRIORITY.COLUMNS.DESCRIPTION} TEXT
        );"""


    private val createTableTask = """ CREATE TABLE ${DataBaseConstants.TASK.TABLE_NAME} (
        ${DataBaseConstants.TASK.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DataBaseConstants.TASK.COLUMNS.USERID} INTEGER,
        ${DataBaseConstants.TASK.COLUMNS.PRIORITYID} INTEGER,
        ${DataBaseConstants.TASK.COLUMNS.DESCRIPTION} TEXT,
        ${DataBaseConstants.TASK.COLUMNS.COMPLETE} INTEGER,
        ${DataBaseConstants.TASK.COLUMNS.DUEDATE} TEXT
        );"""


/*    private val insertPriorities = """INSERT INTO ${DataBaseConstants.PRIORITY.TABLE_NAME}
        VALUES (1, 'Baixa'), (2, 'Media'), (3, 'Alta'), (4, 'Critica')"""
*/
    private val insertPriority = ("INSERT INTO ${DataBaseConstants.PRIORITY.TABLE_NAME} values (1, 'Baixa');" +
            "INSERT INTO ${DataBaseConstants.PRIORITY.TABLE_NAME} values (2, 'Média');" +
            "INSERT INTO ${DataBaseConstants.PRIORITY.TABLE_NAME} values (3, 'Alta');" +
            "INSERT INTO ${DataBaseConstants.PRIORITY.TABLE_NAME} values (4, 'Crítica');")

    private val deleteTableUser = "drop table if exists ${DataBaseConstants.USER.TABLE_NAME}"
    private val deleteTablePriority = "drop table if exists ${DataBaseConstants.PRIORITY.TABLE_NAME}"
    private val deleteTableTask = "drop table if exists ${DataBaseConstants.TASK.TABLE_NAME}"

    override fun onCreate(sqlLite: SQLiteDatabase) {
        sqlLite.execSQL(createTableUser)  //execSQL(createTableUser)
        sqlLite.execSQL(createTablePriority)
        sqlLite.execSQL(createTableTask)
        sqlLite.execSQL(insertPriority)

    }

    override fun onUpgrade(sqlLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Remocao
        sqlLite.execSQL(deleteTableUser)
        sqlLite.execSQL(deleteTablePriority)
        sqlLite.execSQL(deleteTableTask)

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