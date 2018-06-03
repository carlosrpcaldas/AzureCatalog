package carloscaldas.fiap.com.br.azurecatalog.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import carloscaldas.fiap.com.br.azurecatalog.contants.DataBaseConstants
import carloscaldas.fiap.com.br.azurecatalog.entities.UserEntity
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


    fun get (email: String, password: String) : UserEntity?{

        var userEntity: UserEntity? = null

        try {
            val cursor: Cursor

            val db = mTaskDatabaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.USER.COLUMNS.ID,
                    DataBaseConstants.USER.COLUMNS.NAME,
                    DataBaseConstants.USER.COLUMNS.EMAIL,
                    DataBaseConstants.USER.COLUMNS.PASSWORD)

            val selection = "${DataBaseConstants.USER.COLUMNS.EMAIL} = ? AND ${DataBaseConstants.USER.COLUMNS.PASSWORD} = ?"
            val selectionArgs = arrayOf(email, password)

            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if (cursor.count > 0){
                cursor.moveToFirst()

                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.ID))
                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.NAME))
                val email = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.EMAIL))

                // Preencho a entidade de usuario
                userEntity = UserEntity(userId, name, email)

            }

            cursor.close()
        }catch (e: Exception){
            return userEntity
            //throw e
        }

        return userEntity
    }

    fun isEmailExistent(email: String) : Boolean{

        var ret : Boolean = false
        try {
            val cursor: Cursor

            val db = mTaskDatabaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.USER.COLUMNS.ID)
            val selection = "${DataBaseConstants.USER.COLUMNS.EMAIL} = ?"
            val selectionArgs = arrayOf(email)

            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            ret = cursor.count > 0

            cursor.close()
            // db.rawQuery("select * from user where email - gabriel", null )


        }catch (e: Exception){
            throw e
        }

        return ret
        // Para mais um commit

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

