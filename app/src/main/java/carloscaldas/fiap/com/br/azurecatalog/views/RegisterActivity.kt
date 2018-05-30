package carloscaldas.fiap.com.br.azurecatalog.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import carloscaldas.fiap.com.br.azurecatalog.R
import carloscaldas.fiap.com.br.azurecatalog.repository.UserRepository
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setListeners()
        UserRepository.getInstance(this)
//        val b = UserRepository()
//        val c = UserRepository()
    }

    override fun onClick(view: View){
        when (view.id){
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    private fun setListeners(){
        buttonSave.setOnClickListener(this)
    }

    private fun handleSave(){

    }
}
