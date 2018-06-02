package carloscaldas.fiap.com.br.azurecatalog.viewholder

import android.media.Image
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import carloscaldas.fiap.com.br.azurecatalog.R
import carloscaldas.fiap.com.br.azurecatalog.entities.TaskEntity
import carloscaldas.fiap.com.br.azurecatalog.repository.PriorityCacheConstants

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

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
    }

}