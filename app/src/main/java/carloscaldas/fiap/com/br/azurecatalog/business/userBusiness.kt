package carloscaldas.fiap.com.br.azurecatalog.business

import android.content.Context
import carloscaldas.fiap.com.br.azurecatalog.R
import carloscaldas.fiap.com.br.azurecatalog.contants.TaskConstants
import carloscaldas.fiap.com.br.azurecatalog.entities.UserEntity
import carloscaldas.fiap.com.br.azurecatalog.repository.UserRepository
import carloscaldas.fiap.com.br.azurecatalog.util.SecurityPreferences
import carloscaldas.fiap.com.br.azurecatalog.util.ValidationException

class UserBusiness (val context: Context){

    private val mUserRepository : UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun login (email: String, password: String){

        val user: UserEntity? = mUserRepository.get(email, password)
        if (user != null){

            mSecurityPreferences.storeString(TaskConstants.Key.USER_ID, user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.Key.USER_EMAIL, user.name)
            mSecurityPreferences.storeString(TaskConstants.Key.USER_NAME, user.email)

        }
    }

    fun insert (name: String, email: String, password: String){

        try {
            if (name == "" || email == "" || password == "") {
                throw ValidationException(context.getString(R.string.informe_todos_campos))
            }

            if (mUserRepository.isEmailExistent(email)){
                throw ValidationException(context.getString(R.string.email_em_uso))
            }

            val userID = mUserRepository.insert(name, email, password)

            // Salvar dados do usuario no shared
            mSecurityPreferences.storeString(TaskConstants.Key.USER_ID, userID.toString())
            mSecurityPreferences.storeString(TaskConstants.Key.USER_EMAIL, name)
            mSecurityPreferences.storeString(TaskConstants.Key.USER_NAME, email)


        } catch (e: Exception) {
            throw e
        }
    }
}