package carloscaldas.fiap.com.br.azurecatalog.business

import android.content.Context
import android.database.Cursor
import carloscaldas.fiap.com.br.azurecatalog.contants.DataBaseConstants
import carloscaldas.fiap.com.br.azurecatalog.entities.TaskEntity
import carloscaldas.fiap.com.br.azurecatalog.repository.TaskRepository

class TaskBusiness (context: Context){

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)

    fun getList(userId: Int) : MutableList<TaskEntity> = mTaskRepository.getList(userId)

    fun insert(taskEntity: TaskEntity) = mTaskRepository.insert(taskEntity)

}