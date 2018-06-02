package carloscaldas.fiap.com.br.azurecatalog.business

import android.content.Context
import android.database.Cursor
import carloscaldas.fiap.com.br.azurecatalog.contants.DataBaseConstants
import carloscaldas.fiap.com.br.azurecatalog.contants.TaskConstants
import carloscaldas.fiap.com.br.azurecatalog.entities.TaskEntity
import carloscaldas.fiap.com.br.azurecatalog.repository.TaskRepository
import carloscaldas.fiap.com.br.azurecatalog.util.SecurityPreferences

class TaskBusiness (context: Context){

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun getList() : MutableList<TaskEntity> {
        val userID = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()
        return mTaskRepository.getList(userID)

    }
    fun insert(taskEntity: TaskEntity) = mTaskRepository.insert(taskEntity)

}