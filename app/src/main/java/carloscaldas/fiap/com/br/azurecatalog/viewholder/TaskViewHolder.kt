package carloscaldas.fiap.com.br.azurecatalog.viewholder

import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.TextureView
import android.view.View
import android.widget.TextView
import carloscaldas.fiap.com.br.azurecatalog.R
import carloscaldas.fiap.com.br.azurecatalog.entities.TaskEntity

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)

    fun bindData(task: TaskEntity){
        mTextDescription.text = task.description

    }

}