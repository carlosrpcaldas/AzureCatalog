package carloscaldas.fiap.com.br.azurecatalog.entities

import java.util.*

data class TaskEntity ( val id: Int,
                        val userID: Int,
                        val priorityID: Int,
                        var description: String,
                        var dueDate: String,
                        var complete: Boolean)
                        var latitude: String = ""
                            get() = if (field.trim().isEmpty()) "0.0" else field

                        var longitude = ""
                            get() = if (field.trim().isEmpty()) "0.0" else field



