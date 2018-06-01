package carloscaldas.fiap.com.br.azurecatalog.entities

import java.util.*

data class TaskEntity (val id: Int,
                       val userID: Int,
                       val priorityID: Int,
                       var description: String,
                       var dueDate: String,
                       var complete: Boolean){



}