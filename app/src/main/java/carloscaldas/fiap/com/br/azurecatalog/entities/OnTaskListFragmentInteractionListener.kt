package carloscaldas.fiap.com.br.azurecatalog.entities

interface OnTaskListFragmentInteractionListener {

    fun onListClick(taskID: Int)

    fun onDeleteClick(taskID: Int)

    fun onUncompleteClick(taskID: Int)

    fun onCompleteClick(taskID: Int)
}