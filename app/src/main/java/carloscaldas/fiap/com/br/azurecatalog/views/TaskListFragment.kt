package carloscaldas.fiap.com.br.azurecatalog.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import carloscaldas.fiap.com.br.azurecatalog.R
import carloscaldas.fiap.com.br.azurecatalog.adapter.TaskListAdapter
import carloscaldas.fiap.com.br.azurecatalog.business.PriorityBusiness
import carloscaldas.fiap.com.br.azurecatalog.business.TaskBusiness
import carloscaldas.fiap.com.br.azurecatalog.contants.TaskConstants
import carloscaldas.fiap.com.br.azurecatalog.util.SecurityPreferences


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TaskListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TaskListFragment : Fragment(), View.OnClickListener {


    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TaskListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(): TaskListFragment {
/*
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
*/
            return TaskListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_task_list, container, false)

        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)
        mContext = rootView.context

        mTaskBusiness = TaskBusiness(mContext)
        mSecurityPreferences = SecurityPreferences(mContext)

        // 1 Obter o elemento
        mRecyclerTaskList = rootView.findViewById(R.id.recyclerTaskList)

        // 2 Definir um layout com os itens de listagem
        mRecyclerTaskList.adapter = TaskListAdapter(mutableListOf())

        // 3 Definir um layout
        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.floatAddTask -> {
                startActivity(Intent(mContext, TaskFormActivity::class.java))
            }
        }
     }

    private fun loadTasks(){
        mRecyclerTaskList.adapter = TaskListAdapter(mTaskBusiness.getList())
    }
}
