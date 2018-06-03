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
import android.widget.Toast

import carloscaldas.fiap.com.br.azurecatalog.R
import carloscaldas.fiap.com.br.azurecatalog.adapter.TaskListAdapter
import carloscaldas.fiap.com.br.azurecatalog.business.PriorityBusiness
import carloscaldas.fiap.com.br.azurecatalog.business.TaskBusiness
import carloscaldas.fiap.com.br.azurecatalog.contants.TaskConstants
import carloscaldas.fiap.com.br.azurecatalog.entities.OnTaskListFragmentInteractionListener
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
    private lateinit var mListerner: OnTaskListFragmentInteractionListener
    private var mTaskFilter: Int = 0

    companion object {

        fun newInstance(taskFilter: Int): TaskListFragment {
            val args: Bundle = Bundle()
            args.putInt(TaskConstants.TASKFILTER.KEY, taskFilter)

            val fragment = TaskListFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTaskFilter = arguments!!.getInt(TaskConstants.TASKFILTER.KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_task_list, container, false)

        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)

        // Nao estava aqui antes!!!
        mContext = rootView.context

        //Inicializa variaveis
        mTaskBusiness = TaskBusiness(mContext)
        mSecurityPreferences = SecurityPreferences(mContext)
        mListerner = object : OnTaskListFragmentInteractionListener {

            override fun onListClick(taskId: Int) {
                val bundle: Bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASKID, taskId)

                val intent = Intent(mContext, TaskFormActivity::class.java)
                intent.putExtras(bundle)

                startActivity(intent)
            }
            override fun onDeleteClick(taskId: Int) {
                mTaskBusiness.delete(taskId)
                loadTasks()
                Toast.makeText(mContext, getString(R.string.tarefa_removida_sucesso), Toast.LENGTH_LONG).show()
            }
            override fun onUncompleteClick(taskID: Int) {
                mTaskBusiness.complete(taskID, false)
                loadTasks()
            }

            override fun onCompleteClick(taskID: Int) {
                mTaskBusiness.complete(taskID, true)
                loadTasks()
            }

        }

        // 1 Obter o elemento
        mRecyclerTaskList = rootView.findViewById(R.id.recyclerTaskList)

        // 2 Definir um layout com os itens de listagem
        mRecyclerTaskList.adapter = TaskListAdapter(mutableListOf(), mListerner)

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
        mRecyclerTaskList.adapter = TaskListAdapter(mTaskBusiness.getList(mTaskFilter), mListerner)
    }
}
