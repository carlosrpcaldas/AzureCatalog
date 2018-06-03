package carloscaldas.fiap.com.br.azurecatalog.views

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import carloscaldas.fiap.com.br.azurecatalog.R
import carloscaldas.fiap.com.br.azurecatalog.business.PriorityBusiness
import carloscaldas.fiap.com.br.azurecatalog.business.TaskBusiness
import carloscaldas.fiap.com.br.azurecatalog.contants.TaskConstants
import carloscaldas.fiap.com.br.azurecatalog.entities.PriorityEntity
import carloscaldas.fiap.com.br.azurecatalog.entities.TaskEntity
import carloscaldas.fiap.com.br.azurecatalog.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var mPriorityBusiness: PriorityBusiness
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    private var mLstPrioritiesEntity: MutableList<PriorityEntity> = mutableListOf()
    private var mLstPrioritiesId: MutableList<Int> = mutableListOf()
    private var mTaskId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mPriorityBusiness = PriorityBusiness(this)
        mTaskBusiness = TaskBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        setListeners()
        loadPriorities()
        loadDataFromActivity()

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.buttonDate -> {
                openDatePrickerDialog()
            }
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        buttonDate.text = mSimpleDateFormat.format(calendar.time)
    }

    private fun setListeners(){
        buttonDate.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
    }

    private fun loadDataFromActivity(){
        val bundle =intent.extras
        if (bundle != null){
            mTaskId = bundle.getInt(TaskConstants.BUNDLE.TASKID)
            val task = mTaskBusiness.get(mTaskId)
            if (task != null) {
                editDescription.setText(task.description)
                buttonDate.text = task.dueDate
                checkComplete.isChecked = task.complete
                spinnerPriority.setSelection(getIndex(task.priorityID))
            }
        }
    }

    private fun handleSave(){

        try{
            //  [1, 2, 3, 4]
            val priorityId = mLstPrioritiesId[spinnerPriority.selectedItemPosition]
            val complete = checkComplete.isChecked
            val dueDate = buttonDate.text.toString()
            val description = editDescription.text.toString()
            val userID =  mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()

            val taskEntity = TaskEntity(mTaskId, userID, priorityId, description, dueDate, complete )

            if (mTaskId == 0) {
                mTaskBusiness.insert(taskEntity)
                Toast.makeText(this, getString(R.string.tarefa_incluida), Toast.LENGTH_LONG).show()
            } else {
                mTaskBusiness.update(taskEntity)
                Toast.makeText(this, getString(R.string.tarefa_alterada), Toast.LENGTH_LONG).show()
            }

            finish()

        }catch (e: Exception){
            Toast.makeText(this, getString(R.string.erro_inesperado), Toast.LENGTH_LONG)
        }

    }

    private fun openDatePrickerDialog() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)


        DatePickerDialog(this, this, year, month, dayOfMonth).show()
    }

    private fun getIndex(id: Int) : Int{

        var index = 0
        for (i in 0..mLstPrioritiesEntity.size){
            if (mLstPrioritiesEntity[i].id == id)
                index = 1
                break
        }

        return index
    }


    private fun loadPriorities(){
        mLstPrioritiesEntity = mPriorityBusiness.getList()

        val lstPriorities = mLstPrioritiesEntity.map { it.description}
        mLstPrioritiesId = mLstPrioritiesEntity.map {it.id}.toMutableList()

        // [Baixa, media, alta, Critica]
        //  [1, 2, 3, 4]

        val adapter = ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, lstPriorities)
        spinnerPriority.adapter = adapter
    }

}
