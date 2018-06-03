package carloscaldas.fiap.com.br.azurecatalog.viewholder

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import carloscaldas.fiap.com.br.azurecatalog.R
import carloscaldas.fiap.com.br.azurecatalog.entities.OnTaskListFragmentInteractionListener
import carloscaldas.fiap.com.br.azurecatalog.entities.TaskEntity
import carloscaldas.fiap.com.br.azurecatalog.repository.PriorityCacheConstants

class TaskViewHolder(itemView: View, val context: Context, val listener: OnTaskListFragmentInteractionListener) : RecyclerView.ViewHolder(itemView){

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPriority)
    private val mTextDate: TextView = itemView.findViewById(R.id.textDueDate)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)


    fun bindData(task: TaskEntity){
        mTextDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(task.priorityID)
        mTextDate.text = task.dueDate

        if (task.complete){
            mImageTask.setImageResource(R.drawable.ic_done)
        }

        // Evento de click para edicao
        mTextDescription.setOnClickListener({
            listener.onListClick(task.id)
        })

        mTextDescription.setOnLongClickListener({
            showConfirmationDialog(task)

            true
        })

    }
    private fun showConfirmationDialog(task: TaskEntity){
        //listener.onDeleteClick(taskId)

        AlertDialog.Builder(context)
                .setTitle("Remocao de tarefa")
                .setMessage("Deseja remover ${task.description}?")
                .setIcon(R.drawable.ic_delete)
                // Lambda   .setPositiveButton("Remover", {dialogInterface, i ->listener.onDeleteClick(task.id) })
                /* Human readable format */ .setPositiveButton("Remover", handleRemoval(listener, task.id))
                .setNegativeButton("Cancelar", null).show()

    }

    private class handleRemoval(val listener: OnTaskListFragmentInteractionListener, val taskId: Int) : DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            listener.onDeleteClick(taskId)
        }

    }
}