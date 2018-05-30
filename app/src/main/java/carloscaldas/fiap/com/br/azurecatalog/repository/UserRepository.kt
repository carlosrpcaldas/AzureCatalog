package carloscaldas.fiap.com.br.azurecatalog.repository

import android.content.ContentValues
import android.content.Context
import carloscaldas.fiap.com.br.azurecatalog.contants.DataBaseConstants
import java.security.AccessControlContext

class UserRepository private constructor(context: Context) {

    private var mTaskDatabaseHelper : TaskDatabaseHelper = TaskDatabaseHelper(context)

    companion object {
        fun getInstance (context: Context) : UserRepository{
            if (INSTANCE == null){
                INSTANCE = UserRepository(context)
            }
            return INSTANCE as UserRepository
        }

        private var INSTANCE: UserRepository? = null

    }

    fun insert(name: String, email: String, password: String) : Int{
        // select, update, inserte, delete
        val db = mTaskDatabaseHelper.writableDatabase

        val insertValues = ContentValues()
        insertValues.put(DataBaseConstants.USER.COLUMNS.NAME, name)
        insertValues.put(DataBaseConstants.USER.COLUMNS.EMAIL, email)
        insertValues.put(DataBaseConstants.USER.COLUMNS.PASSWORD, password)

        return db.insert(DataBaseConstants.USER.TABLE_NAME, null, insertValues).toInt()
    }

}

